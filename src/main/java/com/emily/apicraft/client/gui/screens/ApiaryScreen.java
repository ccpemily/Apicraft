package com.emily.apicraft.client.gui.screens;

import cofh.core.client.gui.ContainerScreenCoFH;
import com.emily.apicraft.inventory.menu.tile.ApiaryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ApiaryScreen extends ContainerScreenCoFH<ApiaryMenu> {
    public ApiaryScreen(ApiaryMenu menu, Inventory inv, Component titleIn) {
        super(menu, inv, titleIn);
    }
}
