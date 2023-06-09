package com.emily.apicraft.items.creativetab;

import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreativeTabs {
    public static final CreativeModeTab TAB_BEES = new CreativeModeTab("apicraft.bees") {
        @Override
        public @NotNull ItemStack makeIcon() {
            ItemStack stack = new ItemStack(Registries.ITEMS.get("bee_drone"));
            BeeProviderCapability.get(stack).setBeeIndividual(Bee.getPure(Alleles.Species.FOREST));
            return stack;
        }
    };

    public static final CreativeModeTab TAB_BLOCKS = new CreativeModeTab("apicraft.blocks") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Registries.ITEMS.get("apiary"));
        }
    };

    public static final CreativeModeTab TAB_ITEMS = new CreativeModeTab("apicraft.items") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Registries.ITEMS.get("bee_comb_honey"));
        }
    };
}
