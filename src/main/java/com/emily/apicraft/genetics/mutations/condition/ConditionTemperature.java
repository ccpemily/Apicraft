package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IMutationCondition;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

public class ConditionTemperature implements IMutationCondition {
    private final EnumTemperature temperatureStart;
    private final EnumTemperature temperatureEnd;

    public ConditionTemperature(EnumTemperature start, EnumTemperature end){
        temperatureStart = start.ordinal() > end.ordinal() ? end : start;
        temperatureEnd = start.ordinal() > end.ordinal() ? start : end;
    }

    @Override
    public Function<Float, Float> getModifier(IBeeHousing beeHousing) {
        EnumTemperature temperature = beeHousing.getTemperature();
        return (x) -> temperature.ordinal() <= temperatureEnd.ordinal() && temperature.ordinal() >= temperatureStart.ordinal() ? x : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
