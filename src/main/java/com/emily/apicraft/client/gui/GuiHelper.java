package com.emily.apicraft.client.gui;

import cofh.core.client.gui.IGuiAccess;
import com.emily.apicraft.client.gui.elements.ElementBreedingProcess;
import com.emily.apicraft.client.gui.elements.BreedingProcessStorage;

public final class GuiHelper {
    private GuiHelper(){}

    public static ElementBreedingProcess createDefaultBreedingProcess(IGuiAccess gui, int posX, int posY, BreedingProcessStorage storage){
        return (ElementBreedingProcess) new ElementBreedingProcess(gui, posX, posY, storage)
                .setTexture("apicraft:textures/gui/element/breeding.png", 24, 46)
                .setSize(4, 46);
    }
}
