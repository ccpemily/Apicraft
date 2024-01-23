package com.emily.apicraft.recipes.beeproduct;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeeProductRecipeSerializer implements RecipeSerializer<BeeProductRecipe> {
    @Override
    public @NotNull BeeProductRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
        return null;
    }

    @Override
    public @Nullable BeeProductRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
        return null;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull BeeProductRecipe pRecipe) {

    }
}
