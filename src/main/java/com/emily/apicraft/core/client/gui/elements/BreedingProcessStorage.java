package com.emily.apicraft.core.client.gui.elements;

import cofh.lib.api.IResourceStorage;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

public class BreedingProcessStorage implements IResourceStorage, INBTSerializable<CompoundTag> {
    protected int capacity;
    protected int process;

    public BreedingProcessStorage(int capacity){
        this.capacity = capacity;
        this.process = 0;
    }

    public BreedingProcessStorage(int stored, int capacity){
        this.capacity = capacity;
        this.process = stored;
    }

    @Override
    public boolean clear() {
        if(isEmpty()){
            return false;
        }
        process = 0;
        return true;
    }

    @Override
    public void modify(int amount) {
        process += amount;
        if(process < 0){
            process = 0;
        }
        else if(process > capacity){
            process = capacity;
        }
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return process == 0 && capacity > 0;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getStored() {
        return process;
    }

    @Override
    public String getUnit() {
        return "";
    }

    public void readFromBuffer(FriendlyByteBuf buf){
        this.capacity = buf.readInt();
        this.process = buf.readInt();
    }

    public void writeToBuffer(FriendlyByteBuf buf){
        buf.writeInt(this.capacity);
        buf.writeInt(this.process);
    }

    public CompoundTag writeToTag(CompoundTag tag){
        tag.putInt(Tags.TAG_BREEDING_PROCESS, this.process);
        tag.putInt(Tags.TAG_MAX_BREEDING_PROCESS, this.capacity);
        return tag;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
        if(this.process > capacity){
            process = capacity;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(Tags.TAG_BREEDING_PROCESS, this.process);
        tag.putInt(Tags.TAG_MAX_BREEDING_PROCESS, this.capacity);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.capacity = nbt.getInt(Tags.TAG_MAX_BREEDING_PROCESS);
        this.process = nbt.getInt(Tags.TAG_BREEDING_PROCESS);
    }
}
