package com.emily.apicraft.utils.recipes;

import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.recipes.mutation.MutationRecipe;
import com.emily.apicraft.utils.recipes.mutation.MutationRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializers {
    private RecipeSerializers(){}

    public static void register(){
    }

    public static final RegistryObject<RecipeSerializer<MutationRecipe>> MUTATION_RECIPE_SERIALIZER = Registries.RECIPE_SERIALIZERS.register("mutation", MutationRecipeSerializer::new);
}
