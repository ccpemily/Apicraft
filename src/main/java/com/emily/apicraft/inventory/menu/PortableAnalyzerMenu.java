package com.emily.apicraft.inventory.menu;

import cofh.core.inventory.container.ContainerCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.container.slot.SlotLocked;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import com.emily.apicraft.items.BeeItem;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortableAnalyzerMenu extends ContainerCoFH {
    public ItemStack containerStack;
    public SlotLocked lockedSlot;
    public SlotCoFH beeSlot;
    public SimpleItemInv inv;
    public InvWrapperCoFH invWrapper;

    public PortableAnalyzerMenu(int id, Inventory inventory, Player player) {
        super(Registries.MENUS.get("portable_analyzer"), id, inventory, player);
        containerStack = player.getMainHandItem();
        inv = new SimpleItemInv(List.of(new ItemStorageCoFH((stack -> stack.getItem() instanceof BeeItem))));
        invWrapper = new InvWrapperCoFH(inv);
        beeSlot = new SlotCoFH(invWrapper, 0, 227, 8);
        addSlot(beeSlot);
        bindPlayerInventory(inventory);
    }

    // region ContainerCoFH
    @Override
    protected int getMergeableSlotCount() {
        return invWrapper.getContainerSize();
    }

    @Override
    public int getPlayerInventoryHorizontalOffset(){
        return super.getPlayerInventoryHorizontalOffset() + 39;
    }

    @Override
    public int getPlayerInventoryVerticalOffset(){
        return super.getPlayerInventoryVerticalOffset() + 72;
    }
    @Override
    protected void bindPlayerInventory(Inventory inventory) {
        int xOffset = getPlayerInventoryHorizontalOffset();
        int yOffset = getPlayerInventoryVerticalOffset();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            if (i == inventory.selected) {
                lockedSlot = new SlotLocked(inventory, i, xOffset + i * 18, yOffset + 58);
                addSlot(lockedSlot);
            } else {
                addSlot(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
            }
        }
    }
    // endregion

    // region AbstractContainerMenu
    @Override
    public boolean stillValid(@NotNull Player player) {
        return lockedSlot.getItem() == containerStack;
    }

    @Override
    public void removed(@NotNull Player player){
        super.removed(player);
        this.clearContainer(player, invWrapper);
    }

    // endregion
}
