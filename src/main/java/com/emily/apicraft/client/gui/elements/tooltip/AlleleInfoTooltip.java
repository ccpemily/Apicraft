package com.emily.apicraft.client.gui.elements.tooltip;

import cofh.core.client.gui.element.ElementBase;
import cofh.core.client.gui.element.ITooltipFactory;
import com.emily.apicraft.client.gui.elements.ElementAlleleInfo;
import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.core.lib.ErrorStates;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.IAllele;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AlleleInfoTooltip implements ITooltipFactory {

    @Override
    public List<Component> create(ElementBase element, int mouseX, int mouseY) {
        if(element instanceof ElementAlleleInfo alleleInfo){
            if(!element.visible() || alleleInfo.getCurrentChromosome().isEmpty()){
                return Collections.emptyList();
            }
            else{
                IAllele<?> allele = alleleInfo.getCurrentChromosome().get();
                List<Component> components = new ArrayList<>(allele.getDescriptionTooltips());
                if(allele.getClass() == Alleles.TemperatureTolerance.class || allele.getClass() == Alleles.HumidityTolerance.class){
                    Optional<Bee> bee = alleleInfo.getBee();
                    MutableComponent component = Component.translatable("tooltip.tolerance.can_tolerate").append(": ");
                    if(bee.isPresent()){
                        IAllele<AlleleSpecies> species = alleleInfo.isActive() ? bee.get().getGenome().getSpecies() : bee.get().getGenome().getInactiveSpecies();
                        EnumTemperature temperatureSelf = species.getValue().getTemperature();
                        EnumHumidity humiditySelf = species.getValue().getHumidity();
                        boolean addedComment = false;
                        if(allele instanceof Alleles.TemperatureTolerance temperatureTolerance){
                            for(EnumTemperature temperature : EnumTemperature.values()){
                                if(temperatureTolerance.getValue().apply(temperature, temperatureSelf) == ErrorStates.NONE){
                                    if(addedComment){
                                        component.append(", ");
                                    }
                                    else{
                                        addedComment = true;
                                    }
                                    component.append(Component.translatable(temperature.getName()).withStyle(ChatFormatting.YELLOW));
                                }
                            }
                        }
                        else {
                            Alleles.HumidityTolerance humidityTolerance = (Alleles.HumidityTolerance) allele;
                            for(EnumHumidity humidity : EnumHumidity.values()){
                                if(humidityTolerance.getValue().apply(humidity, humiditySelf) == ErrorStates.NONE){
                                    if(addedComment){
                                        component.append(", ");
                                    }
                                    else{
                                        addedComment = true;
                                    }
                                    component.append(Component.translatable(humidity.getName()).withStyle(ChatFormatting.YELLOW));
                                }
                            }
                        }
                    }
                    components.add(component);
                }
                return components;
            }
        }
        return Collections.emptyList();
    }
}
