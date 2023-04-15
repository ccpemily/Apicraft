package com.emily.apicraft.core.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import cofh.core.client.gui.element.ElementResourceStorage;
import cofh.core.util.helpers.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ElementBreedingProcess extends ElementResourceStorage {
    private final static int LAYERS = 6;
    public ElementBreedingProcess(IGuiAccess gui, int posX, int posY, BreedingProcessStorage storage) {
        super(gui, posX, posY, storage);
        clearable = () -> true;
    }

    @Override
    protected void drawResource(PoseStack poseStack) {
        RenderHelper.setShaderTexture0(texture);
        int resourceHeight = height;
        if(storage.getCapacity() == 0){
            drawTexturedModalRect(poseStack, posX(), posY() + resourceHeight, width, resourceHeight, width, 0);
            return;
        }
        int amount = getScaled(resourceHeight);
        int u = (texW / LAYERS) * (int)(Math.ceil((double)storage.getStored() * (LAYERS - 1) / storage.getCapacity()));
        drawTexturedModalRect(poseStack, posX(), posY() + resourceHeight - amount, u, resourceHeight - amount, width, amount);
    }

    @Override
    public void addTooltip(List<Component> tooltipList, int mouseX, int mouseY){
        if(storage.getCapacity() == 0){
            tooltipList.add(Component.translatable("gui.tooltip.bee_housing_no_bee"));
        }
        else super.addTooltip(tooltipList, mouseX, mouseY);
    }
}
