package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionOwnerName;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class RequirePlayerSerializer implements IConditionSerializer<ConditionOwnerName> {
    @Override
    public ConditionOwnerName fromJson(ResourceLocation id, JsonObject jsonObject) {
        if(jsonObject.has(JsonUtils.NAME)){
            String name = jsonObject.get(JsonUtils.NAME).getAsString();
            return new ConditionOwnerName(name);
        }
        return null;
    }

    @Nullable
    @Override
    public ConditionOwnerName fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionOwnerName condition) {

    }
}
