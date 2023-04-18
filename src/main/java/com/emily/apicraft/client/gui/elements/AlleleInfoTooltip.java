package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.element.ElementBase;
import cofh.core.client.gui.element.ITooltipFactory;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public class AlleleInfoTooltip implements ITooltipFactory {

    @Override
    public List<Component> create(ElementBase element, int mouseX, int mouseY) {
        if(element instanceof ElementAlleleInfo){
            if(!element.visible() || ((ElementAlleleInfo) element).getCurrentChromosome().isEmpty()){
                return Collections.emptyList();
            }
            else{
                return ((ElementAlleleInfo) element).getCurrentChromosome().get().getDescriptionTooltips();
            }
        }
        return Collections.emptyList();
    }
}
