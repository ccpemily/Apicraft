package com.emily.apicraft.genetics;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.core.lib.ErrorStates;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.*;

import static com.emily.apicraft.utils.Tags.*;

public class Bee {
    // region Fields
    private final BeeGenome genome;
    @Nullable
    private BeeGenome mate;
    private int health;
    private final int generation;
    // endregion

    // region Constructor
    public Bee(BeeGenome genome, @Nullable BeeGenome mate, int generation){
        this.genome = genome;
        this.health = genome.getMaxHealth();
        this.generation = generation;
    }
    public Bee(BeeGenome genome, @Nullable BeeGenome mate){
        this(genome, mate, 0);
    }
    public Bee(BeeGenome genome){
        this(genome, null);
    }
    public Bee(CompoundTag tag){
        tag = tag.getCompound(TAG_BEE);
        CompoundTag genomeTag = tag.getCompound(TAG_GENOME);
        this.genome = new BeeGenome(genomeTag);
        if(tag.contains(TAG_MATE)){
            CompoundTag mateTag = tag.getCompound(TAG_MATE);
            this.mate = new BeeGenome(mateTag);
        }
        else{
            this.mate = null;
        }
        this.health = tag.getInt(TAG_HEALTH);
        this.generation = tag.getInt(TAG_GENERATION);
    }
    // endregion

    // region NBT
    public CompoundTag writeToTag(CompoundTag tag){
        CompoundTag beeTag = new CompoundTag();
        beeTag.put(TAG_GENOME, this.genome.writeToTag(new CompoundTag()));
        if(this.mate != null){
            beeTag.put(TAG_MATE, this.mate.writeToTag(new CompoundTag()));
        }
        beeTag.putInt(TAG_HEALTH, this.health);
        beeTag.putInt(TAG_GENERATION, this.generation);
        tag.put(TAG_BEE, beeTag);
        return tag;
    }
    // endregion

    // region Tooltips
    public void addTooltip(List<Component> components) {
        Alleles.Species speciesActive = genome.getSpecies();
        Alleles.Species speciesInactive = genome.getInactiveSpecies();
        if(speciesActive != speciesInactive){
            components.add(
                    Component.translatable(speciesActive.getName())
                            .append("-")
                            .append(Component.translatable(speciesInactive.getName())
                                    .append(Component.translatable("bee.tooltip.hybrid"))
                            ).withStyle(ChatFormatting.BLUE)
            );
        }
        if(generation > 0){
            Rarity rarity;
            if (generation >= 1000) {
                rarity = Rarity.EPIC;
            } else if (generation >= 100) {
                rarity = Rarity.RARE;
            } else if (generation >= 10) {
                rarity = Rarity.UNCOMMON;
            } else {
                rarity = Rarity.COMMON;
            }
            components.add(Component.translatable("bee.tooltip.generation", generation)
            .withStyle(rarity.getStyleModifier().apply(Style.EMPTY)));
        }
        components.add(Component.translatable(genome.getAllele(AlleleTypes.LIFESPAN, true).getName()).withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable(genome.getAllele(AlleleTypes.PRODUCTIVITY, true).getName()).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("T: ")
                .append(Component.translatable(speciesActive.getValue().getTemperature().getName())
                        .append(" / ")
                        .append(Component.translatable(genome.getAllele(AlleleTypes.TEMPERATURE_TOLERANCE, true).getName()))
                ).withStyle(ChatFormatting.GREEN)
        );
        components.add(Component.literal("H: ")
                .append(Component.translatable(speciesActive.getValue().getHumidity().getName())
                                .append(" / ")
                                .append(Component.translatable(genome.getAllele(AlleleTypes.HUMIDITY_TOLERANCE, true).getName()))
                ).withStyle(ChatFormatting.AQUA)
        );
        Alleles.Behavior behavior = (Alleles.Behavior) genome.getAllele(AlleleTypes.BEHAVIOR, true);
        ChatFormatting color = ChatFormatting.WHITE;
        switch (behavior){
            case DIURNAL -> color = ChatFormatting.YELLOW;
            case NOCTURNAL -> color = ChatFormatting.DARK_RED;
            case CREPUSCULAR -> color = ChatFormatting.AQUA;
            case CATHEMERAL -> color = ChatFormatting.DARK_GREEN;
        }
        components.add(Component.translatable("bee.tooltip.behavior").append(Component.translatable("tooltip." + behavior).withStyle(color)));
        components.add(Component.translatable(genome.getAllele(AlleleTypes.ACCEPTED_FLOWERS, true).getName()).withStyle(ChatFormatting.GRAY));
        if(genome.isCaveDwelling()){
            components.add(Component.translatable("bee.tooltip.cave_dwelling").withStyle(ChatFormatting.DARK_GRAY));
        }
        if(genome.toleratesRain()){
            components.add(Component.translatable("bee.tooltip.tolerates_rain").withStyle(ChatFormatting.WHITE));
        }
    }
    // endregion

    // region BeeInfo
    public BeeGenome getGenome(){
        return genome;
    }
    public int getMaxHealth(){
        return genome.getMaxHealth();
    }

    public int getHealth(){
        return health;
    }

    public boolean isAlive(){
        return health > 0;
    }
    // endregion

    // region BeeBehavior
    public void age(float modifier){
        if(this.health > 0){
            int remain = (int) Math.floor(modifier);
            float chance = modifier - remain;
            this.health = health - remain;
            Random random = new Random();
            if(random.nextFloat() <= chance){
                this.health -= 1;
            }
        }
        if(this.health < 0){
            this.health = 0;
        }
    }

    public void mate(@Nonnull BeeGenome mate){
        if(this.mate == null){
            this.mate = mate;
        }
    }

    public List<ItemStack> getOffspring(IBeeHousing housing){
        if(this.mate == null){
            Apicraft.LOGGER.error("Detected queen with no mate try to get offspring.");
            return Collections.emptyList();
        }
        int fertility = housing.applyFertilityModifier(getGenome().getFertility());
        List<ItemStack> beeList = new ArrayList<>();
        Random random = new Random();
        int r = random.nextInt(2);
        for (int i = 0; i < fertility; i++){
            ItemStack stack = new ItemStack(Registries.ITEMS.get(ItemUtils.BEE_LARVA_ID));
            Bee bee = new Bee(getGenome().inheritWith(mate, housing), null, 0);
            if(i == 0){
                stack = new ItemStack(Registries.ITEMS.get(ItemUtils.BEE_PRINCESS_ID));
                bee = new Bee(getGenome().inheritWith(mate, housing), null, generation + 1);
            }
            if(i <= 1 + r){
                stack = new ItemStack(Registries.ITEMS.get(ItemUtils.BEE_DRONE_ID));
            }
            BeeProviderCapability.get(stack).setBeeIndividual(bee);
            beeList.add(stack);
        }
        return beeList;
    }

    public boolean canWork(IBeeHousing housing){
        // Check for level absence.
        Optional<Level> levelOptional = Optional.ofNullable(housing.getBeeHousingLevel());
        if(levelOptional.isEmpty()){
            housing.setErrorState(ErrorStates.ILLEGAL_STATE);
            return false; // Null level (exceptional)
        }
        Level level = levelOptional.get();
        // Check weather
        boolean isRainingAside = level.isRainingAt(housing.getBeeHousingPos().above())
                || level.isRainingAt(housing.getBeeHousingPos().east())
                || level.isRainingAt(housing.getBeeHousingPos().west())
                || level.isRainingAt(housing.getBeeHousingPos().north())
                || level.isRainingAt(housing.getBeeHousingPos().south());
        if(!this.getGenome().toleratesRain() && isRainingAside){
            housing.setErrorState(ErrorStates.IS_RAINING);
            return false; // Raining
        }
        // Check skylight
        int skylight = level.dimensionType().hasSkyLight() ? 15 - level.getSkyDarken() : 0;
        ErrorStates state = getGenome().canWork(skylight);
        if(state != ErrorStates.NONE){
            housing.setErrorState(state);
            return false;
        }
        // Check cave dwelling
        if(!level.dimensionType().hasCeiling() && level.dimensionType().hasSkyLight()){
            if(!getGenome().isCaveDwelling() && !level.canSeeSkyFromBelowWater(housing.getBeeHousingPos())){
                housing.setErrorState(ErrorStates.CANT_SEE_SKY);
                return false; // Not cave dwelling but in cave
            }
        }
        // Check climate
        EnumTemperature temperature = housing.getTemperature();
        EnumHumidity humidity = housing.getHumidity();
        EnumTemperature speciesTemperature = getGenome().getSpecies().getValue().getTemperature();
        EnumHumidity speciesHumidity = getGenome().getSpecies().getValue().getHumidity();
        ErrorStates temperatureState =
                ((Alleles.TemperatureTolerance) getGenome().getAllele(AlleleTypes.TEMPERATURE_TOLERANCE, true))
                        .getValue().apply(temperature, speciesTemperature);
        ErrorStates humidityState =
                ((Alleles.HumidityTolerance) getGenome().getAllele(AlleleTypes.HUMIDITY_TOLERANCE, true))
                        .getValue().apply(humidity, speciesHumidity);
        if(temperatureState != ErrorStates.NONE){
            housing.setErrorState(temperatureState);
            return false; // Climate not suitable.
        }
        else if(humidityState != ErrorStates.NONE){
            housing.setErrorState(humidityState);
            return false;
        }
        housing.setErrorState(ErrorStates.NONE);
        return true; // No errors, queen can get work !
    }

    public boolean canProduceSpecial(IBeeHousing housing, boolean active){
        EnumTemperature temperature = housing.getTemperature();
        EnumHumidity humidity = housing.getHumidity();
        EnumTemperature speciesTemperature =
                active ? getGenome().getSpecies().getValue().getTemperature() :
                        getGenome().getInactiveSpecies().getValue().getTemperature();
        EnumHumidity speciesHumidity =
                active ? getGenome().getSpecies().getValue().getHumidity() :
                        getGenome().getInactiveSpecies().getValue().getHumidity();
        return temperature == speciesTemperature && humidity == speciesHumidity;
    }
    // endregion

    // region Helpers
    public static Bee getPure(IAllele<AlleleSpecies> species){
        return new Bee(BeeKaryotype.INSTANCE.defaultGenome(species));
    }

    public static Bee getPureMated(IAllele<AlleleSpecies> species){
        BeeGenome genome = BeeKaryotype.INSTANCE.defaultGenome(species);
        return new Bee(genome, genome);
    }

    public boolean isGeneticEqual(Bee other){
        return getGenome().isEqualTo(other.getGenome());
    }
    // endregion
}
