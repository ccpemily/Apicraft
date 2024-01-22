package com.emily.apicraft.utils.recipes.mutation;

import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonArray;
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
        List<IAllele<AlleleSpecies>> results = new ArrayList<>();
        List<Float> chances = new ArrayList<>();
        List<List<IBeeCondition>> conditions = new ArrayList<>();

        if(jsonObject.has(JsonUtils.PARENTS)){
            JsonObject parentsDict = jsonObject.getAsJsonObject(JsonUtils.PARENTS);
            if(parentsDict.has(JsonUtils.FIRST) && parentsDict.has(JsonUtils.SECOND)){
                IAllele<AlleleSpecies> first = (IAllele<AlleleSpecies>)Registries.ALLELES.get(parentsDict.get(JsonUtils.FIRST).getAsString());
                IAllele<AlleleSpecies> second = (IAllele<AlleleSpecies>)Registries.ALLELES.get(parentsDict.get(JsonUtils.SECOND).getAsString());
                parents = new Combination<>(first, second);
            }
        }
        if(jsonObject.has(JsonUtils.RESULTS)){
            JsonArray resultArray = jsonObject.getAsJsonArray(JsonUtils.RESULTS);
            for(var item : resultArray){
                JsonObject result = item.getAsJsonObject();
                IAllele<AlleleSpecies> species = null;
                float chance = 0;
                List<IBeeCondition> conditionList = new ArrayList<>();

                if(result.has(JsonUtils.SPECIES)){
                    species = (IAllele<AlleleSpecies>) Registries.ALLELES.get(result.get(JsonUtils.SPECIES).getAsString());
                }
                if(result.has(JsonUtils.CHANCE)){
                    chance = result.get(JsonUtils.CHANCE).getAsFloat();
                }
                if(result.has(JsonUtils.CONDITIONS)){
                    for(var cItem : result.getAsJsonArray(JsonUtils.CONDITIONS)){
                        if(cItem.getAsJsonObject().has(JsonUtils.TYPE)){
                            IConditionSerializer<IBeeCondition> serializer = (IConditionSerializer<IBeeCondition>) Registries.CONDITION_TYPES.get(cItem.getAsJsonObject().get(JsonUtils.TYPE).getAsString()).getSerializer().get();
                            IBeeCondition cond = serializer.fromJson(ResourceLocation.tryParse(cItem.getAsJsonObject().get(JsonUtils.TYPE).getAsString()), cItem.getAsJsonObject());
                            if(cond != null){
                                conditionList.add(cond);
                            }
                        }
                    }
                }
                if(species != null && chance != 0){
                    results.add(species);
                    chances.add(chance);
                    conditions.add(conditionList);
                }
            }
        }

        if(parents == null || results.isEmpty() || conditions.isEmpty()){
            return null;
        }
        else{
            return new MutationRecipe(id, parents, results, chances, conditions);
        }
    }

    @Override
    public @Nullable MutationRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf id, @NotNull MutationRecipe buffer) {

    }
}
