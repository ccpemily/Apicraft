package com.emily.apicraft.interfaces.genetics;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface IChromosomeType {
    @Override
    String toString();
    boolean isDominant();
    List<Component> getDescriptionTooltips();
}
