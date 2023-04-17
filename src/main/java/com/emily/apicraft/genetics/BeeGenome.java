package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BeeGenome {
    private final BeeKaryotype karyotype = BeeKaryotype.INSTANCE;
    private final BeeChromosome[] chromosomes;

    public BeeGenome(BeeChromosome[] chromosomes){
        if(chromosomes.length != karyotype.size()){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < chromosomes.length; i++){
            if(!karyotype.validate(i, chromosomes[i].getType())){
                throw new IllegalArgumentException();
            }
        }
        this.chromosomes = Arrays.copyOf(chromosomes, chromosomes.length);
    }

    public BeeGenome(CompoundTag tag){
        ListTag list = tag.getList(Tags.TAG_CHROMOSOMES, Tag.TAG_COMPOUND);
        if(list.size() != karyotype.size()){
            throw new IllegalArgumentException();
        }
        chromosomes = new BeeChromosome[karyotype.size()];
        for(int i = 0; i < list.size(); i++){
            chromosomes[i] = new BeeChromosome(list.getCompound(i));
            if(!karyotype.validate(i, chromosomes[i].getType())){
                throw new IllegalArgumentException();
            }
        }
    }

    public CompoundTag writeToTag(CompoundTag tag){
        ListTag list = new ListTag();
        for(int i = 0; i < chromosomes.length; i++){
            list.add(i, chromosomes[i].writeToTag(new CompoundTag()));
        }
        tag.put(Tags.TAG_CHROMOSOMES, list);
        return tag;
    }

    // region GenomeInfo
    public Chromosomes.Species getSpecies(){
        return (Chromosomes.Species) chromosomes[karyotype.getIndex(Chromosomes.Species.class)].getActive();
    }
    public Chromosomes.Species getInactiveSpecies(){
        return (Chromosomes.Species) chromosomes[karyotype.getIndex(Chromosomes.Species.class)].getInactive();
    }
    @SuppressWarnings("unchecked")
    public <T extends IChromosomeType> T getChromosomeValue(Class<T> type, boolean active){
        return (T) (active ? chromosomes[karyotype.getIndex(type)].getActive() : chromosomes[karyotype.getIndex(type)].getInactive());
    }
    public int getMaxHealth(){
        return ((Chromosomes.LifeSpan) chromosomes[karyotype.getIndex(Chromosomes.LifeSpan.class)].getActive()).getMaxHealth();
    }
    public float getProductivity() { return ((Chromosomes.Productivity) chromosomes[karyotype.getIndex(Chromosomes.Productivity.class)].getActive()).getProductivity(); }
    public int getFertility() { return ((Chromosomes.Fertility) chromosomes[karyotype.getIndex(Chromosomes.Fertility.class)].getActive()).getFertility(); }
    public boolean canWork(long time) { return ((Chromosomes.Behavior) chromosomes[karyotype.getIndex(Chromosomes.Behavior.class)].getActive()).canWork(time); }
    public boolean toleratesRain(){
        return getChromosomeValue(Chromosomes.RainTolerance.class, true).toleratesRain();
    }
    public boolean isCaveDwelling(){
        return getChromosomeValue(Chromosomes.CaveDwelling.class, true).isCaveDwelling();
    }
    // endregion
}
