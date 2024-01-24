package com.emily.apicraft.compat.jei;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.compat.jei.category.BeeMutationCategory;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.recipes.RecipeTypes;
import com.emily.apicraft.recipes.beeproduct.BeeProductRecipe;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.ItemUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@JeiPlugin
public class ApicraftJeiPlugin implements IModPlugin {
    public static final RecipeType<MutationRecipe> MUTATION_RECIPE_TYPE = new RecipeType<>(RecipeTypes.MUTATION_RECIPE.getId(), MutationRecipe.class);
    public static final RecipeType<BeeProductRecipe> BEE_PRODUCT_RECIPE_TYPE = new RecipeType<>(RecipeTypes.BEE_PRODUCT_RECIPE.getId(), BeeProductRecipe.class);
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(Apicraft.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration){
        RecipeManager recipeManager = getRecipeManager();
        if(recipeManager == null){
            return;
        }
        registration.addRecipes(MUTATION_RECIPE_TYPE, recipeManager.getAllRecipesFor(RecipeTypes.MUTATION_RECIPE.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BeeMutationCategory(registration.getJeiHelpers().getGuiHelper(), ItemUtils.getDefaultDroneStack(), MUTATION_RECIPE_TYPE));
    }


    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_DRONE_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_PRINCESS_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_QUEEN_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_LARVA_ID), BeeSubtypeInterpreter.INSTANCE);
    }


    private RecipeManager getRecipeManager() {
        RecipeManager recipeManager = null;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            recipeManager = level.getRecipeManager();
        }
        return recipeManager;
    }


    private static class BeeSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        public static final BeeSubtypeInterpreter INSTANCE = new BeeSubtypeInterpreter();

        private BeeSubtypeInterpreter(){}

        @Override
        public @NotNull String apply(@NotNull ItemStack stack, @NotNull UidContext context) {
            if(!stack.hasTag()){
                return IIngredientSubtypeInterpreter.NONE;
            }
            Bee bee = BeeProviderCapability.get(stack).getBeeIndividual().orElse(Bee.getPure(Alleles.Species.FOREST));
            return bee.getGenome().getSpecies().toString();
        }
    }
}
