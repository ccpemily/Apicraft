package com.emily.apicraft.genetics;

import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Arrays;

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
    public Alleles.Species getSpecies(){
        return (Alleles.Species) chromosomes[karyotype.getIndex(AlleleTypes.SPECIES)].getActive();
    }
    public Alleles.Species getInactiveSpecies(){
        return (Alleles.Species) chromosomes[karyotype.getIndex(AlleleTypes.SPECIES)].getInactive();
    }

    public IAllele<?> getAllele(IAlleleType type, boolean active){
        return active ? chromosomes[karyotype.getIndex(type)].getActive() : chromosomes[karyotype.getIndex(type)].getInactive();
    }
    public int getMaxHealth(){
        return ((Alleles.LifeSpan) getAllele(AlleleTypes.LIFESPAN, true)).getValue();
    }
    public float getProductivity() { return ((Alleles.Productivity) getAllele(AlleleTypes.PRODUCTIVITY, true)).getValue(); }
    public int getFertility() { return ((Alleles.Fertility) getAllele(AlleleTypes.FERTILITY, true)).getValue(); }
    public boolean canWork(long time) { return ((Alleles.Behavior) getAllele(AlleleTypes.BEHAVIOR, true)).getValue().apply(time); }
    public boolean toleratesRain(){
        return ((Alleles.RainTolerance) getAllele(AlleleTypes.RAIN_TOLERANCE, true)).getValue();
    }
    public boolean isCaveDwelling(){
        return ((Alleles.CaveDwelling) getAllele(AlleleTypes.CAVE_DWELLING, true)).getValue();
    }
    // endregion
}
