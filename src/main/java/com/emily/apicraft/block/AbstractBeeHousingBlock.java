package com.emily.apicraft.block;

import cofh.core.common.block.EntityBlock4Way;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public abstract class AbstractBeeHousingBlock extends EntityBlock4Way {

    public AbstractBeeHousingBlock(Properties builder, Class<?> tileClass, Supplier<BlockEntityType<?>> blockEntityType) {
        super(builder, tileClass, blockEntityType);
    }
}
