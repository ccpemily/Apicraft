package com.emily.apicraft.client.gui.menus;

import cofh.core.client.gui.ContainerScreenCoFH;
import com.emily.apicraft.inventory.containers.PortableAnalyzerContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
public class PortableAnalyzerScreen extends ContainerScreenCoFH<PortableAnalyzerContainer> {
    private final ResourceLocation TEXTURE_BACKGROUND = new ResourceLocation("apicraft:textures/gui/portable_analyzer/background.png");

    public PortableAnalyzerScreen(PortableAnalyzerContainer container, Inventory inv, Component titleIn) {
        super(container, inv, titleIn);
        texture = TEXTURE_BACKGROUND;
        imageWidth = 250;
        imageHeight = 237;
    }
}
