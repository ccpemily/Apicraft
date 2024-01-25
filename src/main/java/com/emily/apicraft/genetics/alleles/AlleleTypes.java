package com.emily.apicraft.genetics.alleles;

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
    public String getRegistryName(){
        return this.name().toLowerCase(Locale.ENGLISH);
    }
    @Override
    public String getLocalizationKey(){
         return "allele." + this.getRegistryName() + ".class";
    }
    @Override
    public String getDescriptionKey(){
        return getLocalizationKey() + ".description";
    }
}
