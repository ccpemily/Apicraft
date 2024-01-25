package com.emily.apicraft.recipes.beeproduct;

import cofh.lib.util.recipes.SerializableRecipe;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.conditions.IBeeCondition;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public class BeeProductRecipe extends SerializableRecipe {
    protected IAllele<SpeciesData> species;
    protected List<Tuple<ItemStack, Float>> normalOutputs = new ArrayList<>();
    protected List<Tuple<ItemStack, Float>> specialOutputs = new ArrayList<>();
    protected List<IBeeCondition> specialConditions = new ArrayList<>();
    protected BeeProductRecipe(
            ResourceLocation recipeId,
            IAllele<SpeciesData> species,
            List<Tuple<ItemStack, Boolean>> outputs,
            List<Float> chances,
            List<IBeeCondition> conditions
    ) {
        super(recipeId);
        this.species = species;
    }

    public IAllele<SpeciesData> getSpecies(){
        return species;
    }
    public List<Tuple<ItemStack, Float>> getNormalOutputs(){
        return normalOutputs;
    }

    public List<Tuple<ItemStack, Float>> getSpecialOutputs(){
        return specialOutputs;
    }

    public List<IBeeCondition> getSpecialConditions(){
        return specialConditions;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.BEE_PRODUCT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.BEE_PRODUCT_RECIPE.get();
    }
}
