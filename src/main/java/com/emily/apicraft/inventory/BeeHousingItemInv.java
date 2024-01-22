package com.emily.apicraft.inventory;

import cofh.lib.api.IStorageCallback;
import cofh.lib.common.inventory.ItemStorageCoFH;
import cofh.lib.common.inventory.SimpleItemInv;
import com.emily.apicraft.capabilities.implementation.BeeProductContainerCapability;
import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.capabilities.IBeeProductContainer;
import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import com.emily.apicraft.inventory.storage.ItemStorageBee;
import com.emily.apicraft.inventory.storage.ItemStorageFrame;
import com.emily.apicraft.items.subtype.BeeTypes;
import com.emily.apicraft.utils.Tags;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BeeHousingItemInv extends SimpleItemInv implements IBeeModifierProvider {
    protected ItemStorageBee queenInv;
    protected ItemStorageBee droneInv;
    protected List<ItemStorageCoFH> outputInv;
    protected List<ItemStorageFrame> frameInv;



    protected List<ItemStorageCoFH> augmentInv;
    protected IItemHandler inputHandler;
    protected IItemHandler outputHandler;
    protected IItemHandler accessibleHandler;

    public BeeHousingItemInv(@Nullable IStorageCallback callback, int outputCount, int frameCount, int augmentCount, int maxFrameTier) {
        super(callback, Tags.TAG_BEEHOUSING_INV);
        queenInv = new ItemStorageBee(BeeTypes.QUEEN, BeeTypes.DRONE);
        queenInv.setCapacity(1);
        slots.add(queenInv);
        droneInv = new ItemStorageBee(BeeTypes.DRONE);
        slots.add(droneInv);
        outputInv = new ArrayList<>();
        for(int i = 0; i < outputCount; i++){
            ItemStorageCoFH slot = new ItemStorageCoFH();
            outputInv.add(slot);
            slots.add(slot);
        }

        frameInv = new ArrayList<>();
        for(int i = 0; i < frameCount; i++){
            ItemStorageFrame frameSlot = new ItemStorageFrame(maxFrameTier);
            frameInv.add(frameSlot);
            slots.add(frameSlot);
        }

        augmentInv = new ArrayList<>();
        for(int i = 0; i < augmentCount; i++){
            ItemStorageCoFH augSlot = new ItemStorageCoFH();
            augmentInv.add(augSlot);
            slots.add(augSlot);
        }
    }

    public boolean hasFrameSlot(){
        return frameInv.size() > 0;
    }

    public ItemStorageBee getQueenSlot(){
        return queenInv;
    }

    public ItemStorageBee getDroneSlot(){
        return droneInv;
    }

    public List<ItemStorageCoFH> getOutputInv() {
        return outputInv;
    }

    public List<ItemStorageFrame> getFrameInv() {
        return frameInv;
    }
    public List<ItemStorageCoFH> getAugmentInv() {
        return augmentInv;
    }
    public boolean isFrameSlot(int id){
        return (id < 2 + outputInv.size() + frameInv.size()) && (id >= 2 + outputInv.size());
    }
    public boolean isAugmentSlot(int id){
        return !(id < 2 + outputInv.size() + frameInv.size());
    }

    public ItemStack getQueen(){
        return queenInv.getItemStack();
    }

    public ItemStack getDrone(){
        return droneInv.getItemStack();
    }

    public ItemStack tryAddItemOutput(ItemStack stack){
        for(int i = 0; i < outputInv.size(); i++){
            stack = outputInv.get(i).insertItem(i, stack, false);
        }
        return stack;
    }

    public void addFrameProduct(Alleles.Species species, boolean special){
        Random random = new Random();
        List<Integer> listFrame = new ArrayList<>();

        // Randomly select a frame to add, except frames already full
        for(int i = 0; i < frameInv.size(); i++){
            if(frameInv.get(i).getItemStack().getCapability(Capabilities.PRODUCT_DATA_PROVIDER).isPresent()){
                IBeeProductContainer container = BeeProductContainerCapability.get(frameInv.get(i).getItemStack());
                if(container.getProductData().isPresent() && !container.getProductData().get().isFull()){
                    listFrame.add(i);
                }
            }
        }
        if(listFrame.isEmpty()){
            return;
        }
        int id = listFrame.get(random.nextInt(listFrame.size()));
        if(frameInv.get(id).getItemStack().getCapability(Capabilities.PRODUCT_DATA_PROVIDER).isPresent()){
            BeeProductContainerCapability.addProduct(frameInv.get(id).getItemStack(), species, special);
        }
    }

    // region IBeeModifierProvider
    @Override
    public float applyProductivityModifier(float val) {
        float result = val;
        for(ItemStorageFrame frameStorage : getFrameInv()){
            if(frameStorage.getItemStack().getItem() instanceof IBeeModifierProvider provider){
                result = provider.applyProductivityModifier(result);
            }
        }
        return result;
    }

    @Override
    public int applyLifespanModifier(int val) {
        int result = val;
        for(ItemStorageFrame frameStorage : getFrameInv()){
            if(frameStorage.getItemStack().getItem() instanceof IBeeModifierProvider provider){
                result = provider.applyLifespanModifier(result);
            }
        }
        return result;
    }

    @Override
    public float applyMutationModifier(float val) {
        float result = val;
        for(ItemStorageFrame frameStorage : getFrameInv()){
            if(frameStorage.getItemStack().getItem() instanceof IBeeModifierProvider provider){
                result = provider.applyMutationModifier(result);
            }
        }
        return result;
    }

    @Override
    public Vec3i applyTerritoryModifier(Vec3i val) {
        Vec3i result = val;
        for(ItemStorageFrame frameStorage : getFrameInv()){
            if(frameStorage.getItemStack().getItem() instanceof IBeeModifierProvider provider){
                result = provider.applyTerritoryModifier(result);
            }
        }
        return result;
    }

    @Override
    public int applyFertilityModifier(int val) {
        int result = val;
        for(ItemStorageFrame frameStorage : getFrameInv()){
            if(frameStorage.getItemStack().getItem() instanceof IBeeModifierProvider provider){
                result = provider.applyFertilityModifier(result);
            }
        }
        return result;
    }
    // endregion
}
