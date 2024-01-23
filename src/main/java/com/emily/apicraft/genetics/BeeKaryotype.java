package com.emily.apicraft.genetics;

import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeeKaryotype {
    public static final BeeKaryotype INSTANCE = new BeeKaryotype();

    private final List<IAlleleType> karyotype = new ArrayList<>();
    private final HashMap<IAlleleType, IAllele<?>> defaultTemplate = new HashMap<>();


    public int size(){
        return karyotype.size();
    }

    public boolean validate(int id, IAlleleType type){
        return id != -1 && getIndex(type) != -1 && id == getIndex(type);
    }

    public boolean validate(int id, IAllele<?> allele){
        return validate(id, allele.getType());
    }

    public int getIndex(IAlleleType type){
        if(!karyotype.contains(type)){
            return -1;
        }
        else return karyotype.indexOf(type);
    }

    public void registerToKaryotype(IAlleleType type, IAllele<?> defaultValue){
        karyotype.add(type);
        defaultTemplate.put(type, defaultValue);
    }

    public IAllele<?>[] defaultTemplate(IAllele<AlleleSpecies> species){
        IAllele<?>[] alleles = new IAllele[karyotype.size()];
        for(int i = 0; i < karyotype.size(); i++){
            IAlleleType type = karyotype.get(i);
            if(species.getValue().getTemplate().containsKey(type)){
                alleles[i] = species.getValue().getTemplate().get(type);
            }
            else {
                alleles[i] = defaultTemplate.get(type);
            }
        }
        alleles[getIndex(AlleleTypes.SPECIES)] = species;
        return alleles;
    }

    public BeeGenome defaultGenome(IAllele<AlleleSpecies> species){
        BeeChromosome[] chromosomes = new BeeChromosome[karyotype.size()];
        IAllele<?>[] template = defaultTemplate(species);
        for(int i = 0; i < karyotype.size(); i++){
            chromosomes[i] = new BeeChromosome(template[i], karyotype.get(i));
        }
        return new BeeGenome(chromosomes);
    }
}
