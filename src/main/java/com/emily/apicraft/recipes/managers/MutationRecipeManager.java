package com.emily.apicraft.recipes.managers;

import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.recipes.ICustomManager;
import com.emily.apicraft.recipes.RecipeTypes;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

import java.util.*;

import static com.mojang.logging.LogUtils.getLogger;

public class MutationRecipeManager implements ICustomManager {
    public static final MutationRecipeManager INSTANCE = new MutationRecipeManager();

    protected Map<Combination<ResourceLocation>, List<MutationRecipe>> recipeMap = new Object2ObjectOpenHashMap<>();

    public void addRecipe(MutationRecipe recipe){
        if(recipeMap.containsKey(recipe.getParents())){
            recipeMap.get(recipe.getParents()).add(recipe);
        }
        else{
            List<MutationRecipe> recipes = new ArrayList<>(Collections.singleton(recipe));
            recipeMap.put(recipe.getParents(), recipes);
        }
    }

    public List<MutationRecipe> getRecipe(Combination<ResourceLocation> combination){
        return recipeMap.get(combination);
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        recipeMap.clear();
        var recipes = recipeManager.byType(RecipeTypes.MUTATION_RECIPE.get());
        for(var entry : recipes.entrySet()){
            addRecipe(entry.getValue());
        }
        int size = 0;
        for(var val : recipeMap.values()){
            size += val.size();
        }
        Logger logger = getLogger();
        logger.debug(String.format("Loaded %d mutation recipes from refreshing event.", size));
    }
}
