package com.emily.apicraft.inventory.menu.tile;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import com.emily.apicraft.block.entity.beehousing.AbstractBeeHousingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBeeHousingMenu extends TileContainer {
    protected final AbstractBeeHousingBlockEntity beehousing;
    protected InvWrapperCoFH invWrapper;

    public AbstractBeeHousingMenu(@Nullable MenuType<?> type, int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(type, windowId, world, pos, inventory, player);
        this.beehousing = (AbstractBeeHousingBlockEntity) world.getBlockEntity(pos);
        if(beehousing == null){
            throw new IllegalArgumentException("Null block entity found.");
        }
        beehousing.clearCachedValue();
        this.invWrapper = new InvWrapperCoFH(beehousing.getBeeHousingInv());
    }

    public AbstractBeeHousingBlockEntity getBlockEntity(){
        return beehousing;
    }

    public InvWrapperCoFH getInventory(){
        return invWrapper;
    }
    @Override
    protected int getMergeableSlotCount(){
        return invWrapper.getContainerSize();
    }
}
