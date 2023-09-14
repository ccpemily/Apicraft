package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionHumidity;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class HumiditySerializer implements IConditionSerializer<ConditionHumidity> {
    @Override
    public ConditionHumidity fromJson(ResourceLocation id, JsonObject jsonObject) {
        return null;
    }

    @Nullable
    @Override
    public ConditionHumidity fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionHumidity condition) {

    }
}
