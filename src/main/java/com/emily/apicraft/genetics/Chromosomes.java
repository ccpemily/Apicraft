package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;

import java.util.Locale;

public class Chromosomes {
    public enum Species implements IChromosomeType {
        FOREST;
        @Override
        public String toString(){
            return "species." + this.name().toLowerCase(Locale.ENGLISH);
        }
    }
    public enum LifeSpan implements IChromosomeType {
        HYPER, RAPID, SHORT, NORMAL, LONG, ANCIENT, ETERNAL;
        @Override
        public String toString(){
            return "lifespan." + this.name().toLowerCase(Locale.ENGLISH);
        }
    }

    public enum Productivity implements IChromosomeType {
        SLUGGISH, SLOWEST, SLOW, NORMAL, FAST, FASTEST, BRISK;
        @Override
        public String toString(){
            return "productivity." + this.name().toLowerCase(Locale.ENGLISH);
        }
    }
}
