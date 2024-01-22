package com.emily.apicraft.client.gui.screens;

import com.emily.apicraft.inventory.menu.tile.ThermalApiaryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ThermalApiaryScreen extends AbstractBeeHousingScreen<ThermalApiaryMenu> {
    public ThermalApiaryScreen(ThermalApiaryMenu menu, Inventory inv, Component titleIn) {
        super(menu, inv, menu.getBlockEntity(), titleIn);
        texture = new ResourceLocation("apicraft:textures/gui/beehousing/thermal_apiary.png");
        imageWidth = 175;
        imageHeight = 189;
        progressY = 24;
    }

    @Override
    public void init() {
        super.init();
    }
}
