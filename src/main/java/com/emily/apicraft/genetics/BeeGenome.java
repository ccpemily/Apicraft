package com.emily.apicraft.genetics;

import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.genetics.mutations.MutationManager;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    // region NBTTag
    public CompoundTag writeToTag(CompoundTag tag){
        ListTag list = new ListTag();
        for(int i = 0; i < chromosomes.length; i++){
            list.add(i, chromosomes[i].writeToTag(new CompoundTag()));
        }
        tag.put(Tags.TAG_CHROMOSOMES, list);
        return tag;
    }
    // endregion

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
    public boolean canWork(int time) { return ((Alleles.Behavior) getAllele(AlleleTypes.BEHAVIOR, true)).getValue().apply(time); }
    public boolean toleratesRain(){
        return ((Alleles.RainTolerance) getAllele(AlleleTypes.RAIN_TOLERANCE, true)).getValue();
    }
    public boolean isCaveDwelling(){
        return ((Alleles.CaveDwelling) getAllele(AlleleTypes.CAVE_DWELLING, true)).getValue();
    }
    // endregion

    // region GenomeInherit
    public BeeGenome inheritWith(BeeGenome other, IBeeHousing beeHousing){
        Random random = new Random();
        if(this.karyotype != other.karyotype){
            throw new IllegalArgumentException("Attempting to inherit genome of different species!");
        }
        BeeChromosome[] chromosomes = new BeeChromosome[karyotype.size()];
        for(int i = 0; i < karyotype.size(); i++){
            chromosomes[i] = this.chromosomes[i].inheritWith(other.chromosomes[i]);
        }
        BeeChromosome speciesChromosome = chromosomes[karyotype.getIndex(AlleleTypes.SPECIES)];

        if(!speciesChromosome.isPure()){
            Optional<List<Mutation>> mutationOptional = MutationManager.findMutations((Alleles.Species) speciesChromosome.getActive(), (Alleles.Species) speciesChromosome.getInactive());
            if(mutationOptional.isPresent()){
                int mutationId = random.nextInt(mutationOptional.get().size());
                float chance = mutationOptional.get().get(mutationId).getConditionalChance(beeHousing);
                IAllele<?>[] activeAlleles = getAllActive(chromosomes);
                IAllele<?>[] inactiveAlleles = getAllInactive(chromosomes);
                if(random.nextFloat() * 100 < chance){
                    activeAlleles = karyotype.defaultTemplate(mutationOptional.get().get(mutationId).getResult());
                }
                if(random.nextFloat() * 100 < chance){
                    inactiveAlleles = karyotype.defaultTemplate(mutationOptional.get().get(mutationId).getResult());
                }
                setTemplate(chromosomes, activeAlleles, inactiveAlleles);
            }
        }
        return new BeeGenome(chromosomes);
    }
    // endregion

    // region Helpers
    private static void setTemplate(BeeChromosome[] chromosomes, IAllele<?>[] templateActive, IAllele<?>[] templateInactive){
        if(chromosomes.length != templateActive.length){
            return;
        }
        for(int i = 0; i < chromosomes.length; i++){
            IAllele<?> first = templateActive[i];
            IAllele<?> second = templateInactive[i];
            if(!first.isDominant() && second.isDominant()){
                IAllele<?> temp = first;
                first = second;
                second = temp;
            }
            chromosomes[i] = new BeeChromosome(first, second, chromosomes[i].getType());
        }
    }

    private static IAllele<?>[] getAllActive(BeeChromosome[] chromosomes){
        IAllele<?>[] alleles = new IAllele[chromosomes.length];
        for(int i = 0; i < chromosomes.length; i++){
            alleles[i] = chromosomes[i].getActive();
        }
        return alleles;
    }

    private static IAllele<?>[] getAllInactive(BeeChromosome[] chromosomes){
        IAllele<?>[] alleles = new IAllele[chromosomes.length];
        for(int i = 0; i < chromosomes.length; i++){
            alleles[i] = chromosomes[i].getInactive();
        }
        return alleles;
    }
    // endregion
}
