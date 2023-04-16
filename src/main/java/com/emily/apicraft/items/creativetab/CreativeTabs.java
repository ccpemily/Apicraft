package com.emily.apicraft.items.creativetab;

import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.capabilities.EmptyBeeProvider;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeGenome;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreativeTabs {
    public static final CreativeModeTab TAB_BEES = new CreativeModeTab("apicraft.bees") {
        @Override
        public @NotNull ItemStack makeIcon() {
            ItemStack stack = new ItemStack(Registries.ITEMS.get("bee_drone"));
            BeeProviderCapability.get(stack).setBeeIndividual(Bee.getPure(Chromosomes.Species.FOREST));
            return stack;
        }
    };

    public static final CreativeModeTab TAB_BLOCKS = new CreativeModeTab("apicraft.blocks") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemStack.EMPTY;
            //return new ItemStack(Registries.ITEMS.get("block_apiary"));
        }
    };

    public static final CreativeModeTab TAB_ITEMS = new CreativeModeTab("apicraft.items") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemStack.EMPTY;
            //return new ItemStack(Registries.ITEMS.get("bee_comb_honey"));
        }
    };
}
