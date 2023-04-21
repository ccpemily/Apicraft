package com.emily.apicraft.genetics.alleles;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.BeeBranches;
import com.emily.apicraft.genetics.effects.EffectProvider;
import com.emily.apicraft.genetics.flowers.FlowerProvider;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Alleles {
    public enum Species implements IAllele<AlleleSpecies> {
        // region Honey branch
        FOREST(AlleleSpecies.getBuilder()
                .addToTemplate(Fertility.FECUND)
                .setColor(new Color(0x19d0ec), new Color(0xffdc16))
                .build(BeeBranches.HONEY), true),
        MEADOWS(AlleleSpecies.getBuilder()
                .setColor(new Color(0xef131e), new Color(0xffdc16))
                .build(BeeBranches.HONEY), true),
        COMMON(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .setColor(new Color(0xb2b2b2), new Color(0xffdc16))
                .build(BeeBranches.HONEY), true),
        CULTIVATED(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.FASTEST)
                .addToTemplate(LifeSpan.HYPER)
                .setColor(new Color(0x5734ec), new Color(0xffdc16))
                .build(BeeBranches.HONEY), true),
        // endregion
        // region Noble branch
        NOBLE(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setColor(new Color(0xec9a19), new Color(0xffdc16))
                .build(BeeBranches.NOBLE)),
        MAJESTIC(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(Fertility.SWARMING)
                .setColor(new Color(0x7f0000), new Color(0xffdc16))
                .build(BeeBranches.NOBLE), true),
        IMPERIAL(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.LONG)
                .setColor(new Color(0xa3e02f), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.NOBLE)),
        // endregion
        // region Industrious branch
        DILIGENT(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setColor(new Color(0xc219ec), new Color(0xffdc16))
                .build(BeeBranches.INDUSTRIOUS)),
        UNWEARY(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.RAPID)
                .setColor(new Color(0x19ec5a), new Color(0xffdc16))
                .build(BeeBranches.INDUSTRIOUS), true),
        INDUSTRIOUS(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.FAST)
                .addToTemplate(LifeSpan.LONG)
                .setColor(new Color(0xffffff), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.INDUSTRIOUS)),
        // endregion
        // region Heroic branch
        STEADFAST(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.LONG)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0x4d2b15), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.HEROIC)),
        VALIANT(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0x626dbb), new Color(0xffdc16))
                .build(BeeBranches.HEROIC), true),
        HEROIC(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0xb3d5e4), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.HEROIC)),
        // endregion
        // region Infernal branch
        SINISTER(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xb3d5e4), new Color(0x9a2323))
                .build(BeeBranches.INFERNAL)),
        FIENDISH(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xd7bee5), new Color(0x9a2323))
                .build(BeeBranches.INFERNAL), true),
        DEMONIC(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xf4e400), new Color(0x9a2323)).setFoil()
                .build(BeeBranches.INFERNAL)),
        // endregion
        // region Austere branch
        MODEST(AlleleSpecies.getBuilder()
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xc5be86), new Color(0xffdc16))
                .build(BeeBranches.AUSTERE)),
        FRUGAL(AlleleSpecies.getBuilder()
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xe8dcb1), new Color(0xffdc16))
                .build(BeeBranches.AUSTERE), true),
        AUSTERE(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLUGGISH)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CREPUSCULAR)
                .addToTemplate(TemperatureTolerance.DOWN_2)
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xfffac2), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.AUSTERE)),
        // endregion
        // region Tropical branch
        TROPICAL(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x378020), new Color(0xffdc16))
                .build(BeeBranches.TROPICAL)),
        EXOTIC(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x304903), new Color(0xffdc16))
                .build(BeeBranches.TROPICAL), true),
        EDENIC(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOWEST)
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(HumidityTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x393d0d), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.TROPICAL)),
        // endregion
        // region Phantom branch
        ENDED(AlleleSpecies.getBuilder()
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xe079fa), new Color(0xd9de9e))
                .build(BeeBranches.END)),
        SPECTRAL(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xa98bed), new Color(0xd9de9e))
                .build(BeeBranches.END)),
        PHANTASMAL(AlleleSpecies.getBuilder()
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(Territory.LARGE)
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xcc00fa), new Color(0xd9de9e)).setFoil()
                .build(BeeBranches.END), true),
        // endregion
        // region Frozen branch
        WINTRY(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.SLOW)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xa0ffc8), new Color(0xdaf5f3))
                .build(BeeBranches.FROZEN)),
        ICY(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xa0ffff), new Color(0xdaf5f3))
                .build(BeeBranches.FROZEN), true),
        GLACIAL(AlleleSpecies.getBuilder()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.SHORT)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xefffff), new Color(0xdaf5f3)).setFoil()
                .build(BeeBranches.FROZEN)),
        // endregion
        // region Vengeful branch
        VINDICTIVE(AlleleSpecies.getBuilder()
                .addToTemplate(LifeSpan.LONG)
                .addToTemplate(Productivity.SLOWEST)
                .setColor(new Color(0xeafff3), new Color(0xffdc16))
                .build(BeeBranches.VENGEFUL)),
        VENGEFUL(AlleleSpecies.getBuilder()
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Productivity.SLUGGISH)
                .setColor(new Color(0xc2de00), new Color(0xffdc16))
                .build(BeeBranches.VENGEFUL)),
        AVENGING(AlleleSpecies.getBuilder()
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(Productivity.SLUGGISH)
                .setColor(new Color(0xddff00), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.VENGEFUL)),
        // endregion
        // region Agrarian branch
        RURAL(AlleleSpecies.getBuilder()
                .setColor(new Color(0xfeff8f), new Color(0xffdc16))
                .build(BeeBranches.AGRARIAN)),
        FARMER(AlleleSpecies.getBuilder()
                .setColor(new Color(0xd39728), new Color(0xffdc16))
                .build(BeeBranches.AGRARIAN), true),
        AGRARIAN(AlleleSpecies.getBuilder()
                .addToTemplate(HumidityTolerance.BOTH_2)
                .setColor(new Color(0xffca75), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.AGRARIAN), true),
        // endregion
        // region Boggy branch
        MARSHY(AlleleSpecies.getBuilder()
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x546626), new Color(0xffdc16))
                .build(BeeBranches.BOGGY), true),
        MIRY(AlleleSpecies.getBuilder()
                .addToTemplate(RainTolerance.TRUE)
                .addToTemplate(Behavior.CREPUSCULAR)
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x92af42), new Color(0xffdc16))
                .build(BeeBranches.BOGGY)),
        BOGGY(AlleleSpecies.getBuilder()
                .addToTemplate(RainTolerance.TRUE)
                .addToTemplate(Behavior.CATHEMERAL)
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x698948), new Color(0xffdc16)).setFoil()
                .build(BeeBranches.BOGGY)),
        // endregion
        // region Monastic branch
        MONASTIC(AlleleSpecies.getBuilder()
                .setColor(new Color(0x42371c), new Color(0xfff7b6))
                .build(BeeBranches.MONASTIC)),
        SECLUDED(AlleleSpecies.getBuilder()
                .setColor(new Color(0x7b6634), new Color(0xfff7b6))
                .build(BeeBranches.MONASTIC), true),
        HERMITIC(AlleleSpecies.getBuilder()
                .setColor(new Color(0xffd46c), new Color(0xfff7b6)).setFoil()
                .build(BeeBranches.MONASTIC)),
        // endregion
        // region Player specified branch
        EMILIAS(AlleleSpecies.getBuilder()
                .setColor(new Color(0xd7bee5), new Color(0xfd58ab)).setFoil()
                .build(BeeBranches.EMILY)),
        AKINAPIS(AlleleSpecies.getBuilder()
                .setColor(new Color(0xa976ff), new Color(0xdc77ff)).setFoil()
                .build(BeeBranches.AKINA));
        // endregion
        // region InternalMethods
        private final boolean dominant;
        private final AlleleSpecies species;

        Species(AlleleSpecies species){
            this(species, false);
        }
        Species(AlleleSpecies species, boolean dominant){
            this.dominant = dominant;
            this.species = species;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            components.add(Component.translatable("tooltip.branch")
                    .append(": ")
                    .append(Component.translatable(getValue().getBranch().getName()).withStyle(ChatFormatting.YELLOW)));
            return components;
        }

        @Override
        public AlleleSpecies getValue() {
            return species;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.SPECIES;
        }

        // endregion
    }
    public enum LifeSpan implements IAllele<Integer> {
        HYPER(10),
        RAPID(15),
        SHORT(20, true),
        NORMAL(30, true),
        LONG(40),
        ANCIENT(60, true),
        ETERNAL(100);

        // region InternalMethods
        private final int span;
        private final boolean dominant;
        LifeSpan(int span){
            this(span, false);
        }
        LifeSpan(int span, boolean dominant){
            this.span = span;
            this.dominant = dominant;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            components.add(Component.translatable("tooltip.max_health")
                    .append(": ")
                    .append(Component.literal(span * 30 + "s").withStyle(ChatFormatting.YELLOW)));
            return components;
        }

        @Override
        public Integer getValue() {
            return span;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.LIFESPAN;
        }
        // endregion
    }
    public enum Productivity implements IAllele<Float> {
        SLUGGISH(0.2f),
        SLOWEST(0.5f, true),
        SLOW(0.8f, true),
        NORMAL(1.0f),
        FAST(1.3f, true),
        FASTEST(1.7f),
        BRISK(2.0f);

        // region InternalMethods
        private final float productivity;
        private final boolean dominant;

        Productivity(float productivity){
            this(productivity, false);
        }
        Productivity(float productivity, boolean dominant){
            this.productivity = productivity;
            this.dominant = dominant;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            components.add(Component.translatable("tooltip.productivity")
                    .append(": ")
                    .append(Component.literal(productivity + " x").withStyle(ChatFormatting.YELLOW)));
            return components;
        }

        @Override
        public Float getValue() {
            return productivity;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.PRODUCTIVITY;
        }
        // endregion
    }
    public enum Fertility implements IAllele<Integer> {
        STERILE(0),
        INFERTILE(1, true),
        UNLUCKY(2, true),
        FERTILE(3, true),
        FECUND(4),
        PROLIFIC(5),
        SWARMING(6);

        // region InternalMethods
        private final int fertility;
        private final boolean dominant;

        Fertility(int fertility){
            this(fertility, false);
        }
        Fertility(int fertility, boolean dominant){
            this.fertility = fertility;
            this.dominant = dominant;
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            return components;
        }

        @Override
        public Integer getValue() {
            return fertility;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.FERTILITY;
        }
        // endregion
    }
    public enum Behavior implements IAllele<Function<Integer, Boolean>> {
        DIURNAL(true), NOCTURNAL(true), CREPUSCULAR, CATHEMERAL;
        // region InternalMethods
        private final boolean dominant;

        Behavior(){
            this(false);
        }
        Behavior(boolean dominant){
            this.dominant = dominant;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }
        private boolean canWork(int skylight){
            if(this == DIURNAL){
                return skylight > 11;
            }
            else if(this == NOCTURNAL){
                return skylight < 11;
            }
            else if(this == CREPUSCULAR){
                return skylight > 8 && skylight < 13;
            }
            return true;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            ChatFormatting color = ChatFormatting.WHITE;
            switch (this){
                case DIURNAL -> color = ChatFormatting.YELLOW;
                case NOCTURNAL -> color = ChatFormatting.DARK_RED;
                case CREPUSCULAR -> color = ChatFormatting.AQUA;
                case CATHEMERAL -> color = ChatFormatting.DARK_GREEN;
            }
            components.add(Component.translatable("bee.tooltip.behavior").append(Component.translatable("tooltip." + this).withStyle(color)));
            return components;
        }

        @Override
        public Function<Integer, Boolean> getValue() {
            return this::canWork;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.BEHAVIOR;
        }
        // endregion
    }
    public enum RainTolerance implements IAllele<Boolean> {
        TRUE, FALSE;
        // region InternalMethods
        private final boolean dominant;
        RainTolerance(){
            this(false);
        }
        RainTolerance(boolean dominant){
            this.dominant = dominant;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }
        @Override
        public boolean isDominant() {
            return dominant;
        }


        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            return components;
        }

        @Override
        public Boolean getValue() {
            return this == TRUE;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.RAIN_TOLERANCE;
        }
        // endregion
    }
    public enum CaveDwelling implements IAllele<Boolean> {
        TRUE, FALSE;
        // region InternalMethods
        private final boolean dominant;
        CaveDwelling(){
            this(false);
        }
        CaveDwelling(boolean dominant){
            this.dominant = dominant;
        }
        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }
        @Override
        public boolean isDominant() {
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            return components;
        }

        @Override
        public Boolean getValue() {
            return this == TRUE;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.CAVE_DWELLING;
        }
        // endregion
    }
    public enum AcceptedFlowers implements IAllele<FlowerProvider> {
        VANILLA(new FlowerProvider(), true),
        NETHER(new FlowerProvider()),
        CACTI(new FlowerProvider()),
        MUSHROOMS(new FlowerProvider()),
        END(new FlowerProvider()),
        JUNGLE(new FlowerProvider()),
        SNOW(new FlowerProvider()),
        WHEAT(new FlowerProvider()),
        GOURD(new FlowerProvider());

        // region InternalMethods
        private final boolean dominant;
        private final FlowerProvider provider;

        AcceptedFlowers(FlowerProvider provider){
            this(provider, false);
        }
        AcceptedFlowers(FlowerProvider provider, boolean dominant){
            this.dominant = dominant;
            this.provider = provider;
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public FlowerProvider getProvider(){
            return provider;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            return components;
        }

        @Override
        public FlowerProvider getValue() {
            return provider;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.ACCEPTED_FLOWERS;
        }
        // endregion
    }
    public enum TemperatureTolerance implements IAllele<BiFunction<EnumTemperature, EnumTemperature, Boolean>> {
        NONE,
        BOTH_1, BOTH_2, BOTH_3, BOTH_4, BOTH_5,
        UP_1(true), UP_2, UP_3, UP_4, UP_5,
        DOWN_1(true), DOWN_2, DOWN_3, DOWN_4, DOWN_5;

        // region InternalMethods
        private final boolean dominant;
        private final int toleranceUp;
        private final int toleranceDown;
        TemperatureTolerance(){
            this(false);
        }
        TemperatureTolerance(boolean dominant){
            this.dominant = dominant;
            String name = this.name();
            if(name.contains("NONE")){
                toleranceUp = 0;
                toleranceDown = 0;
            }
            else {
                int tolerance = Integer.parseInt(name.substring(name.length() - 1));
                if(name.contains("UP")){
                    toleranceUp = tolerance;
                    toleranceDown = 0;
                }
                else if(name.contains("DOWN")){
                    toleranceUp = 0;
                    toleranceDown = tolerance;
                }
                else {
                    toleranceUp = tolerance;
                    toleranceDown = tolerance;
                }
            }
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        private boolean canTolerate(EnumTemperature environment, EnumTemperature self){
            if(environment == EnumTemperature.NONE){
                return false;
            }
            int diff = environment.ordinal() - self.ordinal();
            return diff <= toleranceUp && diff >= -toleranceDown;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            return Collections.emptyList();
        }

        @Override
        public BiFunction<EnumTemperature, EnumTemperature, Boolean> getValue() {
            return this::canTolerate;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.TEMPERATURE_TOLERANCE;
        }
        // endregion
    }
    public enum HumidityTolerance implements IAllele<BiFunction<EnumHumidity, EnumHumidity, Boolean>> {
        NONE,
        BOTH_1, BOTH_2,
        UP_1(true), UP_2,
        DOWN_1(true), DOWN_2;

        // region InternalMethods
        private final boolean dominant;
        private final int toleranceUp;
        private final int toleranceDown;
        HumidityTolerance(){
            this(false);
        }
        HumidityTolerance(boolean dominant){
            this.dominant = dominant;
            String name = this.name();
            if(name.contains("NONE")){
                toleranceUp = 0;
                toleranceDown = 0;
            }
            else {
                int tolerance = Integer.parseInt(name.substring(name.length() - 1));
                if(name.contains("UP")){
                    toleranceUp = tolerance;
                    toleranceDown = 0;
                }
                else if(name.contains("DOWN")){
                    toleranceUp = 0;
                    toleranceDown = tolerance;
                }
                else {
                    toleranceUp = tolerance;
                    toleranceDown = tolerance;
                }
            }
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        private boolean canTolerate(EnumHumidity environment, EnumHumidity self){
            if(environment == EnumHumidity.NONE){
                return false;
            }
            int diff = environment.ordinal() - self.ordinal();
            return diff <= toleranceUp && diff >= -toleranceDown;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            return Collections.emptyList();
        }

        @Override
        public BiFunction<EnumHumidity, EnumHumidity, Boolean> getValue() {
            return this::canTolerate;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.HUMIDITY_TOLERANCE;
        }
        // endregion
    }
    public enum Territory implements IAllele<Vec3i> {
        AVERAGE(9, 6, 9),
        LARGE(11, 8, 11),
        LARGER(13, 12, 13),
        LARGEST(15, 13, 15);

        // region InternalMethods
        private final Vec3i territory;
        private final boolean dominant;

        Territory(int x, int y, int z){
            this(new Vec3i(x, y, z));
        }

        Territory(int x, int y, int z, boolean dominant){
            this(new Vec3i(x, y, z), dominant);
        }
        Territory(Vec3i territory){
            this(territory, false);
        }

        Territory(Vec3i territory, boolean dominant){
            this.territory = territory;
            this.dominant = dominant;
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            components.add(Component.translatable("tooltip.territory")
                    .append(": ")
                    .append(Component.literal(territory.getX() + " x " + territory.getY() + " x " + territory.getZ()).withStyle(ChatFormatting.YELLOW)));
            return components;
        }

        @Override
        public Vec3i getValue() {
            return territory;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.TERRITORY;
        }
        // endregion
    }
    public enum Effect implements IAllele<EffectProvider> {
        NONE(new EffectProvider());

        // region InternalMethods
        private final EffectProvider provider;
        private final boolean dominant;

        Effect(EffectProvider provider){
            this(provider, false);
        }

        Effect(EffectProvider provider, boolean dominant){
            this.dominant = dominant;
            this.provider = provider;
        }

        @Override
        public String toString(){
            return getType() + "." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        @Override
        public List<Component> getDescriptionTooltips() {
            List<String> strings = List.of(Component.translatable(getDescription()).getString().split("\n"));
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable(getType().getName())
                    .append(": ")
                    .append(Component.translatable(getName()).withStyle(ChatFormatting.YELLOW)));
            for(String str : strings){
                components.add(Component.literal(str));
            }
            return components;
        }

        @Override
        public EffectProvider getValue() {
            return provider;
        }

        @Override
        public IAlleleType getType() {
            return AlleleTypes.EFFECT;
        }
        // endregion
    }
}
