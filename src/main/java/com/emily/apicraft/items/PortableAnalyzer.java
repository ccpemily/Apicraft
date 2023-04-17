package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import com.emily.apicraft.inventory.containers.PortableAnalyzerContainer;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PortableAnalyzer extends ItemCoFH implements MenuProvider {
    public PortableAnalyzer() {
        super(new Item.Properties().stacksTo(1).tab(CreativeTabs.TAB_ITEMS));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if(handIn == InteractionHand.OFF_HAND){
            return InteractionResultHolder.pass(stack);
        }
        if(playerIn instanceof ServerPlayer){
            NetworkHooks.openScreen((ServerPlayer) playerIn, this);
        }
        return InteractionResultHolder.success(stack);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new PortableAnalyzerContainer(i, inventory, player);
    }
}
