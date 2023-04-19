package com.emily.apicraft.client.gui.elements.tooltip;

import cofh.core.client.gui.element.ElementBase;
import cofh.core.client.gui.element.ITooltipFactory;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class AlleleTypeToolTip implements ITooltipFactory {
    private final IAlleleType type;

    public AlleleTypeToolTip(IAlleleType type){
        this.type = type;
    }

    @Override
    public List<Component> create(ElementBase element, int mouseX, int mouseY) {
        List<String> strings = List.of(Component.translatable(type.getDescription()).getString().split("\n"));
        return new ArrayList<>(strings.stream().map(Component::translatable).toList());
    }
}
