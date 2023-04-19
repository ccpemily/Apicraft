package com.emily.apicraft.inventory.menu.tile;

import cofh.core.inventory.container.TileContainer;
import com.emily.apicraft.block.entity.ApiaryEntity;
import com.emily.apicraft.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ApiaryMenu extends TileContainer {
    public final ApiaryEntity apiary;

    public ApiaryMenu(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(Registries.MENUS.get("apiary"), windowId, world, pos, inventory, player);
        this.apiary = (ApiaryEntity) world.getBlockEntity(pos);
    }
}
