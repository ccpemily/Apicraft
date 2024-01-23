package com.emily.apicraft.recipes.mutation;

import cofh.lib.util.recipes.SerializableRecipe;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.conditions.IBeeCondition;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public class MutationRecipe extends SerializableRecipe {
    protected static final float DEFAULT_MUTATE_CHANCE = 0.05f;
    protected final Combination<IAllele<AlleleSpecies>> parents;
    protected final IAllele<AlleleSpecies> result;
    protected final float baseChance;
    protected final List<IBeeCondition> conditions = new ArrayList<>();

    public MutationRecipe(
            ResourceLocation id,
            Combination<IAllele<AlleleSpecies>> parents,
            IAllele<AlleleSpecies> result,
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
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null ");
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
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null conditions.");
        }
        trim();
    }

    public Combination<IAllele<AlleleSpecies>> getParents(){
        return parents;
    }

    public Mutation getResult(){
            Mutation.MutationBuilder builder = new Mutation.MutationBuilder();
        return builder.setParent(parents.getFirst(), parents.getSecond())
                .setResult(result)
                .setChance((int)Math.floor(baseChance * 100))
                .addConditions(conditions).build();
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
