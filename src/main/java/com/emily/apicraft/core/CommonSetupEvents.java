package com.emily.apicraft.core;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.utils.recipes.RecipeManagerBus;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
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
        event.addListener((ResourceManagerReloadListener) manager ->
                RecipeManagerBus.INSTANCE.setServerRecipeManager(event.getServerResources().getRecipeManager())
        );
    }

    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {
        RecipeManagerBus.INSTANCE.refreshServer();
        RecipeManagerBus.INSTANCE.refreshClient();
    }

    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {
        RecipeManagerBus.INSTANCE.setClientRecipeManager(event.getRecipeManager());
        RecipeManagerBus.INSTANCE.refreshClient();
    }
}
