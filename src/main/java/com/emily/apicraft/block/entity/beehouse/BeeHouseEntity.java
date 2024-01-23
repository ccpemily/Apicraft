package com.emily.apicraft.block.entity.beehouse;

import com.emily.apicraft.inventory.menu.blockentity.BeeHouseMenu;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeeHouseEntity extends AbstractBeeHousingBlockEntity {
    public BeeHouseEntity(BlockPos pos, BlockState state) {
        super(Registries.TILE_ENTITIES.get("bee_house"), pos, state, 7, 0, 0, 1);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("tile.bee_house.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new BeeHouseMenu(i, level, pos(), inventory, player);
    }
}
