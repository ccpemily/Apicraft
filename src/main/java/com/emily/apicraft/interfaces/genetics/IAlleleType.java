package com.emily.apicraft.interfaces.genetics;

public interface IAlleleType {
    boolean isValid(IAllele<?> allele);

    /**
     *
     * @return Returns the simple name of this allele type ("type")
     */
    @Override
    String toString();

    /**
     *
     * @return Returns the full unlocalized name of this allele type ("allele.type.class")
     */
    String getName();
    /**
     *
     * @return Returns the unlocalized name of this allele ("allele.type.class.description")
     */
    String getDescription();
}
