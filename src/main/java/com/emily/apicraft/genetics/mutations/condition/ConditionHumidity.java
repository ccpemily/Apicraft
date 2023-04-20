package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IBeeCondition;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ConditionHumidity implements IBeeCondition {
    private final EnumHumidity humidityStart;
    private final EnumHumidity humidityEnd;

    public ConditionHumidity(EnumHumidity start, EnumHumidity end){
        humidityStart = start.ordinal() > end.ordinal() ? end : start;
        humidityEnd = start.ordinal() > end.ordinal() ? start : end;
    }
    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        EnumHumidity humidity = beeHousing.getHumidity();
        return humidity.ordinal() <= humidityEnd.ordinal() && humidity.ordinal() >= humidityStart.ordinal() ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
