package com.emily.apicraft.core;

import com.emily.apicraft.Apicraft;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Apicraft.MODID)
public class CommonSetupEvents {
    private CommonSetupEvents(){

    }

    @SubscribeEvent
    public static void addReloadListener(final AddReloadListenerEvent event) {
    }

    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {
    }

        @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {
    }
}
