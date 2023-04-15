package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;

import java.awt.*;
import java.util.Locale;

public class Chromosomes {
    public enum Species implements IChromosomeType {
        FOREST(BeeSpeciesProperties.get()
                .setColor(new Color(0x19d0ec), new Color(0xffdc16))
                , true);

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
            return tintIndex == 0 ? firstColor : secondColor;
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
}
