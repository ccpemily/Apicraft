package com.emily.apicraft.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BeeProviderCapability implements IBeeProvider, ICapabilityProvider {
    private final LazyOptional<IBeeProvider> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    public BeeProviderCapability(ItemStack container){
        this.container = container;
    }

    @Override
    public Optional<Bee> getBeeIndividual() {
        if(container.hasTag()){
            return Optional.empty();
        }
        else{
            return Optional.of(new Bee(container.getTag()));
        }
    }

    @Override
    public void setBeeIndividual(Bee bee) {
        CompoundTag tag = container.getOrCreateTag();
        container.setTag(bee.writeToTag(tag));
    }

    @Override
    public Chromosomes.Species getBeeSpeciesDirectly() {
        if (container.hasTag()) {
            assert container.getTag() != null;
            CompoundTag beeTag = container.getTag().getCompound(Tags.TAG_BEE);
            if (beeTag.contains(Tags.TAG_GENOME)) {
                ListTag chromosomeTag = beeTag.getCompound(Tags.TAG_GENOME).getList(Tags.TAG_CHROMOSOMES, Tag.TAG_COMPOUND);
                return (Chromosomes.Species) Registries.CHROMOSOMES.get(chromosomeTag.getString(0));
            }
        }
        return Chromosomes.Species.FOREST;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == Capabilities.BEE_PROVIDER ? holder.cast() : LazyOptional.empty();
    }
}
