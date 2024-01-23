package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.recipes.managers.MutationRecipeManager;
import com.emily.apicraft.recipes.mutation.MutationRecipe;

import java.util.*;

public class MutationManager {
    private MutationManager(){}

    public static List<Mutation> findMutations(Combination<IAllele<AlleleSpecies>> pair){
        MutationRecipe recipe = MutationRecipeManager.INSTANCE.getRecipe(pair);
        if(recipe == null){
            return List.of();
        }
        else{
            return recipe.getResults();
        }
    }

    public static List<Mutation> findMutations(IAllele<AlleleSpecies> first, IAllele<AlleleSpecies> second){
        Combination<IAllele<AlleleSpecies>> pair = new Combination<>(first, second);
        return findMutations(pair);
    }
}
