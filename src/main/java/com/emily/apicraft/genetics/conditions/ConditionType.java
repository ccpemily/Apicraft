package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionType;
import net.minecraft.resources.ResourceLocation;

public class ConditionType<T extends IBeeCondition> implements IConditionType<T> {
    private final ResourceLocation registryName;

    public ConditionType(ResourceLocation location) {

        this.registryName = location;
    }

    public ConditionType(String modId, String name) {

        this.registryName = new ResourceLocation(modId, name);
    }

    @Override
    public String toString() {

        return registryName.toString();
    }
}
