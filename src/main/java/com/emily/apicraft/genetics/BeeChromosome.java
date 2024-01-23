package com.emily.apicraft.genetics;

import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class BeeChromosome {
    private final IAlleleType type;
    private final IAllele<?> active;
    private final IAllele<?> inactive;

    public BeeChromosome(IAllele<?> active, IAllele<?> inactive, IAlleleType type){
        this.type = type;
        if(!type.isValid(active) || !type.isValid(inactive)){
            throw new IllegalArgumentException();
        }
        this.active = active;
        this.inactive = inactive;
    }

    public BeeChromosome(IAllele<?> active, IAlleleType type){
        this(active, active, type);
    }

    public BeeChromosome(CompoundTag tag){
        this.active = Registries.ALLELES.get(tag.getString(Tags.TAG_ACTIVE));
        this.inactive = Registries.ALLELES.get(tag.getString(Tags.TAG_INACTIVE));
        this.type = active.getType();
        if(!type.isValid(active) || !type.isValid(inactive)){
            throw new IllegalArgumentException();
        }
    }

    public IAllele<?> getActive() {
        return active;
    }

    public IAllele<?> getInactive() {
        return inactive;
    }

    public IAlleleType getType(){
        return type;
    }

    public CompoundTag writeToTag(CompoundTag tag){
        tag.putString(Tags.TAG_ACTIVE, active.toString());
        tag.putString(Tags.TAG_INACTIVE, inactive.toString());
        return tag;
    }

    public BeeChromosome inheritWith(BeeChromosome mate){
        if(this.getType() != mate.getType()){
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        IAllele<?> first = random.nextBoolean() ? this.getActive() : this.getInactive();
        IAllele<?> second = random.nextBoolean() ? mate.getActive() : mate.getInactive();
        if(first.isDominant() == second.isDominant()){
            return random.nextBoolean() ? new BeeChromosome(first, second, this.getType()) : new BeeChromosome(second, first, this.getType());
        }
        else{
            return first.isDominant() ? new BeeChromosome(first, second, this.getType()) : new BeeChromosome(second, first, this.getType());
        }
    }

    public boolean isPure(){
        return this.active == this.inactive;
    }
    public boolean isEqualTo(BeeChromosome other){
        // Use == since allele is singleton (enum)
        return this.active == other.active && this.inactive == other.inactive;
    }
}
