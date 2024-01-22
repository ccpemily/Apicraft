package com.emily.apicraft.interfaces.items;

import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import com.emily.apicraft.items.subtype.BeeTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IBeeInfoProvider extends IForgeItem {

    @Override
    default ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag){
        return new BeeProviderCapability(stack);
    }

    BeeTypes getBeeType();

    default Optional<Bee> getBeeIndividual(ItemStack stack){
        IBeeProvider provider = BeeProviderCapability.get(stack);
        return provider.getBeeIndividual();
    }
}
