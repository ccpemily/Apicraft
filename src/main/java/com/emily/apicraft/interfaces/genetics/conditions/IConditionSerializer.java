package com.emily.apicraft.interfaces.genetics.conditions;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public interface IConditionSerializer<T extends IBeeCondition> {
    T fromJson(ResourceLocation id, JsonObject jsonObject);

    @Nullable
    T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer);

    void toNetwork(FriendlyByteBuf buffer, T condition);
}
