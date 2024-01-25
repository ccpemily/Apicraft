package com.emily.apicraft.core.datagen.providers.recipe.builder;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.conditions.*;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class MutationRecipeBuilder {
    private Combination<ResourceLocation> parents = null;
    private ResourceLocation result = null;
    private float chance = 0;
    private final List<IBeeCondition> conditions = new ArrayList<>();

    private MutationRecipeBuilder(){
    }

    public MutationRecipeBuilder setParents(ResourceLocation first, ResourceLocation second){
        if(first.equals(second)){
            throw new IllegalArgumentException();
        }
        else {
            parents = new Combination<>(first, second);
        }
        return this;
    }

    public MutationRecipeBuilder setParents(String first, String second){
        return setParents(new ResourceLocation(Apicraft.MOD_ID, first), new ResourceLocation(Apicraft.MOD_ID, second));
    }

    public MutationRecipeBuilder setResult(ResourceLocation result){
        this.result = result;
        return this;
    }

    public MutationRecipeBuilder setResult(String result){
        this.result = new ResourceLocation(Apicraft.MOD_ID, result);
        return this;
    }

    public MutationRecipeBuilder setChance(float chance){
        this.chance = chance;
        return this;
    }

    public MutationRecipeBuilder addCondition(IBeeCondition condition){
        conditions.add(condition);
        return this;
    }

    public MutationRecipeBuilder restrictTemperature(EnumTemperature temperature){
        return restrictTemperatureRange(temperature, temperature);
    }

    public MutationRecipeBuilder restrictTemperatureRange(EnumTemperature start, EnumTemperature end){
        return addCondition(new ConditionTemperature(start, end));
    }

    public MutationRecipeBuilder restrictHumidity(EnumHumidity humidity){
        return restrictHumidityRange(humidity, humidity);
    }

    public MutationRecipeBuilder restrictHumidityRange(EnumHumidity start, EnumHumidity end){
        return addCondition(new ConditionHumidity(start, end));
    }

    public MutationRecipeBuilder requirePlayerName(String name){
        return addCondition(new ConditionOwnerName(name));
    }

    // region RequireBlocks
    public MutationRecipeBuilder requireBlocks(Collection<Block> blocks, Collection<TagKey<Block>> tags){
        return addCondition(new ConditionRequireBlock(blocks, tags));
    }

    public MutationRecipeBuilder requireBlocks(Collection<Block> blocks){
        return requireBlocks(blocks, Collections.emptySet());
    }

    public MutationRecipeBuilder requireBlockTags(Collection<TagKey<Block>> tags){
        return requireBlocks(Collections.emptySet(), tags);
    }

    public MutationRecipeBuilder requireBlock(Block block){
        return requireBlocks(Collections.singleton(block));
    }

    public MutationRecipeBuilder requireBlockTag(TagKey<Block> tag){
        return requireBlockTags(Collections.singleton(tag));
    }
    // endregion


    // region RestrictBiomes
    public MutationRecipeBuilder restrictBiomes(Collection<ResourceLocation> biomeNames, Collection<TagKey<Biome>> biomeTags){
        return addCondition(new ConditionBiome(biomeNames, biomeTags));
    }

    public MutationRecipeBuilder restrictBiomes(Collection<ResourceLocation> biomeNames){
        return restrictBiomes(biomeNames, Collections.emptySet());
    }

    public MutationRecipeBuilder restrictBiomeTags(Collection<TagKey<Biome>> tags){
        return restrictBiomes(Collections.emptySet(), tags);
    }

    public MutationRecipeBuilder restrictBiome(ResourceLocation biomeName){
        return restrictBiomes(Collections.singleton(biomeName));
    }

    public MutationRecipeBuilder restrictBiomeTag(TagKey<Biome> biomeTag){
        return restrictBiomeTags(Collections.singleton(biomeTag));
    }
    // endregion

    public MutationRecipe build(){
        if(parents == null || result == null || chance == 0){
            throw new IllegalArgumentException();
        }
        String first = parents.getFirst().getPath().substring(8);
        String second = parents.getSecond().getPath().substring(8);
        String res = this.result.getPath().substring(8);
        ResourceLocation id = new ResourceLocation(Apicraft.MOD_ID, "mutation/" + first + "_" + second + "_to_" + res);
        return new MutationRecipe(id, parents, result, chance, conditions);
    }

    public void buildAndAccept(Consumer<FinishedRecipe> writer){
        writer.accept(new MutationRecipeResult(this.build()));
    }

    public static MutationRecipeBuilder get(){
        return new MutationRecipeBuilder();
    }

    protected static class MutationRecipeResult implements FinishedRecipe {
        private final MutationRecipe recipe;
        public MutationRecipeResult(MutationRecipe recipe){
            this.recipe = recipe;
        }


        @Override
        @SuppressWarnings("unchecked")
        public void serializeRecipeData(@NotNull JsonObject json) {
            JsonObject p = new JsonObject();
            p.addProperty(JsonUtils.FIRST, recipe.getParents().getFirst().getPath());
            p.addProperty(JsonUtils.SECOND, recipe.getParents().getSecond().getPath());
            json.add(JsonUtils.PARENTS, p);
            JsonObject r = new JsonObject();
            r.addProperty(JsonUtils.SPECIES, recipe.getResult().getPath());
            r.addProperty(JsonUtils.CHANCE, recipe.getChance());

            JsonArray condArray = new JsonArray();
            for (var cond : recipe.getConditions()){
                IConditionSerializer<IBeeCondition> serializer = (IConditionSerializer<IBeeCondition>) cond.getType().getSerializer().get();
                condArray.add(serializer.toJson(cond));
            }
            r.add(JsonUtils.CONDITIONS, condArray);
            json.add(JsonUtils.RESULT, r);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return recipe.getId();
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return RecipeSerializers.MUTATION_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
