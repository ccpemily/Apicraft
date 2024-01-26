package com.emily.apicraft.genetics.alleles;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class Species implements IAllele<SpeciesData> {
    private final String id;
    private final boolean dominant;
    private final SpeciesData species;

    public Species(String id, SpeciesData species){
        this(id, species, false);
    }
    public Species(String id, SpeciesData species, boolean dominant){
        this.id = id;
        this.dominant = dominant;
        this.species = species;
    }
    @Override
    public String getRegistryName(){
        return getType() + "." + this.id;
    }

    @Override
    public boolean isDominant(){
        return dominant;
    }

    @Override
    public List<Component> getDescriptionTooltips() {
        List<String> strings = List.of(Component.translatable(getDescriptionKey()).getString().split("\n"));
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable(getType().getLocalizationKey())
                .append(": ")
                .append(Component.translatable(getLocalizationKey()).withStyle(ChatFormatting.YELLOW)));
        for(String str : strings){
            components.add(Component.literal(str));
        }
        components.add(Component.translatable("tooltip.branch")
                .append(": ")
                .append(Component.translatable(getValue().getBranch().getLocalizationKey()).withStyle(ChatFormatting.YELLOW)));
        return components;
    }

    @Override
    public SpeciesData getValue() {
        return species;
    }

    @Override
    public IAlleleType getType() {
        return AlleleTypes.SPECIES;
    }
}
