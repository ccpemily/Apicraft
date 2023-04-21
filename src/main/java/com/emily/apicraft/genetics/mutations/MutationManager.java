package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MutationManager {
    private static final HashMap<Combination<IAllele<AlleleSpecies>>, List<Mutation>> mutationMap = new HashMap<>();

    public static void registerMutation(Mutation mutation){
        Combination<IAllele<AlleleSpecies>> combination = new Combination<>(mutation.getFirst(), mutation.getSecond());
        if(mutationMap.containsKey(combination)){
            mutationMap.get(combination).add(mutation);
        }
        else{
            mutationMap.put(combination, List.of(mutation));
        }

    }

    static{
        registerMutation(new Mutation.MutationBuilder()
                .setParent(Alleles.Species.FOREST, Alleles.Species.MEADOWS)
                .setResult(Alleles.Species.COMMON)
                .setChance(15).build()
        );
    }
    public static Optional<List<Mutation>> findMutations(Combination<IAllele<AlleleSpecies>> pair){
        if(mutationMap.containsKey(pair)){
            return Optional.of(mutationMap.get(pair));
        }
        return Optional.empty();
    }

    public static Optional<List<Mutation>> findMutations(IAllele<AlleleSpecies> first, IAllele<AlleleSpecies> second){
        Combination<IAllele<AlleleSpecies>> pair = new Combination<>(first, second);
        if(mutationMap.containsKey(pair)){
            return Optional.of(mutationMap.get(pair));
        }
        return Optional.empty();
    }
}
