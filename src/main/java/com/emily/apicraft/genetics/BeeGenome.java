package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Arrays;

public class BeeGenome {
    @SuppressWarnings("unchecked")
    private static final Class<? extends IChromosomeType>[] karyotype = new Class[]{
            Chromosomes.Species.class,
            Chromosomes.LifeSpan.class,
            Chromosomes.Productivity.class
    };
    private static final BeeChromosome[] defaultTemplate = new BeeChromosome[]{
            new BeeChromosome(Chromosomes.Species.FOREST, Chromosomes.Species.class),
            new BeeChromosome(Chromosomes.LifeSpan.SHORT, Chromosomes.LifeSpan.class),
            new BeeChromosome(Chromosomes.Productivity.SLOWEST, Chromosomes.Productivity.class)
    };
    private final BeeChromosome[] chromosomes;

    public BeeGenome(BeeChromosome[] chromosomes){
        if(chromosomes.length != karyotype.length){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < chromosomes.length; i++){
            if(chromosomes[i].getType() != karyotype[i]){
                throw new IllegalArgumentException();
            }
        }
        this.chromosomes = Arrays.copyOf(chromosomes, chromosomes.length);
    }

    public BeeGenome(CompoundTag tag){
        ListTag list = tag.getList(Tags.TAG_CHROMOSOMES, Tag.TAG_COMPOUND);
        if(list.size() != karyotype.length){
            throw new IllegalArgumentException();
        }
        chromosomes = new BeeChromosome[karyotype.length];
        for(int i = 0; i < list.size(); i++){
            chromosomes[i] = new BeeChromosome(list.getCompound(i));
            if(chromosomes[i].getType() != karyotype[i]){
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
        return (Chromosomes.Species) chromosomes[0].getActive();
    }
    public int getMaxHealth(){
        return ((Chromosomes.LifeSpan) chromosomes[1].getActive()).getMaxHealth();
    }
    // endregion

    // region GenomeTemplate
    public static BeeGenome defaultGenome(Chromosomes.Species species){
        BeeChromosome[] chromosomes = Arrays.copyOf(defaultTemplate, karyotype.length);
        return new BeeGenome(chromosomes);
    }
    // endregion
}
