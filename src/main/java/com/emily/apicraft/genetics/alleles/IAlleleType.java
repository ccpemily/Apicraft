package com.emily.apicraft.genetics.alleles;

public interface IAlleleType {
    boolean isValid(IAllele<?> allele);

    /**
     *
     * @return Returns the simple name of this allele type ("type")
     */
    String getRegistryName();

    /**
     *
     * @return Returns the full unlocalized name of this allele type ("allele.type.class")
     */
    String getLocalizationKey();
    /**
     *
     * @return Returns the unlocalized name of this allele ("allele.type.class.description")
     */
    String getDescriptionKey();
}
