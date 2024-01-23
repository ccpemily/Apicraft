package com.emily.apicraft.capabilities.implementation;

import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.capabilities.empty.EmptyBeeProductContainer;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.interfaces.capabilities.IBeeProductContainer;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.items.BrokenFrameItem;
import com.emily.apicraft.items.FrameItem;
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

public class BeeProductFrameCapability implements IBeeProductContainer, ICapabilityProvider {
    private final LazyOptional<IBeeProductContainer> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    public BeeProductFrameCapability(ItemStack stack){
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

    public static boolean addProduct(ItemStack frame, IAllele<AlleleSpecies> species, Boolean special){
        if(frame.getItem() instanceof BrokenFrameItem){
            return false;
        }
        Optional<BeeProductData> dataOptional = BeeProductFrameCapability.get(frame).getProductData();
        if(dataOptional.isPresent()){
            BeeProductData data = dataOptional.get();
            boolean added = data.tryAdd(species, special);
            if(added){
                frame.setDamageValue(frame.getMaxDamage() - data.getTotalStored());
                BeeProductFrameCapability.get(frame).setProductData(data);
            }
            return added;
        }
        return false;
    }

    public static Optional<Tuple<IAllele<AlleleSpecies>, Boolean>> removeProduct(ItemStack frame){
        Optional<BeeProductData> dataOptional = BeeProductFrameCapability.get(frame).getProductData();
        if(dataOptional.isPresent()){
            BeeProductData data = dataOptional.get();
            Optional<Tuple<IAllele<AlleleSpecies>, Boolean>> result = data.tryRemove();
            if(result.isPresent()){
                frame.setDamageValue(frame.getMaxDamage() - data.getTotalStored());
                BeeProductFrameCapability.get(frame).setProductData(data);
            }
            return result;
        }
        return Optional.empty();
    }
}