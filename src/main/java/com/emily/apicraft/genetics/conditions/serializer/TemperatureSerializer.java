package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.conditions.ConditionTemperature;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class TemperatureSerializer implements IConditionSerializer<ConditionTemperature> {
    @Override
    public ConditionTemperature fromJson(ResourceLocation id, JsonObject jsonObject) {
        EnumTemperature from = null;
        EnumTemperature to = null;
        if(jsonObject.has(JsonUtils.RESTRICT)){
            String val = jsonObject.get(JsonUtils.RESTRICT).getAsString();
            try {
                from = EnumTemperature.valueOf(val.toUpperCase(Locale.ENGLISH));
            }
            catch (IllegalArgumentException e){
                return null;
            }
            to = from;
        }
        else if(jsonObject.has(JsonUtils.FROM) && jsonObject.has(JsonUtils.TO)){
            String fromVal = jsonObject.get(JsonUtils.FROM).getAsString();
            String toVal = jsonObject.get(JsonUtils.TO).getAsString();
            try {
                from = EnumTemperature.valueOf(fromVal.toUpperCase(Locale.ENGLISH));
                to = EnumTemperature.valueOf(toVal.toUpperCase(Locale.ENGLISH));
            }
            catch (IllegalArgumentException e){
                return null;
            }
        }
        if(from != null){
            return new ConditionTemperature(from, to);
        }
        return null;
    }

    @Nullable
    @Override
    public ConditionTemperature fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionTemperature condition) {

    }
}
