package com.emily.apicraft.client.gui.screens;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.panel.SecurityPanel;
import cofh.lib.util.helpers.SecurityHelper;
import com.emily.apicraft.block.entity.beehousing.AbstractBeeHousingBlockEntity;
import com.emily.apicraft.client.gui.elements.ElementBreedingProcess;
import com.emily.apicraft.client.gui.panels.ClimatePanel;
import com.emily.apicraft.client.gui.panels.ErrorPanel;
import com.emily.apicraft.inventory.menu.tile.AbstractBeeHousingMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractBeeHousingScreen<T extends AbstractBeeHousingMenu> extends ContainerScreenCoFH<T> {
    protected final AbstractBeeHousingBlockEntity blockEntity;
    protected int progressX = 20;
    protected int progressY = 37;
    public AbstractBeeHousingScreen(T menu, Inventory inv, AbstractBeeHousingBlockEntity blockEntity, Component titleIn) {
        super(menu, inv, titleIn);
        this.blockEntity = blockEntity;
    }

    @Override
    public void init(){
        super.init();
        addPanel(new ClimatePanel(this, blockEntity));
        addPanel(new SecurityPanel(this, blockEntity, SecurityHelper.getID(player)));
        addPanel(new ErrorPanel(this, blockEntity::getErrorState));
        addElement(new ElementBreedingProcess(this, progressX, progressY, blockEntity.getBreedingProcess())
                .setTexture("apicraft:textures/gui/element/breeding.png", 24, 46)
                .setSize(4, 46));
    }
}
