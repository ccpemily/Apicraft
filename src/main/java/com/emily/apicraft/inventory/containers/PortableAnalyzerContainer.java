package com.emily.apicraft.inventory.containers;

import cofh.core.inventory.container.ContainerCoFH;
import cofh.lib.inventory.container.slot.SlotLocked;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PortableAnalyzerContainer extends ContainerCoFH {
    public ItemStack containerStack;
    public SlotLocked lockedSlot;
    public PortableAnalyzerContainer(int id, Inventory inventory, Player player) {
        super(Registries.CONTAINERS.get("portable_analyzer"), id, inventory, player);
        containerStack = player.getMainHandItem();
        bindPlayerInventory(inventory);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 0;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return lockedSlot.getItem() == containerStack;
    }

    @Override
    protected void bindPlayerInventory(Inventory inventory) {

        int xOffset = getPlayerInventoryHorizontalOffset() + 39;
        int yOffset = getPlayerInventoryVerticalOffset() + 72;

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
}
