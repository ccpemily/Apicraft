package com.emily.apicraft.interfaces.genetics.conditions;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IBeeCondition {
    float applyModifier(IBeeHousing beeHousing, float chance);
    List<Component> getConditionTooltip();
    IConditionType<?> getType();
}
