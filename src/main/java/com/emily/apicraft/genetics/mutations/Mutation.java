package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.registry.Registries;
import com.emily.apicraft.genetics.alleles.Species;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.conditions.IBeeCondition;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mojang.logging.LogUtils.getLogger;

public class Mutation {
    private final ResourceLocation speciesFirst;
    private final ResourceLocation speciesSecond;
    private final ResourceLocation speciesResult;
    private final int baseChance;
    private final List<IBeeCondition> conditions;

    private Mutation(MutationBuilder builder){
        this.speciesFirst = builder.speciesFirst;
        this.speciesSecond = builder.speciesSecond;
        this.speciesResult = builder.speciesResult;
        this.baseChance = builder.baseChance;
        this.conditions = List.copyOf(builder.conditions);
    }

    public ResourceLocation getFirst(){
        return speciesFirst;
    }

    public ResourceLocation getSecond() {
        return speciesSecond;
    }

    public ResourceLocation getResult() {
        return speciesResult;
    }

    @SuppressWarnings("unchecked")
    public IAllele<SpeciesData> getResultSpecies(){
        return (IAllele<SpeciesData>) Registries.ALLELES.get(speciesResult);
    }
    public List<IBeeCondition> getConditions() {return conditions;}
    public int getBaseChance(){
        return baseChance;
    }

    public float getConditionalChance(IBeeHousing housing){
        float result = baseChance;
        for(IBeeCondition condition : conditions){
            result = condition.applyModifier(housing, result);
        }
        return housing.applyMutationModifier(result);
    }

    public static class MutationBuilder {
        private ResourceLocation speciesFirst;
        private ResourceLocation speciesSecond;
        private ResourceLocation speciesResult;
        private int baseChance = 0;
        private final List<IBeeCondition> conditions = new ArrayList<>();

        public MutationBuilder(){

        }

        private void validate(){
            if(speciesFirst == null || speciesSecond == null || speciesResult == null){
                getLogger().error("Invalid mutation found!");
                throw new IllegalArgumentException();
            }
        }

        public MutationBuilder setParent(ResourceLocation first, ResourceLocation second){
            this.speciesFirst = first;
            this.speciesSecond = second;
            return this;
        }

        public MutationBuilder setParent(String first, String second){
            this.speciesFirst = new ResourceLocation(Apicraft.MOD_ID, first);
            this.speciesSecond = new ResourceLocation(Apicraft.MOD_ID, second);
            return this;
        }

        public MutationBuilder setResult(ResourceLocation result){
            this.speciesResult = result;
            return this;
        }

        public MutationBuilder setResult(String result){
            this.speciesResult = new ResourceLocation(Apicraft.MOD_ID, result);
            return this;
        }

        public MutationBuilder setChance(int chance){
            this.baseChance = chance;
            return this;
        }

        public MutationBuilder addCondition(IBeeCondition condition){
            this.conditions.add(condition);
            return this;
        }

        public MutationBuilder addConditions(Collection<IBeeCondition> conditions){
            this.conditions.addAll(conditions);
            return this;
        }

        public Mutation build(){
            validate();
            return new Mutation(this);
        }
    }
}
