package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Objects;

public class ConditionOwnerName implements IBeeCondition {
    private final String name;
    public ConditionOwnerName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        return Objects.equals(beeHousing.getBeeHousingOwnerName(), name) ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return List.of(Component.translatable("tooltip.condition.require_player", Component.literal(name).withStyle(ChatFormatting.YELLOW)));
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.REQUIRE_PLAYER.get();
    }
}
