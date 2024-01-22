package com.emily.apicraft.utils.recipes.mutation;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutationRecipeSerializer implements RecipeSerializer<MutationRecipe> {
    @Override
    public @NotNull MutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
        return null;
    }

    @Override
    public @Nullable MutationRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf id, @NotNull MutationRecipe buffer) {

    }
}
