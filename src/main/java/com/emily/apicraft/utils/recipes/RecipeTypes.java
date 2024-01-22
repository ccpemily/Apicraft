package com.emily.apicraft.utils.recipes;

import cofh.lib.util.recipes.SerializableRecipeType;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.recipes.mutation.MutationRecipe;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypes {
    private RecipeTypes(){}

    public static void register(){

    }

    public static final RegistryObject<SerializableRecipeType<MutationRecipe>> MUTATION_RECIPE = Registries.RECIPE_TYPES.register("mutation", () -> new SerializableRecipeType<>(Apicraft.MODID, "mutation"));
}
