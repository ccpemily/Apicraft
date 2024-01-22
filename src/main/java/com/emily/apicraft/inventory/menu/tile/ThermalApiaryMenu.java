package com.emily.apicraft.inventory.menu.tile;


import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.container.slot.SlotRemoveOnly;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ThermalApiaryMenu extends AbstractBeeHousingMenu {
    public ThermalApiaryMenu(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(Registries.MENUS.get("thermal_apiary"), windowId, world, pos, inventory, player);
        addSlot(new SlotCoFH(this.invWrapper, 0, 29, 25));
        addSlot(new SlotCoFH(this.invWrapper, 1, 29, 51));
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
}
