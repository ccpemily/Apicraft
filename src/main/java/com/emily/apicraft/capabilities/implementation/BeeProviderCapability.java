package com.emily.apicraft.capabilities.implementation;

import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.capabilities.empty.EmptyBeeProvider;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeKaryotype;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
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

import javax.annotation.Nonnull;
import java.util.Optional;

public class BeeProviderCapability implements IBeeProvider, ICapabilityProvider {
    private final LazyOptional<IBeeProvider> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    public BeeProviderCapability(ItemStack container){
        this.container = container;
    }

    @Override
    public Optional<Bee> getBeeIndividual() {
        return container.hasTag() ? Optional.of(new Bee(container.getTag())) : Optional.empty();
    }

    @Override
    public void setBeeIndividual(Bee bee) {
        CompoundTag tag = container.getOrCreateTag();
        container.setTag(bee.writeToTag(tag));
    }

    @Override
    @Nonnull
    public Alleles.Species getBeeSpeciesDirectly(boolean active) {
        Alleles.Species species = (Alleles.Species) getBeeChromosomeDirectly(AlleleTypes.SPECIES, active);
        return species == null ? Alleles.Species.FOREST : species;
    }

    @Override
    @Nullable
    public IAllele<?> getBeeChromosomeDirectly(IAlleleType type, boolean active) {
        if (container.hasTag()) {
            assert container.getTag() != null;
            CompoundTag beeTag = container.getTag().getCompound(Tags.TAG_BEE);
            if (beeTag.contains(Tags.TAG_GENOME)) {
                ListTag chromosomeTag = beeTag.getCompound(Tags.TAG_GENOME).getList(Tags.TAG_CHROMOSOMES, Tag.TAG_COMPOUND);
                CompoundTag chromosome = chromosomeTag.getCompound(BeeKaryotype.INSTANCE.getIndex(type));
                return Registries.ALLELES.get(active ? chromosome.getString(Tags.TAG_ACTIVE) : chromosome.getString(Tags.TAG_INACTIVE));
            }
        }
        return null;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == Capabilities.BEE_PROVIDER ? holder.cast() : LazyOptional.empty();
    }

    public static IBeeProvider get(ItemStack stack){
        return stack.getCapability(Capabilities.BEE_PROVIDER).orElse(EmptyBeeProvider.getInstance());
    }
}
