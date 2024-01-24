package com.emily.apicraft.genetics.conditions;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface IConditionType<T extends IBeeCondition> {
    Supplier<IConditionSerializer<T>> getSerializer();
    // Returns the registry name of condition type
    ResourceLocation getResourceLocation();
}
