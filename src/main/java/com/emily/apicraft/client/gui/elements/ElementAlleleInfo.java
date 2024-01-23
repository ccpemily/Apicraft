package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.IAlleleType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.function.Supplier;

public class ElementAlleleInfo extends ElementText{
    private static final int DOMINANT_COLOR = 0xec3661;
    private static final int RECESSIVE_COLOR = 0x3687ec;

    protected final Supplier<Optional<Bee>> beeSupplier;
    protected final IAlleleType type;
    protected final boolean active;

    public ElementAlleleInfo(IGuiAccess gui, int posX, int posY, Supplier<Optional<Bee>> supplier, IAlleleType type, boolean active) {
        super(gui, posX, posY);
        this.beeSupplier = supplier;
        this.type = type;
        this.active = active;
    }

    @Override
    public void drawBackground(GuiGraphics gui, int mouseX, int mouseY) {
        Optional<Bee> beeOptional = beeSupplier.get();
        if(beeOptional.isPresent()){
            IAllele<?> allele = beeOptional.get().getGenome().getAllele(type, active);
            setText(Component.translatable(allele.getName()).getString(), getColor(allele.isDominant()));
            super.drawBackground(gui, mouseX, mouseY);
        }
    }

    public Optional<IAllele<?>> getCurrentChromosome(){
        if(beeSupplier.get().isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(beeSupplier.get().get().getGenome().getAllele(type, active));
        }
    }

    public Optional<Bee> getBee(){
        return beeSupplier.get();
    }

    public boolean isActive(){
        return active;
    }

    protected int getColor(boolean dominant){
        return dominant ? DOMINANT_COLOR : RECESSIVE_COLOR;
    }
}
