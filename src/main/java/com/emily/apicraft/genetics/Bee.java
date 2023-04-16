package com.emily.apicraft.genetics;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.emily.apicraft.utils.Tags.*;

public class Bee {
    // region Fields
    private final BeeGenome genome;
    @Nullable
    private BeeGenome mate;
    private int health;
    private final int generation;
    private boolean analyzed;
    // endregion

    // region Constructor
    public Bee(BeeGenome genome, @Nullable BeeGenome mate, int generation){
        this.genome = genome;
        this.health = genome.getMaxHealth();
        this.generation = generation;
        this.analyzed = false;
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
        this.analyzed = tag.getBoolean(TAG_ANALYZED);
    }
    // endregion

    // region NBT
    public CompoundTag writeToTag(CompoundTag tag){
        CompoundTag beeTag = new CompoundTag();
        beeTag.put(TAG_GENOME, this.genome.writeToTag(new CompoundTag()));
        if(this.mate != null){
            beeTag.put(TAG_MATE, this.mate.writeToTag(new CompoundTag()));
        }
        beeTag.putInt(TAG_HEALTH, this.health);
        beeTag.putInt(TAG_GENERATION, this.generation);
        beeTag.putBoolean(TAG_ANALYZED, this.analyzed);
        tag.put(TAG_BEE, beeTag);
        return tag;
    }
    // endregion

    // region BeeInfo
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
    public boolean isAnalyzed() { return analyzed; }
    // endregion

    // region BeeBehavior
    public void age(){
        if(this.health > 0){
            health--;
        }
    }

    public boolean mate(@Nonnull BeeGenome mate){
        if(this.mate != null){
            return false;
        }
        else{
            this.mate = mate;
            return true;
        }
    }

    public void analyze(){
        this.analyzed = true;
    }
    // endregion

    // region Helpers
    public static Bee getPure(Chromosomes.Species species){
        return new Bee(BeeKaryotype.INSTANCE.defaultGenome(species));
    }
    // endregion
}
