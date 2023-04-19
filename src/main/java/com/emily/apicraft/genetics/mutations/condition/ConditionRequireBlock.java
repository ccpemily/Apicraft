package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IMutationCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Function;

public class ConditionRequireBlock implements IMutationCondition {
    private final Block resourceType;

    public ConditionRequireBlock(Block type){
        this.resourceType = type;
    }

    @Override
    public Function<Float, Float> getModifier(IBeeHousing beeHousing) {
        BlockPos pos = beeHousing.getBeeHousingPos();
        do{
            pos = pos.below();
        }
        while(beeHousing.getBeeHousingLevel().getBlockEntity(pos) instanceof IBeeHousing);

        BlockPos finalPos = pos;
        return (x) -> beeHousing.getBeeHousingLevel().getBlockState(finalPos).getBlock() == resourceType ? x : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
