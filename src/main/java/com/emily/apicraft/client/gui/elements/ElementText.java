package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import cofh.core.client.gui.element.ElementBase;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public class ElementText extends ElementBase {
    protected String text = "";
    protected int color = 0xffffff;

    public ElementText(IGuiAccess gui, int posX, int posY) {
        super(gui, posX, posY);
    }

    public ElementText setText(String text){
        return setText(text, 0xffffff);
    }

    public ElementText setText(String text, int color){
        this.text = text;
        this.color = color;
        return this;
    }

    @Override
    public void drawBackground(GuiGraphics gui, int mouseX, int mouseY){
        super.drawBackground(gui, mouseX, mouseY);
        if(visible()){
            gui.drawString(fontRenderer(), text, posX(), posY(), color);
        }
        this.width = fontRenderer().width(text);
        this.height = 12;
    }
}
