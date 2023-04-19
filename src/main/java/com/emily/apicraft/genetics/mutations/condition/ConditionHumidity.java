package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IMutationCondition;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

public class ConditionHumidity implements IMutationCondition {
    private final EnumHumidity humidityStart;
    private final EnumHumidity humidityEnd;

    public ConditionHumidity(EnumHumidity start, EnumHumidity end){
        humidityStart = start.ordinal() > end.ordinal() ? end : start;
        humidityEnd = start.ordinal() > end.ordinal() ? start : end;
    }
    @Override
    public Function<Float, Float> getModifier(IBeeHousing beeHousing) {
        EnumHumidity humidity = beeHousing.getHumidity();
        return (x) -> humidity.ordinal() <= humidityEnd.ordinal() && humidity.ordinal() >= humidityStart.ordinal() ? x : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
