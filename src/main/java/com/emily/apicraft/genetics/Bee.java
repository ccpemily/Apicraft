package com.emily.apicraft.genetics;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

import static com.emily.apicraft.utils.Tags.*;

public class Bee {
    private final BeeGenome genome;
    @Nullable
    private BeeGenome mate;
    private int health;
    private final int generation;


    // region Constructor
    public Bee(BeeGenome genome, @Nullable BeeGenome mate, int generation){
        this.genome = genome;
        this.health = genome.getMaxHealth();
        this.generation = generation;
    }
    public Bee(BeeGenome genome, @Nullable BeeGenome mate){
        this(genome, mate, 0);
    }
    public Bee(BeeGenome genome){
        this(genome, null);
    }
    public Bee(CompoundTag tag){
        tag = tag.getCompound(TAG_BEE);
        CompoundTag genomeTag = tag.getCompound(TAG_GENOME);
        this.genome = new BeeGenome(genomeTag);
        if(tag.contains(TAG_MATE)){
            CompoundTag mateTag = tag.getCompound(TAG_MATE);
            this.mate = new BeeGenome(mateTag);
        }
        else{
            this.mate = null;
        }
        this.health = tag.getInt(TAG_HEALTH);
        this.generation = tag.getInt(TAG_GENERATION);
    }
    // endregion

    // region NBTTag
    public CompoundTag writeToTag(CompoundTag tag){
        CompoundTag beeTag = new CompoundTag();
        this.genome.writeToTag(beeTag);
        if(this.mate != null){
            this.mate.writeToTag(beeTag);
        }
        beeTag.putInt(TAG_HEALTH, this.health);
        beeTag.putInt(TAG_GENERATION, this.generation);
        tag.put(TAG_BEE, beeTag);
        return tag;
    }
    // endregion

    public BeeGenome getGenome(){
        return genome;
    }
    public int getMaxHealth(){
        return genome.getMaxHealth();
    }

    public int getHealth(){
        return health;
    }

    public boolean isAlive(){
        return health > 0;
    }
    public void age(){
        if(this.health > 0){
            health--;
        }
    }

    public static Bee getPure(Chromosomes.Species species){
        return new Bee(BeeGenome.defaultGenome(species));
    }
}
