package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class BeeChromosome {
    private final Class<? extends IChromosomeType> type;
    private final IChromosomeType active;
    private final IChromosomeType inactive;

    public BeeChromosome(IChromosomeType active, IChromosomeType inactive, Class<? extends IChromosomeType> type){
        this.type = type;
        if(!type.isInstance(active) || !type.isInstance(inactive)){
            throw new IllegalArgumentException();
        }
        this.active = active;
        this.inactive = inactive;
    }

    public BeeChromosome(IChromosomeType active, Class<? extends IChromosomeType> type){
        this(active, active, type);
    }

    public BeeChromosome(CompoundTag tag){
        this.active = Registries.CHROMOSOMES.get(tag.getString(Tags.TAG_ACTIVE));
        this.inactive = Registries.CHROMOSOMES.get(tag.getString(Tags.TAG_INACTIVE));
        this.type = active.getClass();
        if(!type.isInstance(active) || !type.isInstance(inactive)){
            throw new IllegalArgumentException();
        }
    }

    public IChromosomeType getActive() {
        return active;
    }

    public IChromosomeType getInactive() {
        return inactive;
    }

    public Class<? extends IChromosomeType> getType(){
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
        IChromosomeType first = random.nextBoolean() ? this.getActive() : this.getInactive();
        IChromosomeType second = random.nextBoolean() ? mate.getActive() : mate.getInactive();
        if(first.isDominant() == second.isDominant()){
            return random.nextBoolean() ? new BeeChromosome(first, second, this.getType()) : new BeeChromosome(second, first, this.getType());
        }
        else{
            return first.isDominant() ? new BeeChromosome(first, second, this.getType()) : new BeeChromosome(second, first, this.getType());
        }
    }
}
