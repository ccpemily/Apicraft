package com.emily.apicraft.genetics.alleles;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface IAllele<T> {
    /**
     *
     * @return Returns the registry name of this allele ("type.value")
     */
    String getRegistryName();
    /**
     *
     * @return Returns the unlocalized name of this allele ("allele.type.value")
     */
    default String getLocalizationKey(){
        return "allele." + this.getRegistryName();
    }
    /**
     *
     * @return Returns the description key of this allele ("allele.type.value.description")
     */
    default String getDescriptionKey(){
        return "allele." + this.getRegistryName() + ".description";
    }
    boolean isDominant();
    List<Component> getDescriptionTooltips();
    T getValue();
    IAlleleType getType();
}
