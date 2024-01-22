package com.emily.apicraft.capabilities.implementation;

import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.capabilities.empty.EmptyBeeProductContainer;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.capabilities.IBeeProductContainer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BeeProductContainerCapability implements IBeeProductContainer, ICapabilityProvider {
    private final LazyOptional<IBeeProductContainer> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    public BeeProductContainerCapability(ItemStack stack){
        this.container = stack;
    }

    @Override
    public Optional<BeeProductData> getProductData() {
        if (container.hasTag()) {
            assert container.getTag() != null;
            return Optional.of(new BeeProductData(container, container.getTag()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void setProductData(BeeProductData data) {
        CompoundTag tag = container.getOrCreateTag();
        container.setTag(data.writeToTag(tag));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == Capabilities.PRODUCT_DATA_PROVIDER ? holder.cast() : LazyOptional.empty();
    }

    public static IBeeProductContainer get(ItemStack stack){
        return stack.getCapability(Capabilities.PRODUCT_DATA_PROVIDER).orElse(EmptyBeeProductContainer.getInstance());
    }

    public static void addProduct(ItemStack frame, Alleles.Species species, Boolean special){
        Optional<BeeProductData> dataOptional = BeeProductContainerCapability.get(frame).getProductData();
        if(dataOptional.isPresent()){
            BeeProductData data = dataOptional.get();
            boolean added = data.tryAdd(species, special);
            if(added){
                frame.setDamageValue(frame.getMaxDamage() - data.getTotalStored());
                BeeProductContainerCapability.get(frame).setProductData(data);
            }
        }
    }

    public static Optional<Tuple<Alleles.Species, Boolean>> removeProduct(ItemStack frame){
        Optional<BeeProductData> dataOptional = BeeProductContainerCapability.get(frame).getProductData();
        if(dataOptional.isPresent()){
            BeeProductData data = dataOptional.get();
            Optional<Tuple<Alleles.Species, Boolean>> result = data.tryRemove();
            if(result.isPresent()){
                frame.setDamageValue(frame.getMaxDamage() - data.getTotalStored());
                BeeProductContainerCapability.get(frame).setProductData(data);
            }
            return result;
        }
        return Optional.empty();
    }
}
