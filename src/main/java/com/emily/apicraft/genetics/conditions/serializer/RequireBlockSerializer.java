package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionRequireBlock;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class RequireBlockSerializer implements IConditionSerializer<ConditionRequireBlock> {
    @Override
    public ConditionRequireBlock fromJson(ResourceLocation id, JsonObject jsonObject) {
        return null;
    }

    @Nullable
    @Override
    public ConditionRequireBlock fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionRequireBlock condition) {

    }
}
