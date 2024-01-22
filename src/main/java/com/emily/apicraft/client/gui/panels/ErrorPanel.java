package com.emily.apicraft.client.gui.panels;

import cofh.core.client.gui.IGuiAccess;
import cofh.core.client.gui.element.panel.PanelBase;
import com.emily.apicraft.core.lib.ErrorStates;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public class ErrorPanel extends PanelBase {
    public static int defaultSide = RIGHT;
    public static int defaultHeaderColor = 0xe1c92f;
    public static int defaultSubHeaderColor = 0xaaafb8;
    public static int defaultTextColor = 0x101010;
    public static int defaultBackgroundColor = 0x35a4ff;
    private final Supplier<ErrorStates> supplier;
    public ErrorPanel(IGuiAccess gui, Supplier<ErrorStates> supplier) {
        super(gui, RIGHT);
        headerColor = defaultHeaderColor;
        subheaderColor = defaultSubHeaderColor;
        textColor = defaultTextColor;
        backgroundColor = defaultBackgroundColor;

        maxHeight = 72;
        maxWidth = 112;
        this.supplier = supplier;
        visible = () -> supplier.get() != ErrorStates.NONE;
    }
    @Override
    protected void drawForeground(GuiGraphics guiGraphics) {
        drawPanelIcon(guiGraphics, supplier.get().getIcon());
        if (!fullyOpen) {
            return;
        }
        guiGraphics.drawString(fontRenderer(), Component.translatable(supplier.get().getName()), sideOffset() + 22, 8, headerColor);
        List<Component> components = supplier.get().getTooltips();
        for(int i = 0; i < components.size(); i++){
            guiGraphics.drawString(fontRenderer(), components.get(i), sideOffset() + 22, 20 + 12 * i, 0xffffff);
        }
    }

    @Override
    public void addTooltip(List<Component> tooltipList, int mouseX, int mouseY) {
        if (!fullyOpen) {
            tooltipList.add(Component.translatable(supplier.get().getName()));
            tooltipList.addAll(supplier.get().getTooltips());
        }
    }
}
