package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeeKaryotype {
    public static final BeeKaryotype INSTANCE = new BeeKaryotype();

    private final List<Class<? extends IChromosomeType>> karyotype = new ArrayList<>();
    private final HashMap<Class<? extends IChromosomeType>, IChromosomeType> defaultTemplate = new HashMap<>();


    public int size(){
        return karyotype.size();
    }
    public boolean validate(int id, Class<? extends IChromosomeType> type){
        return id != -1 && getIndex(type) != -1 && id == getIndex(type);
    }

    public boolean validate(int id, IChromosomeType type){
        return validate(id, type.getClass());
    }

    public int getIndex(Class<? extends IChromosomeType> type){
        if(!karyotype.contains(type)){
            return -1;
        }
        else return karyotype.indexOf(type);
    }

    public void registerToKaryotype(Class<? extends IChromosomeType> type, IChromosomeType defaultValue){
        karyotype.add(type);
        defaultTemplate.put(type, defaultValue);
    }

    public IChromosomeType[] defaultTemplate(Chromosomes.Species species){
        IChromosomeType[] chromosomes = new IChromosomeType[karyotype.size()];
        for(int i = 0; i < karyotype.size(); i++){
            Class<? extends IChromosomeType> type = karyotype.get(i);
            if(species.getTemplate().containsKey(type)){
                chromosomes[i] = species.getTemplate().get(type);
            }
            else {
                chromosomes[i] = defaultTemplate.get(type);
            }
        }
        chromosomes[getIndex(Chromosomes.Species.class)] = species;
        return chromosomes;
    }

    public BeeGenome defaultGenome(Chromosomes.Species species){
        BeeChromosome[] chromosomes = new BeeChromosome[karyotype.size()];
        IChromosomeType[] template = defaultTemplate(species);
        for(int i = 0; i < karyotype.size(); i++){
            chromosomes[i] = new BeeChromosome(template[i], karyotype.get(i));
        }
        return new BeeGenome(chromosomes);
    }
}
