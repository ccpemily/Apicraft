package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ConditionHumidity implements IBeeCondition {
    private final EnumHumidity humidityStart;
    private final EnumHumidity humidityEnd;

    public ConditionHumidity(EnumHumidity start, EnumHumidity end){
        humidityStart = start.ordinal() > end.ordinal() ? end : start;
        humidityEnd = start.ordinal() > end.ordinal() ? start : end;
    }

    public EnumHumidity getHumidityStart(){
        return humidityStart;
    }

    public EnumHumidity getHumidityEnd(){
        return humidityEnd;
    }

    public boolean isRestrict(){
        return humidityStart == humidityEnd;
    }
    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        EnumHumidity humidity = beeHousing.getHumidity();
        return humidity.ordinal() <= humidityEnd.ordinal() && humidity.ordinal() >= humidityStart.ordinal() ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        if(humidityStart == humidityEnd){
            return List.of(
                    Component.translatable("tooltip.condition.humidity.restrict",
                            Component.translatable(humidityStart.getName()).withStyle(ChatFormatting.AQUA)
                    )
            );
        }
        else{
            return List.of(
                    Component.translatable("tooltip.condition.humidity.range",
                            Component.translatable(humidityStart.getName()).withStyle(ChatFormatting.AQUA),
                            Component.translatable(humidityEnd.getName()).withStyle(ChatFormatting.AQUA)
                    )
            );
        }
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.HUMIDITY.get();
    }
}
