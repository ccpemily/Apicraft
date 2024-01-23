package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.IGuiAccess;
import cofh.core.util.helpers.RenderHelper;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.IAlleleType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.Supplier;

public class ElementToleranceInfo extends ElementAlleleInfo{
    public ElementToleranceInfo(IGuiAccess gui, int posX, int posY, Supplier<Optional<Bee>> supplier, IAlleleType type, boolean active) {
        super(gui, posX, posY, supplier, type, active);
        texture = new ResourceLocation("apicraft:textures/gui/portable_analyzer/tolerance.png");
        texW = 64;
        texH = 16;
    }

    @Override
    public void drawBackground(GuiGraphics gui, int mouseX, int mouseY) {
        Optional<Bee> beeOptional = beeSupplier.get();
        if(beeOptional.isPresent()){
            IAllele<?> allele = beeOptional.get().getGenome().getAllele(type, active);
            setText(Component.literal("(")
                    .append(Component.translatable(allele.getName()))
                    .append(Component.literal(")"))
                    .getString(), getColor(allele.isDominant()));
            int u = 0;
            int v = 0;
            if(allele instanceof Enum<?> tolerance){
                if(tolerance.name().contains("UP")){
                    u = 16;
                }
                else if(tolerance.name().contains("BOTH")){
                    u = 32;
                }
                else if(tolerance.name().contains("NONE")){
                    u = 48;
                }
            }

            if(visible()){
                RenderHelper.setPosTexShader();
                RenderHelper.setShaderTexture0(texture);
                drawTexturedModalRect(gui.pose(), posX(), posY() - 3, u, v, 15, 15);
                gui.drawString(fontRenderer(), text, posX() + 18, posY(), color);
            }
            this.width = fontRenderer().width(text) + 18;
            this.height = 12;
        }
    }
}
