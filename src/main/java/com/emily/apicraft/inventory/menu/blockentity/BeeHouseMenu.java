package com.emily.apicraft.inventory.menu.blockentity;

import cofh.lib.common.inventory.SlotCoFH;
import cofh.lib.common.inventory.SlotRemoveOnly;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BeeHouseMenu extends AbstractBeeHousingMenu {
    public BeeHouseMenu(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(Registries.MENUS.get("bee_house"), windowId, world, pos, inventory, player);
        addSlot(new SlotCoFH(this.invWrapper, 0, 29, 39));
        addSlot(new SlotCoFH(this.invWrapper, 1, 29, 65));
        // Product slots
        addSlot(new SlotRemoveOnly(this.invWrapper, 2, 116, 52));
        addSlot(new SlotRemoveOnly(this.invWrapper, 3, 137, 39));
        addSlot(new SlotRemoveOnly(this.invWrapper, 4, 137, 65));
        addSlot(new SlotRemoveOnly(this.invWrapper, 5, 116, 78));
        addSlot(new SlotRemoveOnly(this.invWrapper, 6, 95, 65));
        addSlot(new SlotRemoveOnly(this.invWrapper, 7, 95, 39));
        addSlot(new SlotRemoveOnly(this.invWrapper, 8, 116, 26));
        bindPlayerInventory(inventory);
    }

    @Override
    protected int getPlayerInventoryVerticalOffset() {
        return 108;
    }
}
