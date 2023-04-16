package com.emily.apicraft.genetics;

import com.emily.apicraft.genetics.flowers.FlowerProvider;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;

import java.awt.*;
import java.util.Locale;

public class Chromosomes {
    public enum Species implements IChromosomeType {
        FOREST(BeeSpeciesProperties.get()
                .setColor(new Color(0x19d0ec), new Color(0xffdc16)), true),
        MEADOWS(BeeSpeciesProperties.get()
                .setColor(new Color(0xef131e), new Color(0xffdc16)), true)
        ;

        // region InternalMethods
        private final boolean dominant;
        private final int firstColor;
        private final int secondColor;

        Species(BeeSpeciesProperties builder, boolean dominant){
            this.dominant = dominant;
            this.firstColor = builder.firstColor;
            this.secondColor = builder.secondColor;
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
    }

}
