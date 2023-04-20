package com.emily.apicraft.client.gui.screens;

import cofh.core.client.gui.ContainerScreenCoFH;
import com.emily.apicraft.block.entity.ApiaryEntity;
import com.emily.apicraft.client.gui.panels.ClimatePanel;
import com.emily.apicraft.inventory.menu.tile.ApiaryMenu;
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
