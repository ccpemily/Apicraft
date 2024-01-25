package com.emily.apicraft.utils;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.capabilities.IBeeProvider;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.core.registry.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {
    public static final String BEE_DRONE_ID = "bee_drone";
    public static final String BEE_PRINCESS_ID = "bee_princess";
    public static final String BEE_QUEEN_ID = "bee_queen";
    public static final String BEE_LARVA_ID = "bee_larva";

    // region static
    public static ItemStack getDefaultBeeStack(ResourceLocation id, IAllele<SpeciesData> species){
        ItemStack stack = new ItemStack(Registries.ITEMS.get(id));
        IBeeProvider provider = BeeProviderCapability.get(stack);
        if(provider instanceof BeeProviderCapability){
            Bee bee = Bee.getPure(species);
            provider.setBeeIndividual(bee);
        }
        return stack;
    }

    public static ItemStack getDefaultBeeStack(ResourceLocation id){
        return getDefaultBeeStack(id, Alleles.Species.FOREST);
    }

    public static ItemStack getDefaultDroneStack(){
        return getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, BEE_DRONE_ID));
    }
}
