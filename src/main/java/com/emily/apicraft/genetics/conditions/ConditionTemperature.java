package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ConditionTemperature implements IBeeCondition {
    private final EnumTemperature temperatureStart;
    private final EnumTemperature temperatureEnd;

    public ConditionTemperature(EnumTemperature start, EnumTemperature end){
        temperatureStart = start.ordinal() > end.ordinal() ? end : start;
        temperatureEnd = start.ordinal() > end.ordinal() ? start : end;
    }

    public EnumTemperature getTemperatureStart(){
        return temperatureStart;
    }

    public EnumTemperature getTemperatureEnd(){
        return temperatureEnd;
    }

    public boolean isRestrict(){
        return temperatureStart == temperatureEnd;
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        EnumTemperature temperature = beeHousing.getTemperature();
        return temperature.ordinal() <= temperatureEnd.ordinal() && temperature.ordinal() >= temperatureStart.ordinal() ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        if(temperatureStart == temperatureEnd){
            return List.of(
                    Component.translatable("tooltip.condition.temperature.restrict",
                            Component.translatable(temperatureStart.getName()).withStyle(ChatFormatting.GREEN)
                    )
            );
        }
        else{
            return List.of(
                    Component.translatable("tooltip.condition.temperature.range",
                        Component.translatable(temperatureStart.getName()).withStyle(ChatFormatting.GREEN),
                        Component.translatable(temperatureEnd.getName()).withStyle(ChatFormatting.GREEN)
                    )
            );
        }
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.TEMPERATURE.get();
    }
}
