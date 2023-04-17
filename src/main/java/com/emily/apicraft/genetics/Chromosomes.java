package com.emily.apicraft.genetics;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.effects.EffectProvider;
import com.emily.apicraft.genetics.flowers.FlowerProvider;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import net.minecraft.core.Vec3i;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;

public class Chromosomes {
    public enum Species implements IChromosomeType {
        FOREST(BeeBranches.HONEY, BeeSpeciesProperties.get()
                .addToTemplate(Fertility.FECUND)
                .setColor(new Color(0x19d0ec), new Color(0xffdc16)), true),
        MEADOWS(BeeBranches.HONEY, BeeSpeciesProperties.get()
                .setColor(new Color(0xef131e), new Color(0xffdc16)), true),
        COMMON(BeeBranches.HONEY, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .setColor(new Color(0xb2b2b2), new Color(0xffdc16)), true),
        CULTIVATED(BeeBranches.HONEY, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.FASTEST)
                .addToTemplate(LifeSpan.HYPER)
                .setColor(new Color(0x5734ec), new Color(0xffdc16)), true),

        // Noble branch
        NOBLE(BeeBranches.NOBLE, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setColor(new Color(0xec9a19), new Color(0xffdc16))),
        MAJESTIC(BeeBranches.NOBLE, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(Fertility.SWARMING)
                .setColor(new Color(0x7f0000), new Color(0xffdc16)), true),
        IMPERIAL(BeeBranches.NOBLE, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.LONG)
                .setColor(new Color(0xa3e02f), new Color(0xffdc16)).setFoil()),

        // Industrious branch
        DILIGENT(BeeBranches.INDUSTRIOUS, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setColor(new Color(0xc219ec), new Color(0xffdc16))),
        UNWEARY(BeeBranches.INDUSTRIOUS, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.RAPID)
                .setColor(new Color(0x19ec5a), new Color(0xffdc16)), true),
        INDUSTRIOUS(BeeBranches.INDUSTRIOUS, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.FAST)
                .addToTemplate(LifeSpan.LONG)
                .setColor(new Color(0xffffff), new Color(0xffdc16)).setFoil()),

        // Heroic branch
        STEADFAST(BeeBranches.HEROIC, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.LONG)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0x4d2b15), new Color(0xffdc16)).setFoil()),
        VALIANT(BeeBranches.HEROIC, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0x626dbb), new Color(0xffdc16)), true),
        HEROIC(BeeBranches.HEROIC, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CATHEMERAL)
                .addToTemplate(CaveDwelling.TRUE)
                .setColor(new Color(0xb3d5e4), new Color(0xffdc16)).setFoil()),

        // Infernal branch
        SINISTER(BeeBranches.INFERNAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xb3d5e4), new Color(0x9a2323))),
        FIENDISH(BeeBranches.INFERNAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xd7bee5), new Color(0x9a2323)), true),
        DEMONIC(BeeBranches.INFERNAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.ANCIENT)
                .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xf4e400), new Color(0x9a2323)).setFoil()),

        // Austere branch
        MODEST(BeeBranches.AUSTERE, BeeSpeciesProperties.get()
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xc5be86), new Color(0xffdc16))),
        FRUGAL(BeeBranches.AUSTERE, BeeSpeciesProperties.get()
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xe8dcb1), new Color(0xffdc16)), true),
        AUSTERE(BeeBranches.AUSTERE, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLUGGISH)
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Behavior.CREPUSCULAR)
                .addToTemplate(TemperatureTolerance.DOWN_2)
                .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID)
                .setColor(new Color(0xfffac2), new Color(0xffdc16)).setFoil()),

        // Tropical branch
        TROPICAL(BeeBranches.TROPICAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .addToTemplate(LifeSpan.NORMAL)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x378020), new Color(0xffdc16))),
        EXOTIC(BeeBranches.TROPICAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.LONG)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x304903), new Color(0xffdc16)), true),
        EDENIC(BeeBranches.TROPICAL, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOWEST)
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(HumidityTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM).setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x393d0d), new Color(0xffdc16)).setFoil()),

        // Phantom branch
        ENDED(BeeBranches.END, BeeSpeciesProperties.get()
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xe079fa), new Color(0xd9de9e))),
        SPECTRAL(BeeBranches.END, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xa98bed), new Color(0xd9de9e))),
        PHANTASMAL(BeeBranches.END, BeeSpeciesProperties.get()
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(Territory.LARGE)
                .setTemperature(EnumTemperature.COLD)
                .setColor(new Color(0xcc00fa), new Color(0xd9de9e)).setFoil(), true),

        // Frozen branch
        WINTRY(BeeBranches.FROZEN, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.SLOW)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xa0ffc8), new Color(0xdaf5f3))),
        ICY(BeeBranches.FROZEN, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xa0ffff), new Color(0xdaf5f3)), true),
        GLACIAL(BeeBranches.FROZEN, BeeSpeciesProperties.get()
                .addToTemplate(Productivity.NORMAL)
                .addToTemplate(LifeSpan.SHORT)
                .setTemperature(EnumTemperature.ICY)
                .setColor(new Color(0xefffff), new Color(0xdaf5f3)).setFoil()),

        // Vengeful branch
        VINDICTIVE(BeeBranches.VENGEFUL, BeeSpeciesProperties.get()
                .addToTemplate(LifeSpan.LONG)
                .addToTemplate(Productivity.SLOWEST)
                .setColor(new Color(0xeafff3), new Color(0xffdc16))),
        VENGEFUL(BeeBranches.VENGEFUL, BeeSpeciesProperties.get()
                .addToTemplate(LifeSpan.ANCIENT)
                .addToTemplate(Productivity.SLUGGISH)
                .setColor(new Color(0xc2de00), new Color(0xffdc16))),
        AVENGING(BeeBranches.VENGEFUL, BeeSpeciesProperties.get()
                .addToTemplate(LifeSpan.ETERNAL)
                .addToTemplate(Productivity.SLUGGISH)
                .setColor(new Color(0xddff00), new Color(0xffdc16)).setFoil()),

        // Agrarian branch
        RURAL(BeeBranches.AGRARIAN, BeeSpeciesProperties.get()
                .setColor(new Color(0xfeff8f), new Color(0xffdc16))),
        FARMER(BeeBranches.AGRARIAN, BeeSpeciesProperties.get()
                .setColor(new Color(0xd39728), new Color(0xffdc16)), true),
        AGRARIAN(BeeBranches.AGRARIAN, BeeSpeciesProperties.get()
                .addToTemplate(HumidityTolerance.BOTH_2)
                .setColor(new Color(0xffca75), new Color(0xffdc16)).setFoil(), true),

        // Boggy branch
        MARSHY(BeeBranches.BOGGY, BeeSpeciesProperties.get()
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x546626), new Color(0xffdc16)), true),
        MIRY(BeeBranches.BOGGY, BeeSpeciesProperties.get()
                .addToTemplate(RainTolerance.TRUE)
                .addToTemplate(Behavior.CREPUSCULAR)
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x92af42), new Color(0xffdc16))),
        BOGGY(BeeBranches.BOGGY, BeeSpeciesProperties.get()
                .addToTemplate(RainTolerance.TRUE)
                .addToTemplate(Behavior.CATHEMERAL)
                .setHumidity(EnumHumidity.DAMP)
                .setColor(new Color(0x698948), new Color(0xffdc16)).setFoil()),

        // Monastic branch
        MONASTIC(BeeBranches.MONASTIC, BeeSpeciesProperties.get()
                .setColor(new Color(0x42371c), new Color(0xfff7b6))),
        SECLUDED(BeeBranches.MONASTIC, BeeSpeciesProperties.get()
                .setColor(new Color(0x7b6634), new Color(0xfff7b6)), true),
        HERMITIC(BeeBranches.MONASTIC, BeeSpeciesProperties.get()
                .setColor(new Color(0xffd46c), new Color(0xfff7b6)).setFoil()),

        // Player specified branch
        EMILIAS(BeeBranches.EMILY, BeeSpeciesProperties.get()
                .setColor(new Color(0xd7bee5), new Color(0xfd58ab)).setFoil()),
        AKINAPIS(BeeBranches.AKINA, BeeSpeciesProperties.get()
                .setColor(new Color(0xa976ff), new Color(0xdc77ff)).setFoil());

        // region InternalMethods
        private final boolean dominant;
        private final BeeBranches branch;
        private final int firstColor;
        private final int secondColor;
        private final boolean isFoil;
        private final EnumTemperature temperature;
        private final EnumHumidity humidity;
        private final HashMap<Class<? extends IChromosomeType>, IChromosomeType> template;

        Species(BeeBranches branch, BeeSpeciesProperties properties){
            this(branch, properties, false);
        }
        Species(BeeBranches branch, BeeSpeciesProperties builder, boolean dominant){
            this.dominant = dominant;
            this.firstColor = builder.firstColor;
            this.secondColor = builder.secondColor;
            this.branch = branch;
            this.isFoil = builder.isFoil;
            this.temperature = builder.temperature;
            this.humidity = builder.humidity;
            this.template = builder.template;
            for(Class<? extends IChromosomeType> key : branch.getTemplate().keySet()){
                if(!template.containsKey(key)){
                    template.put(key, branch.getTemplate().get(key));
                }
            }
        }
        @Override
        public String toString(){
            return "species." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        public int getColor(int tintIndex){
            return tintIndex == 0 ? firstColor : tintIndex == 1 ? secondColor : 0xffffff;
        }

        public boolean isFoil(){
            return isFoil;
        }

        public EnumTemperature getTemperature() {
            return temperature;
        }

        public EnumHumidity getHumidity() {
            return humidity;
        }

        public HashMap<Class<? extends IChromosomeType>, IChromosomeType> getTemplate() {
            return template;
        }

        public BeeBranches getBranch(){
            return branch;
        }

        private static class BeeSpeciesProperties {
            public int firstColor = 0x000000;
            public int secondColor = 0xffffff;
            public boolean isFoil = false;
            public EnumTemperature temperature = EnumTemperature.NORMAL;
            public EnumHumidity humidity = EnumHumidity.NORMAL;
            public HashMap<Class<? extends IChromosomeType>, IChromosomeType> template = new HashMap<>();

            private BeeSpeciesProperties(){

            }

            public BeeSpeciesProperties setColor(Color first, Color second){
                this.firstColor = first.getRGB();
                this.secondColor = second.getRGB();
                return this;
            }

            public BeeSpeciesProperties setFoil(){
                this.isFoil = true;
                return this;
            }

            public BeeSpeciesProperties setTemperature(EnumTemperature temperature){
                this.temperature = temperature;
                return this;
            }

            public BeeSpeciesProperties setHumidity(EnumHumidity humidity){
                this.humidity = humidity;
                return this;
            }

            public BeeSpeciesProperties addToTemplate(IChromosomeType type){
                template.put(type.getClass(), type);
                return this;
            }

            public static BeeSpeciesProperties get(){
                return new BeeSpeciesProperties();
            }
        }
        // endregion
    }
    public enum LifeSpan implements IChromosomeType {
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
            return "lifespan." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }
        public int getMaxHealth(){
            return span;
        }
        // endregion
    }
    public enum Productivity implements IChromosomeType {
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
            return "productivity." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        public float getProductivity(){
            return productivity;
        }
        // endregion
    }
    public enum Fertility implements IChromosomeType {
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
            return "fertility." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }

        public int getFertility() {
            return fertility;
        }

        // endregion
    }
    public enum Behavior implements IChromosomeType {
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
            return "behavior." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant(){
            return dominant;
        }
        public boolean canWork(long time){
            if(this == DIURNAL){
                return time > 1000 && time < 13000;
            }
            else if(this == NOCTURNAL){
                return time < 1000 || time > 13000;
            }
            else if(this == CREPUSCULAR){
                return time < 1000 || (time > 13000 && time < 16000) || time > 22000;
            }
            return true;
        }
        // endregion
    }
    public enum RainTolerance implements IChromosomeType {
        TRUE, FALSE;
        // region InternalMethods
        private final boolean dominant;
        RainTolerance(){
            this(false);
        }
        RainTolerance(boolean dominant){
            this.dominant = true;
        }
        @Override
        public String toString(){
            return "rain_tolerance." + this.name().toLowerCase(Locale.ENGLISH);
        }
        @Override
        public boolean isDominant() {
            return dominant;
        }

        public boolean toleratesRain(){
            return this == TRUE;
        }
        // endregion
    }
    public enum CaveDwelling implements IChromosomeType {
        TRUE, FALSE;
        // region InternalMethods
        private final boolean dominant;
        CaveDwelling(){
            this(false);
        }
        CaveDwelling(boolean dominant){
            this.dominant = true;
        }
        @Override
        public String toString(){
            return "cave_dwelling." + this.name().toLowerCase(Locale.ENGLISH);
        }
        @Override
        public boolean isDominant() {
            return dominant;
        }

        public boolean isCaveDwelling(){
            return this == TRUE;
        }
        // endregion
    }
    public enum AcceptedFlowers implements IChromosomeType {
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
            return "accepted_flowers." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public FlowerProvider getProvider(){
            return provider;
        }
        // endregion
    }
    public enum TemperatureTolerance implements IChromosomeType {
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
            String name = this.name().toLowerCase(Locale.ENGLISH);
            if(name.contains("none")){
                toleranceUp = 0;
                toleranceDown = 0;
            }
            else {
                int tolerance = Integer.parseInt(name.substring(name.length() - 1));
                if(name.contains("up")){
                    toleranceUp = tolerance;
                    toleranceDown = 0;
                }
                else if(name.contains("down")){
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
            return "temperature_tolerance." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public boolean canTolerate(EnumTemperature environment, EnumTemperature self){
            int diff = environment.ordinal() - self.ordinal();
            return diff <= toleranceUp && diff >= -toleranceDown;
        }
        // endregion
    }
    public enum HumidityTolerance implements IChromosomeType {
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
            String name = this.name().toLowerCase(Locale.ENGLISH);
            if(name.contains("none")){
                toleranceUp = 0;
                toleranceDown = 0;
            }
            else {
                int tolerance = Integer.parseInt(name.substring(name.length() - 1));
                if(name.contains("up")){
                    toleranceUp = tolerance;
                    toleranceDown = 0;
                }
                else if(name.contains("down")){
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
            return "humidity_tolerance." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public boolean canTolerate(EnumTemperature environment, EnumTemperature self){
            int diff = environment.ordinal() - self.ordinal();
            return diff <= toleranceUp && diff >= -toleranceDown;
        }
        // endregion
    }
    public enum Territory implements IChromosomeType {
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
            return "territory." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public Vec3i getTerritory() {
            return territory;
        }
        // endregion
    }
    public enum Effect implements IChromosomeType {
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
            return "effect." + this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public boolean isDominant() {
            return dominant;
        }

        public EffectProvider getProvider() {
            return provider;
        }
        // endregion
    }
}
