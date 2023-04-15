package com.emily.apicraft.items.creativetab;

import com.emily.apicraft.registry.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ApicultureCreativeTab {
    public static final CreativeModeTab TAB_BEES = new CreativeModeTab("apiculture_group.bees") {
        @Override
        public @NotNull ItemStack makeIcon() {
            ItemStack stack = new ItemStack(Registries.ITEMS.get("bee_drone"));
            return stack;
        }
    };

    public static final CreativeModeTab TAB_BLOCKS = new CreativeModeTab("apiculture_group.blocks") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Registries.ITEMS.get("block_apiary"));
        }
    };

    public static final CreativeModeTab TAB_ITEMS = new CreativeModeTab("apiculture_group.items") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Registries.ITEMS.get("bee_comb_honey"));
        }
    };
}
