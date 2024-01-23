package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.emily.apicraft.recipes.conditions.Conditions;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ConditionTemperature implements IBeeCondition {
    private final EnumTemperature temperatureStart;
    private final EnumTemperature temperatureEnd;

    public ConditionTemperature(EnumTemperature start, EnumTemperature end){
        temperatureStart = start.ordinal() > end.ordinal() ? end : start;
        temperatureEnd = start.ordinal() > end.ordinal() ? start : end;
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        EnumTemperature temperature = beeHousing.getTemperature();
        return temperature.ordinal() <= temperatureEnd.ordinal() && temperature.ordinal() >= temperatureStart.ordinal() ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.TEMPERATURE.get();
    }
}
