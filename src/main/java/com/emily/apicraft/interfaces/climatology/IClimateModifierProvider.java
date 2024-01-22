package com.emily.apicraft.interfaces.climatology;

public interface IClimateModifierProvider {
    int applyTemperatureModifier(int val);
    int applyHumidityModifier(int val);
}
