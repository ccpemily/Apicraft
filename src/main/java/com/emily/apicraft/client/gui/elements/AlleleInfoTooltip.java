package com.emily.apicraft.client.gui.elements;

import cofh.core.client.gui.element.ElementBase;
import cofh.core.client.gui.element.ITooltipFactory;
import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
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
                IChromosomeType allele = alleleInfo.getCurrentChromosome().get();
                List<Component> components = new ArrayList<>(allele.getDescriptionTooltips());
                if(allele.getClass() == Chromosomes.TemperatureTolerance.class || allele.getClass() == Chromosomes.HumidityTolerance.class){
                    Optional<Bee> bee = alleleInfo.getBee();
                    MutableComponent component = Component.translatable("tooltip.tolerance.can_tolerate").append(": ");
                    if(bee.isPresent()){
                        Chromosomes.Species species = alleleInfo.isActive() ? bee.get().getGenome().getSpecies() : bee.get().getGenome().getInactiveSpecies();
                        EnumTemperature temperatureSelf = species.getTemperature();
                        EnumHumidity humiditySelf = species.getHumidity();
                        boolean addedComment = false;
                        if(allele instanceof Chromosomes.TemperatureTolerance temperatureTolerance){
                            for(EnumTemperature temperature : EnumTemperature.values()){
                                if(temperatureTolerance.canTolerate(temperature, temperatureSelf)){
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
                            Chromosomes.HumidityTolerance humidityTolerance = (Chromosomes.HumidityTolerance) allele;
                            for(EnumHumidity humidity : EnumHumidity.values()){
                                if(humidityTolerance.canTolerate(humidity, humiditySelf)){
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
