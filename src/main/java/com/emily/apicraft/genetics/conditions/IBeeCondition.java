package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IBeeCondition {
    float applyModifier(IBeeHousing beeHousing, float chance);
    List<Component> getConditionTooltip();
    IConditionType<?> getType();
}
