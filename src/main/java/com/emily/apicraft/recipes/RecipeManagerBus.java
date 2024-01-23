package com.emily.apicraft.recipes;

import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeManagerBus {
    public final static RecipeManagerBus INSTANCE = new RecipeManagerBus();

    private RecipeManager clientRecipeManager;
    private RecipeManager serverRecipeManager;

    private final List<ICustomManager> managers = new ArrayList<>();

    public void setClientRecipeManager(RecipeManager recipeManager) {

        this.clientRecipeManager = recipeManager;
    }

    public void setServerRecipeManager(RecipeManager recipeManager) {

        this.serverRecipeManager = recipeManager;
    }

    public static void register(ICustomManager manager) {

        if (!INSTANCE.managers.contains(manager)) {
            INSTANCE.managers.add(manager);
        }
    }

    public void config() {

        for (ICustomManager sub : managers) {
            sub.config();
        }
    }

    public void refreshServer() {

        if (this.serverRecipeManager == null) {
            return;
        }
        for (ICustomManager sub : managers) {
            sub.refresh(this.serverRecipeManager);
        }
    }

    public void refreshClient() {

        if (this.clientRecipeManager == null) {
            return;
        }
        for (ICustomManager sub : managers) {
            sub.refresh(this.clientRecipeManager);
        }
    }
}
