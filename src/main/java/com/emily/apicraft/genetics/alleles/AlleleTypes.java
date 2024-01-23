package com.emily.apicraft.genetics.alleles;

import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.IAlleleType;

import java.util.Locale;

public enum AlleleTypes implements IAlleleType {
    SPECIES,
    LIFESPAN, PRODUCTIVITY, FERTILITY,
    BEHAVIOR,
    RAIN_TOLERANCE, CAVE_DWELLING,
    ACCEPTED_FLOWERS,
    TEMPERATURE_TOLERANCE, HUMIDITY_TOLERANCE,
    TERRITORY,
    EFFECT;

    @Override
    public boolean isValid(IAllele<?> allele) {
        return this == allele.getType();
    }
    @Override
    public String toString(){
        return this.name().toLowerCase(Locale.ENGLISH);
    }
    @Override
    public String getName(){
         return "allele." + this + ".class";
    }
    @Override
    public String getDescription(){
        return getName() + ".description";
    }
}
