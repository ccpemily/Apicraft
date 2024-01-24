package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.emily.apicraft.recipes.conditions.Conditions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ConditionRequireBlock implements IBeeCondition {
    private final Set<Block> resourceType = new HashSet<>();
    private final Set<TagKey<Block>> tags = new HashSet<>();

    public ConditionRequireBlock(Collection<Block> blocks, Collection<TagKey<Block>> tags){
        this.resourceType.addAll(blocks);
        this.tags.addAll(tags);
    }

    public Set<Block> getAcceptedBlocks(){
        return resourceType;
    }

    public Set<TagKey<Block>> getAcceptedTags(){
        return tags;
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        BlockPos pos = beeHousing.getBeeHousingPos();
        do{
            pos = pos.below();
        }
        while(beeHousing.getBeeHousingLevel().getBlockEntity(pos) instanceof IBeeHousing);

        BlockPos finalPos = pos;
        Block block = beeHousing.getBeeHousingLevel().getBlockState(finalPos).getBlock();
        if(block == Blocks.AIR || block == Blocks.CAVE_AIR){
            return 0;
        }
        boolean containedByTag = false;
        for(var tag : tags){
            if(Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(tag).contains(block)){
                containedByTag = true;
                break;
            }
        }
        return (containedByTag || resourceType.contains(block)) ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return List.of(Component.translatable("tooltip.condition.require_block"));
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.REQUIRE_BLOCK.get();
    }
}
