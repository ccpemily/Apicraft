package com.emily.apicraft.core.client.gui;

import cofh.core.client.gui.IGuiAccess;
import com.emily.apicraft.core.client.gui.elements.BreedingProcessStorage;
import com.emily.apicraft.core.client.gui.elements.ElementBreedingProcess;

public final class GuiHelper {
    private GuiHelper(){}

    public static ElementBreedingProcess createDefaultBreedingProcess(IGuiAccess gui, int posX, int posY, BreedingProcessStorage storage){
        return (ElementBreedingProcess) new ElementBreedingProcess(gui, posX, posY, storage)
                .setTexture("thermalapiculture:textures/gui/element/breeding.png", 24, 46)
                .setSize(4, 46);
    }
}
