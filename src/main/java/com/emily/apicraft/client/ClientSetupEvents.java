package com.emily.apicraft.client;

import cofh.lib.api.item.IColorableItem;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.client.gui.screens.ApiaryScreen;
import com.emily.apicraft.client.gui.screens.BeeHouseScreen;
import com.emily.apicraft.client.gui.screens.PortableAnalyzerScreen;
import com.emily.apicraft.client.gui.screens.ThermalApiaryScreen;
import com.emily.apicraft.client.particles.Particles;
import com.emily.apicraft.client.particles.implementation.BeeExploreParticle;
import com.emily.apicraft.client.particles.implementation.BeeRoundTripParticle;
import com.emily.apicraft.inventory.menu.PortableAnalyzerMenu;
import com.emily.apicraft.inventory.menu.blockentity.ApiaryMenu;
import com.emily.apicraft.inventory.menu.blockentity.BeeHouseMenu;
import com.emily.apicraft.inventory.menu.blockentity.ThermalApiaryMenu;
import com.emily.apicraft.core.registry.Registries;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.logging.LogUtils.getLogger;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Apicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    private static final List<Item> COLORABLE_ITEMS = new ArrayList<>();
    private ClientSetupEvents(){}

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event){
        event.enqueueWork(ClientSetupEvents::registerScreens);
    }

    @SuppressWarnings("unchecked")
    private static void registerScreens(){
        MenuScreens.register((MenuType<PortableAnalyzerMenu>) Registries.MENUS.get("portable_analyzer"), PortableAnalyzerScreen::new);
        MenuScreens.register((MenuType<BeeHouseMenu>) Registries.MENUS.get("bee_house"), BeeHouseScreen::new);
        MenuScreens.register((MenuType<ApiaryMenu>) Registries.MENUS.get("apiary"), ApiaryScreen::new);
        MenuScreens.register((MenuType<ThermalApiaryMenu>) Registries.MENUS.get("thermal_apiary"), ThermalApiaryScreen::new);
    }
    @SubscribeEvent
    public static void colorSetupItem(final RegisterColorHandlersEvent.Item event) {
        //ItemColors colors = event.getItemColors();
        COLORABLE_ITEMS.forEach(c -> event.register(ColorableItemColor.INSTANCE, c));
        /*for (Item colorable : COLORABLE_ITEMS) {
            event.register(ColorableItemColor.INSTANCE, colorable);
            //colors.register(ColorableItemColor.INSTANCE, colorable);
        }*/
    }

    @SubscribeEvent
    public static void registerParticleProviders(final RegisterParticleProvidersEvent event){
        Logger logger = getLogger();
        logger.debug("Registering particle providers");
        event.registerSpriteSet(Particles.BEE_ROUND_TRIP_PARTICLE.get(), BeeRoundTripParticle.Provider::new);
        event.registerSpriteSet(Particles.BEE_EXPLORE_PARTICLE.get(), BeeExploreParticle.Provider::new);
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
