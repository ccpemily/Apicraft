package com.emily.apicraft.climatology;

import net.minecraft.world.level.biome.Biome;

public class ClimateProvider {


    public EnumTemperature getTemperature(){
        return EnumTemperature.NONE;
    }

    public EnumHumidity getHumidity(){
        return EnumHumidity.NONE;
    }

    public Biome getBiome(){
        return null;
    }
}
