package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.block.IBeeHousing;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.logging.LogUtils.getLogger;

public class Mutation {
    private final Alleles.Species speciesFirst;
    private final Alleles.Species speciesSecond;
    private final Alleles.Species speciesResult;
    private final int baseChance;
    private final List<IBeeCondition> conditions;

    private Mutation(MutationBuilder builder){
        this.speciesFirst = builder.speciesFirst;
        this.speciesSecond = builder.speciesSecond;
        this.speciesResult = builder.speciesResult;
        this.baseChance = builder.baseChance;
        this.conditions = List.copyOf(builder.conditions);
    }

    public Alleles.Species getFirst(){
        return speciesFirst;
    }

    public Alleles.Species getSecond() {
        return speciesSecond;
    }

    public Alleles.Species getResult() {
        return speciesResult;
    }

    public float getConditionalChance(IBeeHousing housing){
        float result = baseChance;
        for(IBeeCondition condition : conditions){
            result = condition.applyModifier(housing, result);
        }
        return result;
    }

    public static class MutationBuilder {
        private Alleles.Species speciesFirst;
        private Alleles.Species speciesSecond;
        private Alleles.Species speciesResult;
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

        public MutationBuilder setParent(Alleles.Species first, Alleles.Species second){
            this.speciesFirst = first;
            this.speciesSecond = second;
            return this;
        }

        public MutationBuilder setResult(Alleles.Species result){
            this.speciesResult = result;
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

        public Mutation build(){
            validate();
            return new Mutation(this);
        }
    }
}
