package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class ConditionRequireBlock implements IBeeCondition {
    private final List<Block> resourceType = new ArrayList<>();

    public ConditionRequireBlock(Block type){
        this.resourceType.add(type);
    }

    public ConditionRequireBlock(ITag<Block> tag){
        this.resourceType.addAll(tag.stream().toList());
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        BlockPos pos = beeHousing.getBeeHousingPos();
        do{
            pos = pos.below();
        }
        while(beeHousing.getBeeHousingLevel().getBlockEntity(pos) instanceof IBeeHousing);

        BlockPos finalPos = pos;
        return resourceType.contains(beeHousing.getBeeHousingLevel().getBlockState(finalPos).getBlock()) ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }

    @Override
    public IConditionType<?> getType() {
        return null;
    }

    @Override
    public IConditionSerializer<?> getSerializer() {
        return null;
    }
}
