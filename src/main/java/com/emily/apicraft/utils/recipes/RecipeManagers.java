package com.emily.apicraft.utils.recipes;

import com.emily.apicraft.utils.recipes.managers.MutationRecipeManager;

public class RecipeManagers {
    public static void register(){
        RecipeManagerBus.register(MutationRecipeManager.INSTANCE);
    }
}
