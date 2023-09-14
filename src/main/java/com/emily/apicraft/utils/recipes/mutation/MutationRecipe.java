package com.emily.apicraft.utils.recipes.mutation;

import cofh.lib.util.recipes.SerializableRecipe;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.utils.recipes.RecipeSerializers;
import com.emily.apicraft.utils.recipes.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public class MutationRecipe extends SerializableRecipe {
    protected static final float DEFAULT_MUTATE_CHANCE = 0.05f;
    protected final Combination<Alleles.Species> parents;
    protected final List<Alleles.Species> results = new ArrayList<>();
    protected final List<Float> baseChances = new ArrayList<>();
    protected final List<List<IBeeCondition>> conditions = new ArrayList<>();

    public MutationRecipe(ResourceLocation id, Combination<Alleles.Species> parents, List<Alleles.Species> results, List<Float> chances, List<List<IBeeCondition>> conditions) {
        super(id);
        if(parents != null){
            this.parents = parents;
        }
        else{
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null parents.");
            throw new IllegalArgumentException("Parents must be existed in mutation recipe.");
        }
        if(results != null){
            this.results.addAll(results);
        }
        else{
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null ");
        }
        if(chances != null){
            this.baseChances.addAll(chances);
            if(this.baseChances.size() < this.results.size()){
                for(int i = 0; i < this.results.size() - this.baseChances.size(); i++){
                    this.baseChances.add(DEFAULT_MUTATE_CHANCE);
                }
            }
        }
        else{
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null chance.");
        }
        if(conditions != null){
            for(List<IBeeCondition> cList : conditions){
                this.conditions.add(List.copyOf(cList));
            }
            if(this.conditions.size() < this.results.size()){
                for(int i = 0; i < this.results.size() - this.conditions.size(); i++){
                    this.conditions.add(new ArrayList<>());
                }
            }
        }
        else{
            Apicraft.LOGGER.warn("Invalid mutation recipe :" + id + "\nTrying to add a mutation with null conditions.");
        }
        trim();
    }

    public Combination<Alleles.Species> getParents(){
        return parents;
    }

    public List<Mutation> getResults(){
        List<Mutation> list = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            Mutation.MutationBuilder builder = new Mutation.MutationBuilder();
            builder.setParent(parents.getFirst(), parents.getSecond());
            builder.setResult(results.get(i));
            builder.setChance((int)Math.floor(baseChances.get(i) * 100));
            for(IBeeCondition condition : conditions.get(i)){
                builder.addCondition(condition);
            }
            list.add(builder.build());
        }
        return list;
    }

    private void trim(){
        ((ArrayList<Alleles.Species>)this.results).trimToSize();
        ((ArrayList<Float>)this.baseChances).trimToSize();
        ((ArrayList<List<IBeeCondition>>)this.conditions).trimToSize();
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
