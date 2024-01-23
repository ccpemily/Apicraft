package com.emily.apicraft.genetics;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface IAllele<T> {
    /**
     *
     * @return Returns the registry name of this allele ("type.value")
     */
    @Override
    String toString();
    /**
     *
     * @return Returns the unlocalized name of this allele ("allele.type.value")
     */
    default String getName(){
        return "allele." + this;
    }
    /**
     *
     * @return Returns the description key of this allele ("allele.type.value.description")
     */
    default String getDescription(){
        return "allele." + this + ".description";
    }
    boolean isDominant();
    List<Component> getDescriptionTooltips();
    T getValue();
    IAlleleType getType();
}
