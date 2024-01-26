package com.emily.apicraft.genetics.branches;

import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.IAlleleType;

import java.util.HashMap;

public class BeeBranchProperties {
    private final HashMap<IAlleleType, IAllele<?>> template = new HashMap<>();

    private BeeBranchProperties(){}

    public BeeBranchProperties addToTemplate(IAllele<?> allele){
        template.put(allele.getType(), allele);
        return this;
    }

    public HashMap<IAlleleType, IAllele<?>> getTemplate(){
        return template;
    }

    public static BeeBranchProperties get(){
        return new BeeBranchProperties();
    }
}
