package com.emily.apicraft.inventory.containers;

import cofh.core.inventory.container.ContainerCoFH;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PortableAnalyzerContainer extends ContainerCoFH {
    public PortableAnalyzerContainer(@Nullable MenuType<?> type, int id, Inventory inventory, Player player) {
        super(type, id, inventory, player);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 0;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return false;
    }
}
