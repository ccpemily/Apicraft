package com.emily.apicraft.recipes;

import net.minecraft.world.item.crafting.RecipeManager;

public interface ICustomManager {
    default void config() {

    }

    void refresh(RecipeManager recipeManager);
}
