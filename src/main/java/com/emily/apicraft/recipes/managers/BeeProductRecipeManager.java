package com.emily.apicraft.recipes.managers;

import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.recipes.ICustomManager;
import com.emily.apicraft.recipes.RecipeTypes;
import com.emily.apicraft.recipes.beeproduct.BeeProductRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

import java.util.Map;

import static com.mojang.logging.LogUtils.getLogger;

public class BeeProductRecipeManager implements ICustomManager {
    public static final BeeProductRecipeManager INSTANCE = new BeeProductRecipeManager();

    protected Map<IAllele<SpeciesData>, BeeProductRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    public void addRecipe(BeeProductRecipe recipe){
        recipeMap.put(recipe.getSpecies(), recipe);
    }

    public BeeProductRecipe getRecipe(IAllele<SpeciesData> species){
        return recipeMap.get(species);
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        recipeMap.clear();
        var recipes = recipeManager.byType(RecipeTypes.BEE_PRODUCT_RECIPE.get());
        for(var entry : recipes.entrySet()){
            addRecipe(entry.getValue());
        }
        Logger logger = getLogger();
        logger.debug(String.format("Loaded %d bee product recipes from refreshing event.", recipeMap.size()));
    }
}
