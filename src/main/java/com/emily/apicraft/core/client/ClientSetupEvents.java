package com.emily.apicraft.core.client;

import cofh.lib.api.item.IColorableItem;
import com.emily.apicraft.Apicraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid= Apicraft.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    private static final List<Item> COLORABLE_ITEMS = new ArrayList<>();
    private ClientSetupEvents(){}

    @SubscribeEvent
    public static void colorSetupItem(final RegisterColorHandlersEvent.Item event) {
        ItemColors colors = event.getItemColors();
        for (Item colorable : COLORABLE_ITEMS) {
            colors.register(ColorableItemColor.INSTANCE, colorable);
        }
    }

    public static void addColorable(Item colorable) {
        if (colorable instanceof IColorableItem) {
            COLORABLE_ITEMS.add(colorable);
        }
    }

    private static class ColorableItemColor implements ItemColor {
        public static final ColorableItemColor INSTANCE = new ColorableItemColor();

        private ColorableItemColor(){}
        @Override
        public int getColor(@NotNull ItemStack stack, int layer) {
            Item item = stack.getItem();
            if(item instanceof IColorableItem){
                return ((IColorableItem) item).getColor(stack, layer);
            }
            return 0;
        }
    }
}
