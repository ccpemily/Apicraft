package com.emily.apicraft.genetics;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

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
        Chromosomes.Species speciesActive = genome.getSpecies();
        Chromosomes.Species speciesInactive = genome.getInactiveSpecies();
        if(speciesActive != speciesInactive){
            components.add(
                    Component.translatable("chromosomes." + speciesActive.toString()).append("-").append(Component.translatable("chromosomes." + speciesInactive.toString()).append(Component.translatable("bee.tooltip.hybrid")).withStyle(ChatFormatting.BLUE))
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
            components.add(Component.literal(String.format(Component.translatable("bee.tooltip.generation").toString(), generation)).withStyle(rarity.getStyleModifier()));
        }
        components.add(Component.translatable("chromosomes." + genome.getChromosomeValue(Chromosomes.LifeSpan.class, true).toString()).withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("chromosomes." + genome.getChromosomeValue(Chromosomes.Productivity.class, true).toString()).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("T: ")
                .append(Component.translatable(speciesActive.getTemperature().getName())
                        .append(" / ")
                        .append(Component.translatable("chromosomes." + genome.getChromosomeValue(Chromosomes.TemperatureTolerance.class, true).toString()))
                ).withStyle(ChatFormatting.GREEN)
        );
        components.add(Component.literal("H: ")
                .append(Component.translatable(speciesActive.getHumidity().getName())
                                .append(" / ")
                                .append(Component.translatable("chromosomes." + genome.getChromosomeValue(Chromosomes.HumidityTolerance.class, true).toString()))
                ).withStyle(ChatFormatting.GREEN)
        );
        Chromosomes.Behavior behavior = genome.getChromosomeValue(Chromosomes.Behavior.class, true);
        ChatFormatting color = ChatFormatting.WHITE;
        switch (behavior){
            case DIURNAL -> color = ChatFormatting.YELLOW;
            case NOCTURNAL -> color = ChatFormatting.DARK_RED;
            case CREPUSCULAR -> color = ChatFormatting.AQUA;
            case CATHEMERAL -> color = ChatFormatting.DARK_GREEN;
        }
        components.add(Component.translatable("bee.tooltip.behavior").append(Component.translatable("tooltip." + behavior).withStyle(color)));
        components.add(Component.translatable("chromosomes." + genome.getChromosomeValue(Chromosomes.AcceptedFlowers.class, true).toString()).withStyle(ChatFormatting.GRAY));
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
    public void age(){
        if(this.health > 0){
            health--;
        }
    }

    public boolean mate(@Nonnull BeeGenome mate){
        if(this.mate != null){
            return false;
        }
        else{
            this.mate = mate;
            return true;
        }
    }

    // endregion

    // region Helpers
    public static Bee getPure(Chromosomes.Species species){
        return new Bee(BeeKaryotype.INSTANCE.defaultGenome(species));
    }
    // endregion
}
