package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionType;
import com.emily.apicraft.recipes.conditions.Conditions;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Objects;

public class ConditionOwnerName implements IBeeCondition {
    private final String name;
    public ConditionOwnerName(String name){
        this.name = name;
    }
    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        return Objects.equals(beeHousing.getBeeHousingOwnerName(), name) ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.REQUIRE_PLAYER.get();
    }
}
