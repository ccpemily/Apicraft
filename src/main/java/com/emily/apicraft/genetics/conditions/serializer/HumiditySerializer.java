package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.genetics.conditions.ConditionHumidity;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class HumiditySerializer implements IConditionSerializer<ConditionHumidity> {
    @Override
    public ConditionHumidity fromJson(ResourceLocation id, JsonObject jsonObject) {
        EnumHumidity from = null;
        EnumHumidity to = null;
        if(jsonObject.has(JsonUtils.RESTRICT)){
            String val = jsonObject.get(JsonUtils.RESTRICT).getAsString();
            from = EnumHumidity.valueOf(val.toUpperCase(Locale.ENGLISH));
            to = from;
        }
        else if(jsonObject.has(JsonUtils.FROM) && jsonObject.has(JsonUtils.TO)){
            String fromVal = jsonObject.get(JsonUtils.FROM).getAsString();
            String toVal = jsonObject.get(JsonUtils.TO).getAsString();
            from = EnumHumidity.valueOf(fromVal.toUpperCase(Locale.ENGLISH));
            to = EnumHumidity.valueOf(toVal.toUpperCase(Locale.ENGLISH));
        }
        if(from != null){
            return new ConditionHumidity(from, to);
        }
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
