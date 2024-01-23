package com.emily.apicraft.interfaces.genetics;

import net.minecraft.core.Vec3i;

import java.util.function.Function;

public interface IBeeModifierProvider {

    float applyProductivityModifier(float val);
    float applyLifespanModifier(float val);
    float applyMutationModifier(float val);
    Vec3i applyTerritoryModifier(Vec3i val);
    int applyFertilityModifier(int val);
}
