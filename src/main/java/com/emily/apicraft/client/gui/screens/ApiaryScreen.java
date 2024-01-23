package com.emily.apicraft.client.gui.screens;

import com.emily.apicraft.inventory.menu.blockentity.ApiaryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ApiaryScreen extends AbstractBeeHousingScreen<ApiaryMenu> {
    public ApiaryScreen(ApiaryMenu menu, Inventory inv, Component titleIn) {
        super(menu, inv, menu.getBlockEntity(), titleIn);
        texture = new ResourceLocation("apicraft:textures/gui/beehousing/apiary.png");
        imageWidth = 175;
        imageHeight = 189;
    }

    @Override
    public void init(){
        super.init();
    }
}
