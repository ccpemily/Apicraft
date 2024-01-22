package com.emily.apicraft.inventory.menu.tile;

import cofh.lib.common.inventory.SlotCoFH;
import cofh.lib.common.inventory.SlotRemoveOnly;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ApiaryMenu extends AbstractBeeHousingMenu {

    public ApiaryMenu(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(Registries.MENUS.get("apiary"), windowId, world, pos, inventory, player);
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
        // Frame slots
        for(int i = 0; i < 3; i++){
            addSlot(new SlotCoFH(this.invWrapper, 9 + i, 66, 23 + i * 29));
        }
        bindPlayerInventory(inventory);
    }

    @Override
    protected int getPlayerInventoryVerticalOffset() {
        return 108;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
