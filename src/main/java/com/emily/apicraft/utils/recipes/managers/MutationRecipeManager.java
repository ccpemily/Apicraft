package com.emily.apicraft.utils.recipes.managers;

import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.utils.recipes.ICustomManager;
import com.emily.apicraft.utils.recipes.RecipeTypes;
import com.emily.apicraft.utils.recipes.mutation.MutationRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

import java.util.Map;

import static com.mojang.logging.LogUtils.getLogger;

public class MutationRecipeManager implements ICustomManager {
    public static final MutationRecipeManager INSTANCE = new MutationRecipeManager();

    protected Map<Combination<IAllele<AlleleSpecies>>, MutationRecipe> recipeMap = new Object2ObjectOpenHashMap<>();

    public void addRecipe(MutationRecipe recipe){
        recipeMap.put(recipe.getParents(), recipe);
    }

    public MutationRecipe getRecipe(Combination<Alleles.Species> combination){
        return recipeMap.get(combination);
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        recipeMap.clear();
        var recipes = recipeManager.byType(RecipeTypes.MUTATION_RECIPE.get());
        for(var entry : recipes.entrySet()){
            addRecipe(entry.getValue());
        }
        Logger logger = getLogger();
        logger.debug(String.format("Loaded %d mutation recipes from refreshing event.", recipeMap.size()));
    }
}
