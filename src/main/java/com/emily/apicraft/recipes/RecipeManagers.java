package com.emily.apicraft.recipes;

import com.emily.apicraft.recipes.managers.BeeProductRecipeManager;
import com.emily.apicraft.recipes.managers.MutationRecipeManager;

public class RecipeManagers {
    public static void register(){
        RecipeManagerBus.register(MutationRecipeManager.INSTANCE);
        RecipeManagerBus.register(BeeProductRecipeManager.INSTANCE);
    }
}
