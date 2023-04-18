package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.element.ElementBase;
import cofh.core.client.gui.element.ITooltipFactory;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public class TemperatureTooltip implements ITooltipFactory {
    @Override
    public List<Component> create(ElementBase element, int mouseX, int mouseY) {
        if(element instanceof ElementTemperatureInfo temperatureInfo) {
            return Collections.singletonList(Component.translatable(temperatureInfo.getTemperature().getName() + ".description"));
        }
        return Collections.emptyList();
    }
}
