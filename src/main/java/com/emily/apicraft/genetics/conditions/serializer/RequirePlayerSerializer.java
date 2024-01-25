package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionOwnerName;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class RequirePlayerSerializer implements IConditionSerializer<ConditionOwnerName> {
    @Override
    public ConditionOwnerName fromJson(ResourceLocation id, JsonObject jsonObject) {
        if(jsonObject.has(JsonUtils.NAME)){
            String name = jsonObject.get(JsonUtils.NAME).getAsString();
            return new ConditionOwnerName(name);
        }
        return null;
    }

    @Override
    public JsonObject toJson(ConditionOwnerName condition) {
        JsonObject object = new JsonObject();
        JsonObject value = new JsonObject();
        object.addProperty(JsonUtils.TYPE, condition.getType().getResourceLocation().toString());
        value.addProperty(JsonUtils.NAME, condition.getName());
        object.add(JsonUtils.VALUE, value);
        return object;
    }

    @Nullable
    @Override
    public ConditionOwnerName fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        int length = buffer.readInt();
        String name = buffer.readBytes(length).toString(StandardCharsets.UTF_8);
        return new ConditionOwnerName(name);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionOwnerName condition) {
        String name = condition.getName();
        buffer.writeInt(name.length());
        buffer.writeBytes(name.getBytes(StandardCharsets.UTF_8));
    }
}
