package com.emily.apicraft.recipes.mutation;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.conditions.IBeeCondition;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.core.registry.Registries;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MutationRecipeSerializer implements RecipeSerializer<MutationRecipe> {
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull MutationRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
        Combination<IAllele<AlleleSpecies>> parents = null;
        IAllele<AlleleSpecies> result = null;
        float chance = 0;
        List<IBeeCondition> conditions = new ArrayList<>();

        if(jsonObject.has(JsonUtils.PARENTS)){
            JsonObject parentsDict = jsonObject.getAsJsonObject(JsonUtils.PARENTS);
            if(parentsDict.has(JsonUtils.FIRST) && parentsDict.has(JsonUtils.SECOND)){
                IAllele<AlleleSpecies> first = (IAllele<AlleleSpecies>)Registries.ALLELES.get(parentsDict.get(JsonUtils.FIRST).getAsString());
                IAllele<AlleleSpecies> second = (IAllele<AlleleSpecies>)Registries.ALLELES.get(parentsDict.get(JsonUtils.SECOND).getAsString());
                parents = new Combination<>(first, second);
            }
        }
        if(jsonObject.has(JsonUtils.RESULT)) {
            JsonObject r = jsonObject.getAsJsonObject(JsonUtils.RESULT);
            if (r.has(JsonUtils.SPECIES)) {
                result = (IAllele<AlleleSpecies>) Registries.ALLELES.get(r.get(JsonUtils.SPECIES).getAsString());
            }
            if (r.has(JsonUtils.CHANCE)) {
                chance = r.get(JsonUtils.CHANCE).getAsFloat();
            }
            if(r.has(JsonUtils.CONDITIONS)){
                for(var condition : r.getAsJsonArray(JsonUtils.CONDITIONS)){
                    if(condition.getAsJsonObject().has(JsonUtils.TYPE) && condition.getAsJsonObject().has(JsonUtils.VALUE)){
                        String type = condition.getAsJsonObject().get(JsonUtils.TYPE).getAsString();
                        IConditionSerializer<IBeeCondition> serializer = (IConditionSerializer<IBeeCondition>) Registries.CONDITION_TYPES.get(type).getSerializer().get();
                        IBeeCondition cond = serializer.fromJson(
                                ResourceLocation.tryParse(type),
                                condition.getAsJsonObject().get(JsonUtils.VALUE).getAsJsonObject()
                        );
                        if(cond != null){
                            boolean containedBy = false;
                            for(var c : conditions){
                                if(c.getClass().isInstance(cond)){
                                    containedBy = true;
                                    break;
                                }
                            }
                            conditions.add(cond);
                        }
                    }
                }
            }
        }

        if(parents == null || result == null){
            return null;
        }
        else{
            return new MutationRecipe(id, parents, result, chance, conditions);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable MutationRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        IAllele<AlleleSpecies> first = (IAllele<AlleleSpecies>) Registries.ALLELES.get(buffer.readResourceLocation());
        IAllele<AlleleSpecies> second = (IAllele<AlleleSpecies>) Registries.ALLELES.get(buffer.readResourceLocation());
        IAllele<AlleleSpecies> result = (IAllele<AlleleSpecies>) Registries.ALLELES.get(buffer.readResourceLocation());
        float baseChance = buffer.readFloat();
        int conditionCount = buffer.readInt();
        List<IBeeCondition> conditions = new ArrayList<>(conditionCount);
        for(int i = 0; i < conditionCount; i++){
            ResourceLocation condName = buffer.readResourceLocation();
            IConditionSerializer<?> serializer = Registries.CONDITION_SERIALIZERS.get(condName);
            IBeeCondition cond = serializer.fromNetwork(condName, buffer);
            if(cond != null){
                conditions.add(cond);
            }
        }
        return new MutationRecipe(id, new Combination<>(first, second), result, baseChance, conditions);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull MutationRecipe recipe) {
        Combination<IAllele<AlleleSpecies>> parents = recipe.getParents();
        Mutation mutation = recipe.getMutation();
        buffer.writeResourceLocation(new ResourceLocation(Apicraft.MOD_ID, parents.getFirst().toString()));
        buffer.writeResourceLocation(new ResourceLocation(Apicraft.MOD_ID, parents.getSecond().toString()));
        buffer.writeResourceLocation(new ResourceLocation(Apicraft.MOD_ID, mutation.getResult().toString()));
        buffer.writeFloat(recipe.baseChance);
        buffer.writeInt(mutation.getConditions().size());
        for(var cond : mutation.getConditions()){
            IConditionSerializer<IBeeCondition> serializer = (IConditionSerializer<IBeeCondition>) cond.getType().getSerializer().get();
            buffer.writeResourceLocation(cond.getType().getResourceLocation());
            serializer.toNetwork(buffer, cond);
        }
    }
}
