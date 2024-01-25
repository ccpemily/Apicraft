package com.emily.apicraft.genetics.mutations;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.recipes.managers.MutationRecipeManager;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MutationManager {
    private MutationManager(){}

    public static List<Mutation> findMutations(Combination<IAllele<SpeciesData>> pair){
        List<Mutation> mutations = new ArrayList<>();
        Combination<ResourceLocation> locPair = new Combination<>(
                new ResourceLocation(Apicraft.MOD_ID, pair.getFirst().getRegistryName()),
                new ResourceLocation(Apicraft.MOD_ID, pair.getSecond().getRegistryName())
        );
        List<MutationRecipe> recipes = MutationRecipeManager.INSTANCE.getRecipe(locPair);
        if(recipes == null || recipes.isEmpty()){
            return List.of();
        }
        else{
            recipes.forEach((recipe -> mutations.add(recipe.getMutation())));
            return mutations;
        }
    }

    public static List<Mutation> findMutations(IAllele<SpeciesData> first, IAllele<SpeciesData> second){
        Combination<IAllele<SpeciesData>> pair = new Combination<>(first, second);
        return findMutations(pair);
    }
}
