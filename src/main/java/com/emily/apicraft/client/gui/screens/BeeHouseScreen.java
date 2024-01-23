package com.emily.apicraft.client.gui.screens;

import com.emily.apicraft.inventory.menu.blockentity.BeeHouseMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BeeHouseScreen extends AbstractBeeHousingScreen<BeeHouseMenu> {
    public BeeHouseScreen(BeeHouseMenu menu, Inventory inv, Component titleIn) {
        super(menu, inv, menu.getBlockEntity(), titleIn);
        texture = new ResourceLocation("apicraft:textures/gui/beehousing/bee_house.png");
        imageWidth = 175;
        imageHeight = 189;
    }

    @Override
    public void init(){
        super.init();
    }
}
