package com.emily.apicraft.block.entity.beehousing;

import com.emily.apicraft.inventory.menu.tile.ThermalApiaryMenu;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThermalApiaryEntity extends AbstractBeeHousingBlockEntity{
    public ThermalApiaryEntity(BlockPos pos, BlockState state) {
        super(Registries.TILE_ENTITIES.get("thermal_apiary"), pos, state, 7, 3, 4, 3);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("tile.thermal_apiary.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new ThermalApiaryMenu(i, level, pos(), inventory, player);
    }
}
