package com.emily.apicraft.block.entity;

import com.emily.apicraft.inventory.menu.tile.ApiaryMenu;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiaryEntity extends AbstractBeeHousingBlockEntity {
    public ApiaryEntity(BlockPos pos, BlockState state) {
        super(Registries.TILE_ENTITIES.get("apiary"), pos, state, 7, 3, 0);
    }

    // region IMenuProvider
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("tile.apiary.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new ApiaryMenu(i, level, pos(), inventory, player);
    }
    // endregion
}
