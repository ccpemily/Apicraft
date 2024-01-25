package com.emily.apicraft.recipes;

import cofh.lib.util.recipes.SerializableRecipeType;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.recipes.beeproduct.BeeProductRecipe;
import com.emily.apicraft.core.registry.Registries;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypes {
    private RecipeTypes(){}

    public static void register(){

    }

    public static final RegistryObject<SerializableRecipeType<MutationRecipe>> MUTATION_RECIPE = Registries.RECIPE_TYPES.register(
            "mutation", () -> new SerializableRecipeType<>(Apicraft.MOD_ID, "mutation"));
    public static final RegistryObject<SerializableRecipeType<BeeProductRecipe>> BEE_PRODUCT_RECIPE = Registries.RECIPE_TYPES.register(
            "bee_product", () -> new SerializableRecipeType<>(Apicraft.MOD_ID, "bee_product"));
}
