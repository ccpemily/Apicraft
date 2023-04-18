package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.function.Supplier;

public class ElementAlleleInfo extends ElementText{
    private static final int DOMINANT_COLOR = 0xec3661;
    private static final int RECESSIVE_COLOR = 0x3687ec;

    private final Supplier<Optional<Bee>> beeSupplier;
    private final Class<? extends IChromosomeType> type;
    private final boolean active;

    public ElementAlleleInfo(IGuiAccess gui, int posX, int posY, Supplier<Optional<Bee>> supplier, Class<? extends IChromosomeType> type, boolean active) {
        super(gui, posX, posY);
        this.beeSupplier = supplier;
        this.type = type;
        this.active = active;
    }

    @Override
    public void drawBackground(PoseStack stack, int mouseX, int mouseY) {
        Optional<Bee> beeOptional = beeSupplier.get();
        if(beeOptional.isPresent()){
            IChromosomeType chromosome = beeOptional.get().getGenome().getChromosomeValue(type, active);
            setText(Component.translatable("chromosomes." + chromosome.toString()).getString(), getColor(chromosome.isDominant()));
            super.drawBackground(stack, mouseX, mouseY);
        }
    }

    public Optional<IChromosomeType> getCurrentChromosome(){
        if(beeSupplier.get().isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(beeSupplier.get().get().getGenome().getChromosomeValue(type, active));
        }
    }

    private int getColor(boolean dominant){
        return dominant ? DOMINANT_COLOR : RECESSIVE_COLOR;
    }
}
