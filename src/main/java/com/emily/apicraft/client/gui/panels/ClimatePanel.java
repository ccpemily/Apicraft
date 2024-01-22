package com.emily.apicraft.client.gui.panels;

import cofh.core.client.gui.IGuiAccess;
import cofh.core.client.gui.element.panel.PanelBase;
import com.emily.apicraft.interfaces.climatology.IClimateProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClimatePanel extends PanelBase {
    public static int defaultSide = RIGHT;
    public static int defaultHeaderColor = 0xe1c92f;
    public static int defaultSubHeaderColor = 0xaaafb8;
    public static int defaultTextColor = 0x101010;
    public static int defaultBackgroundColor = 0x35a4ff;
    private final IClimateProvider provider;
    public ClimatePanel(IGuiAccess gui, IClimateProvider provider) {
        super(gui, defaultSide);
        headerColor = defaultHeaderColor;
        subheaderColor = defaultSubHeaderColor;
        textColor = defaultTextColor;
        backgroundColor = defaultBackgroundColor;

        maxHeight = 72;
        maxWidth = 112;
        this.provider = provider;
    }

    @Override
    protected void drawForeground(GuiGraphics guiGraphics) {
        drawPanelIcon(guiGraphics, provider.getTemperature().getIcon());
        if(!fullyOpen){
            return;
        }
        guiGraphics.drawString(fontRenderer(), Component.translatable("gui.climate.header"), sideOffset() + 22, 8, headerColor);
        guiGraphics.drawString(fontRenderer(),
                Component.translatable("gui.climate.temperature").append(":")
                , sideOffset() + 22, 20, subheaderColor);
        guiGraphics.drawString(fontRenderer(),
                Component.translatable(provider.getTemperature().getName())
                        .append(" " + provider.getExactTemperature() + "%")
                ,
                sideOffset() + 22, 32, 0xffffff
        );
        guiGraphics.drawString(fontRenderer(),
                Component.translatable("gui.climate.humidity").append(":")
                , sideOffset() + 22, 44, subheaderColor);
        guiGraphics.drawString(fontRenderer(),
                Component.translatable(provider.getHumidity().getName())
                        .append(" " + provider.getExactHumidity() + "%")
                ,
                sideOffset() + 22, 56, 0xffffff
        );
    }

    @Override
    public void addTooltip(List<Component> tooltipList, int mouseX, int mouseY) {
        if (!fullyOpen) {
            tooltipList.add(Component.translatable("gui.climate.tooltip"));
            tooltipList.add(Component.literal("T: ")
                    .append(Component.translatable(provider.getTemperature().getName()))
                    .append(Component.literal(" / H: "))
                    .append(Component.translatable(provider.getHumidity().getName()))
                    .withStyle(ChatFormatting.YELLOW)
            );
        }
    }
}
