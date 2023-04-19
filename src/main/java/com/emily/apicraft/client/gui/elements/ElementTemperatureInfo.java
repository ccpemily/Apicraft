package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.function.Supplier;

public class ElementTemperatureInfo extends ElementText{
    private static final int DOMINANT_COLOR = 0xec3661;
    private static final int RECESSIVE_COLOR = 0x3687ec;
    private final Supplier<Optional<Bee>> supplier;
    private boolean active;

    public ElementTemperatureInfo(IGuiAccess gui, int posX, int posY, Supplier<Optional<Bee>> supplier, boolean active) {
        super(gui, posX, posY);
        this.supplier = supplier;
    }

    @Override
    public void drawBackground(PoseStack stack, int mouseX, int mouseY) {
        Optional<Bee> beeOptional = supplier.get();
        if(beeOptional.isPresent()){
            Alleles.Species allele = active ? beeOptional.get().getGenome().getSpecies() : beeOptional.get().getGenome().getInactiveSpecies();
            setText(Component.translatable(allele.getValue().getTemperature().getName()).getString(), getColor(allele.isDominant()));
            super.drawBackground(stack, mouseX, mouseY);
        }
    }

    public EnumTemperature getTemperature(){
        Optional<Bee> beeOptional = supplier.get();
        if(beeOptional.isPresent()) {
            Alleles.Species allele = active ? beeOptional.get().getGenome().getSpecies() : beeOptional.get().getGenome().getInactiveSpecies();
            return allele.getValue().getTemperature();
        }
        return EnumTemperature.NONE;
    }

    private int getColor(boolean dominant){
        return dominant ? DOMINANT_COLOR : RECESSIVE_COLOR;
    }
}
