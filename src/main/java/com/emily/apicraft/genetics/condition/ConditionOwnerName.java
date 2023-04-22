package com.emily.apicraft.genetics.condition;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.IBeeCondition;
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
}
