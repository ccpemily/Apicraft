package com.emily.apicraft.climatology;

public interface IClimateModifierProvider {
    int applyTemperatureModifier(int val);
    int applyHumidityModifier(int val);
}
