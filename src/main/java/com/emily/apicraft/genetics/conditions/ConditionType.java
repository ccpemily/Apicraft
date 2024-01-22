package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ConditionType<T extends IBeeCondition> implements IConditionType<T> {
    private final ResourceLocation registryName;
    private final Supplier<IConditionSerializer<T>> serializer;

    public ConditionType(ResourceLocation location, Supplier<IConditionSerializer<T>> serializer) {
        this.serializer = serializer;
        this.registryName = location;
    }

    public ConditionType(String modId, String name, Supplier<IConditionSerializer<T>> serializer) {
        this.serializer = serializer;
        this.registryName = new ResourceLocation(modId, name);
    }

    @Override
    public String toString() {
        return registryName.toString();
    }

    @Override
    public Supplier<IConditionSerializer<T>> getSerializer() {
        return serializer;
    }
}
