package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.conditions.IBeeCondition;
import com.emily.apicraft.interfaces.block.IBeeHousing;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.logging.LogUtils.getLogger;

public class Mutation {
    private final IAllele<AlleleSpecies> speciesFirst;
    private final IAllele<AlleleSpecies> speciesSecond;
    private final IAllele<AlleleSpecies> speciesResult;
    private final int baseChance;
    private final List<IBeeCondition> conditions;

    private Mutation(MutationBuilder builder){
        this.speciesFirst = builder.speciesFirst;
        this.speciesSecond = builder.speciesSecond;
        this.speciesResult = builder.speciesResult;
        this.baseChance = builder.baseChance;
        this.conditions = List.copyOf(builder.conditions);
    }

    public IAllele<AlleleSpecies> getFirst(){
        return speciesFirst;
    }

    public IAllele<AlleleSpecies> getSecond() {
        return speciesSecond;
    }

    public IAllele<AlleleSpecies> getResult() {
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
        private IAllele<AlleleSpecies> speciesFirst;
        private IAllele<AlleleSpecies> speciesSecond;
        private IAllele<AlleleSpecies> speciesResult;
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

        public MutationBuilder setParent(IAllele<AlleleSpecies> first, IAllele<AlleleSpecies> second){
            this.speciesFirst = first;
            this.speciesSecond = second;
            return this;
        }

        public MutationBuilder setResult(IAllele<AlleleSpecies> result){
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
