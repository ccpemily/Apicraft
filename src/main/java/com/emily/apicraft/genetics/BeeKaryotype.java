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

    public BeeGenome defaultGenome(Chromosomes.Species species){
        BeeChromosome[] chromosomes = new BeeChromosome[karyotype.size()];
        for(int i = 0; i < karyotype.size(); i++){
            chromosomes[i] = new BeeChromosome(defaultTemplate.get(karyotype.get(i)), karyotype.get(i));
        }
        chromosomes[getIndex(Chromosomes.Species.class)] = new BeeChromosome(species, Chromosomes.Species.class);
        return new BeeGenome(chromosomes);
    }
}
