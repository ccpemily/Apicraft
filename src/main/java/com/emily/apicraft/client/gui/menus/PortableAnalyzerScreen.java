package com.emily.apicraft.client.gui.menus;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.*;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.client.gui.elements.*;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.emily.apicraft.inventory.containers.PortableAnalyzerContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static cofh.lib.util.helpers.StringHelper.localize;

public class PortableAnalyzerScreen extends ContainerScreenCoFH<PortableAnalyzerContainer> {
    private static final int COLUMN_0 = 17;
    private static final int COLUMN_1 = 95;
    private static final int COLUMN_2 = 160;
    private static final int LINE_HEIGHT = 12;

    private int selected = 0;
    private final InvWrapperCoFH invWrapper;

    public PortableAnalyzerScreen(PortableAnalyzerContainer container, Inventory inv, Component titleIn) {
        super(container, inv, titleIn);
        texture = new ResourceLocation("apicraft:textures/gui/portable_analyzer/background.png");
        imageWidth = 250;
        imageHeight = 237;
        invWrapper = container.invWrapper;
    }

    @Override
    public void init(){
        super.init();
        addButtons();
        addPages();
    }

    @Override
    public void containerTick(){
        super.containerTick();
        if(getBeeIndividual().isEmpty()){
            selected = 0;
        } else if (getBeeIndividual().isPresent() && selected == 0) {
            selected = 1;
        }
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int x, int y){
        super.renderBg(stack, partialTicks, x, y);
        if (selected == 0) {
            drawCenteredString(stack, font, Component.translatable("gui.portable_analyzer.title"), getGuiLeft() + 116, getGuiTop() + getRowY(1), 0xffffff);
            font.draw(stack, Component.translatable("gui.portable_analyzer.can_analyze"), getGuiLeft() + COLUMN_0, getGuiTop() + getRowY(4), 0xffffff);
            font.draw(stack, Component.translatable("gui.portable_analyzer.page_1"), getGuiLeft() + COLUMN_0, getGuiTop() + getRowY(6), 0xffffff);
            font.draw(stack, Component.translatable("gui.portable_analyzer.page_2"), getGuiLeft() + COLUMN_0, getGuiTop() + getRowY(7), 0xffffff);
            font.draw(stack, Component.translatable("gui.portable_analyzer.page_3"), getGuiLeft() + COLUMN_0, getGuiTop() + getRowY(8), 0xffffff);
            font.draw(stack, Component.translatable("gui.portable_analyzer.page_4"), getGuiLeft() + COLUMN_0, getGuiTop() + getRowY(9), 0xffffff);
        }
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawPanels(matrixStack, true);
        drawElements(matrixStack, true);
    }

    private ItemStack getBeeStack(){
        return invWrapper.getItem(0);
    }

    private Optional<Bee> getBeeIndividual(){
        return getBeeStack().isEmpty() || !getBeeStack().hasTag() ? Optional.empty() : BeeProviderCapability.get(getBeeStack()).getBeeIndividual();
    }

    private int getRowY(int row){
        return 1 + LINE_HEIGHT * (row + 1);
    }

    private void addButtons(){
        addElement(new ElementButton(this, 226, 56){
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
                selected = 1;
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture("apicraft:textures/gui/portable_analyzer/button_1.png", 54, 18)
                .setEnabled(() -> this.selected != 1 && getBeeIndividual().isPresent()));
        addElement(new ElementButton(this, 226, 74){
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
                selected = 2;
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture("apicraft:textures/gui/portable_analyzer/button_2.png", 54, 18)
                .setEnabled(() -> this.selected != 2 && getBeeIndividual().isPresent()));
        addElement(new ElementButton(this, 226, 92){
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
                selected = 3;
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture("apicraft:textures/gui/portable_analyzer/button_3.png", 54, 18)
                .setEnabled(() -> this.selected != 3 && getBeeIndividual().isPresent()));
        addElement(new ElementButton(this, 226, 110){
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
                selected = 4;
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture("apicraft:textures/gui/portable_analyzer/button_4.png", 54, 18)
                .setEnabled(() -> this.selected != 4 && getBeeIndividual().isPresent()));
    }

    private void addPages(){
        // Top row (Active and Inactive)
        addElement(new ElementText(this, COLUMN_1, getRowY(0))
                .setText(Component.translatable("gui.portable_analyzer.active").getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable("gui.portable_analyzer.active.tooltip")))
                .setVisible(() -> this.selected == 1 || this.selected == 2)
        );
        addElement(new ElementText(this, COLUMN_2, getRowY(0))
                .setText(Component.translatable("gui.portable_analyzer.inactive").getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable("gui.portable_analyzer.inactive.tooltip")))
                .setVisible(() -> this.selected == 1 || this.selected == 2)
        );

        // Page 1
        // Species row
        addChromosomeInfoLine(Chromosomes.Species.class, "chromosomes.species.class", 2, 1);
        // Item icon
        addSpeciesIcon(true);
        addSpeciesIcon(false);
        // Lifespan row
        addChromosomeInfoLine(Chromosomes.LifeSpan.class, "chromosomes.lifespan.class", 4, 1);
        // Productivity row
        addChromosomeInfoLine(Chromosomes.Productivity.class, "chromosomes.productivity.class", 5, 1);
        // Fertility row
        addChromosomeInfoLine(Chromosomes.Fertility.class, "chromosomes.fertility.class", 6, 1);
        addFertilityIcon();

        // Flowers row
        addChromosomeInfoLine(Chromosomes.AcceptedFlowers.class, "chromosomes.accepted_flowers.class", 8, 1);
        // Territory row
        addChromosomeInfoLine(Chromosomes.Territory.class, "chromosomes.territory.class", 9, 1);
        // Effect row
        addChromosomeInfoLine(Chromosomes.Effect.class, "chromosomes.effect.class", 10, 1);

        // Page 2
        // Temperature row
        addTemperatureInfoLine();
        addToleranceInfoLine(Chromosomes.TemperatureTolerance.class, "chromosomes.temperature_tolerance.class", 3);
        addHumidityInfoLine();
        addToleranceInfoLine(Chromosomes.HumidityTolerance.class, "chromosomes.humidity_tolerance.class", 5);
        addChromosomeInfoLine(Chromosomes.Behavior.class, "chromosomes.behavior.class", 7, 2);
        addChromosomeInfoLine(Chromosomes.RainTolerance.class, "chromosomes.rain_tolerance.class", 9, 2);
        addChromosomeInfoLine(Chromosomes.CaveDwelling.class, "chromosomes.cave_dwelling.class", 10, 2);
    }
    private void addSpeciesIcon(boolean active){
        addElement(new ElementItem(this, (active ? COLUMN_1 : COLUMN_2) + 36, getRowY(0) - 2)
                .setItem(() -> {
                    ItemStack stack = new ItemStack(getBeeStack().getItem());
                    BeeProviderCapability.get(stack).setBeeIndividual(Bee.getPure(
                            getBeeIndividual().isPresent() ?
                                    active ? getBeeIndividual().get().getGenome().getSpecies()
                                            : getBeeIndividual().get().getGenome().getInactiveSpecies()
                                    :Chromosomes.Species.FOREST)
                    );
                    return stack;
                })
                .setVisible(() -> this.selected == 1)
                .setTooltipFactory(ITooltipFactory.EMPTY)
        );
    }
    private void addFertilityIcon(){
        addElement(new ElementTexture(this, COLUMN_1 + 13, getRowY(6) - 2)
                .setUV(0, 2)
                .setSize(16, 12)
                .setTexture("apicraft:textures/gui/portable_analyzer/fertility.png", 16, 16)
                .setVisible(() -> this.selected == 1)
        );
        addElement(new ElementTexture(this, COLUMN_2 + 13, getRowY(6) - 2)
                .setUV(0, 2)
                .setTexture("apicraft:textures/gui/portable_analyzer/fertility.png", 16, 16)
                .setSize(16, 12)
                .setVisible(() -> this.selected == 1)
        );
    }
    private void addChromosomeInfoLine(Class<? extends IChromosomeType> type, String localizationKey, int row, int page){
        addElement(new ElementText(this, COLUMN_0, getRowY(row))
                .setText(Component.translatable(localizationKey).getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable(localizationKey + ".description")))
                .setVisible(() -> this.selected == page)
        );
        addElement(new ElementAlleleInfo(this, COLUMN_1, getRowY(row), this::getBeeIndividual, type, true)
                .setTooltipFactory(new AlleleInfoTooltip())
                .setVisible(() -> this.selected == page)
        );
        addElement(new ElementAlleleInfo(this, COLUMN_2, getRowY(row), this::getBeeIndividual, type, false)
                .setTooltipFactory(new AlleleInfoTooltip())
                .setVisible(() -> this.selected == page)
        );
    }

    private void addTemperatureInfoLine(){
        addElement(new ElementText(this, COLUMN_0, getRowY(2))
                .setText(Component.translatable("climatology.temperature").getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable("climatology.temperature.description")))
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementTemperatureInfo(this, COLUMN_1, getRowY(2), this::getBeeIndividual, true)
                .setTooltipFactory(new TemperatureTooltip())
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementTemperatureInfo(this, COLUMN_2, getRowY(2), this::getBeeIndividual, false)
                .setTooltipFactory(new TemperatureTooltip())
                .setVisible(() -> this.selected == 2)
        );
    }

    private void addHumidityInfoLine(){
        addElement(new ElementText(this, COLUMN_0, getRowY(4))
                .setText(Component.translatable("climatology.humidity").getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable("climatology.humidity.description")))
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementHumidityInfo(this, COLUMN_1, getRowY(4), this::getBeeIndividual, true)
                .setTooltipFactory(new HumidityTooltip())
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementHumidityInfo(this, COLUMN_2, getRowY(4), this::getBeeIndividual, false)
                .setTooltipFactory(new HumidityTooltip())
                .setVisible(() -> this.selected == 2)
        );
    }

    private void addToleranceInfoLine(Class<? extends IChromosomeType> type, String localizationKey, int row){
        addElement(new ElementText(this, COLUMN_0 + 10, getRowY(row))
                .setText(Component.translatable(localizationKey).getString())
                .setTooltipFactory(new SimpleTooltip(Component.translatable(localizationKey + ".description")))
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementToleranceInfo(this, COLUMN_1, getRowY(row), this::getBeeIndividual, type, true)
                .setTooltipFactory(new AlleleInfoTooltip())
                .setVisible(() -> this.selected == 2)
        );
        addElement(new ElementToleranceInfo(this, COLUMN_2, getRowY(row), this::getBeeIndividual, type, false)
                .setTooltipFactory(new AlleleInfoTooltip())
                .setVisible(() -> this.selected == 2)
        );
    }
}
