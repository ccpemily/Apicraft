package com.emily.apicraft.interfaces.genetics.mutations;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

public interface IMutationCondition {
    Function<Float, Float> getModifier(IBeeHousing beeHousing);
    List<Component> getConditionTooltip();
}
