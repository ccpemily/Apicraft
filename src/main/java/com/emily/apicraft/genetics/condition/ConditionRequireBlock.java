package com.emily.apicraft.genetics.condition;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.IBeeCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ConditionRequireBlock implements IBeeCondition {
    private final Block resourceType;

    public ConditionRequireBlock(Block type){
        this.resourceType = type;
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        BlockPos pos = beeHousing.getBeeHousingPos();
        do{
            pos = pos.below();
        }
        while(beeHousing.getBeeHousingLevel().getBlockEntity(pos) instanceof IBeeHousing);

        BlockPos finalPos = pos;
        return beeHousing.getBeeHousingLevel().getBlockState(finalPos).getBlock() == resourceType ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
