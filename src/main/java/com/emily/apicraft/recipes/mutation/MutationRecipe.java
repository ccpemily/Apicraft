package com.emily.apicraft.recipes.mutation;

import cofh.lib.util.recipes.SerializableRecipe;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.conditions.*;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MutationRecipe extends SerializableRecipe {
    protected static final float DEFAULT_MUTATE_CHANCE = 0.05f;
    protected final Combination<ResourceLocation> parents;
    protected final ResourceLocation result;
    protected final float baseChance;
    protected final List<IBeeCondition> conditions = new ArrayList<>();

    protected boolean hasConditionBlock = false;
    protected boolean hasConditionPlayer = false;
    protected boolean hasConditionTemperature = false;
    protected boolean hasConditionHumidity = false;
    protected boolean hasOtherConditions = false;

    public MutationRecipe(
            ResourceLocation id,
            Combination<ResourceLocation> parents,
            ResourceLocation result,
            float chance,
            List<IBeeCondition> conditions
    ) {
        super(id);
        if(parents != null){
            this.parents = parents;
        }
        else{
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null parents.");
            throw new IllegalArgumentException("Parents must be existed in mutation recipe.");
        }
        if(result != null){
            this.result = result;
        }
        else{
            this.result = null;
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null result.");
        }
        if(chance > 0 && chance <= 1){
            this.baseChance = chance;
        }
        else{
            this.baseChance = DEFAULT_MUTATE_CHANCE;
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null chance.");
        }
        if(conditions != null && !conditions.isEmpty()){
            this.conditions.addAll(conditions);
        }
        else{
            if(conditions == null) Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null conditions.");
        }
        for(IBeeCondition condition : this.conditions){
            if(condition instanceof ConditionRequireBlock){
                hasConditionBlock = true;
            }
            else if(condition instanceof ConditionOwnerName){
                hasConditionPlayer = true;
            }
            else if(condition instanceof ConditionTemperature){
                hasConditionTemperature = true;
            }
            else if(condition instanceof ConditionHumidity){
                hasConditionHumidity = true;
            }
            else {
                hasOtherConditions = true;
            }
        }
        trim();
    }

    public Combination<ResourceLocation> getParents(){
        return parents;
    }
    public ResourceLocation getResult(){
        return result;
    }
    public float getChance(){
        return baseChance;
    }

    public Collection<IBeeCondition> getConditions(){
        return conditions;
    }

    public Mutation getMutation(){
            Mutation.MutationBuilder builder = new Mutation.MutationBuilder();
        return builder.setParent(parents.getFirst(), parents.getSecond())
                .setResult(result)
                .setChance((int)Math.floor(baseChance * 100))
                .addConditions(conditions).build();
    }

    public boolean hasConditionBlock() {
        return hasConditionBlock;
    }

    public boolean hasConditionPlayer() {
        return hasConditionPlayer;
    }

    public boolean hasConditionTemperature() {
        return hasConditionTemperature;
    }

    public boolean hasConditionHumidity() {
        return hasConditionHumidity;
    }
    public boolean hasOtherConditions(){
        return hasOtherConditions;
    }

    private void trim(){
        ((ArrayList<IBeeCondition>)conditions).trimToSize();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.MUTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.MUTATION_RECIPE.get();
    }
}
