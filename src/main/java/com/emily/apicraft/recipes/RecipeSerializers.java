package com.emily.apicraft.recipes;

import com.emily.apicraft.recipes.beeproduct.BeeProductRecipe;
import com.emily.apicraft.recipes.beeproduct.BeeProductRecipeSerializer;
import com.emily.apicraft.recipes.mutation.MutationRecipeSerializer;
import com.emily.apicraft.core.registry.Registries;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializers {
    private RecipeSerializers(){}

    public static void register(){
    }

    public static final RegistryObject<RecipeSerializer<MutationRecipe>> MUTATION_RECIPE_SERIALIZER =
            Registries.RECIPE_SERIALIZERS.register("mutation", MutationRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<BeeProductRecipe>> BEE_PRODUCT_RECIPE_SERIALIZER =
            Registries.RECIPE_SERIALIZERS.register("bee_product", BeeProductRecipeSerializer::new);
}
