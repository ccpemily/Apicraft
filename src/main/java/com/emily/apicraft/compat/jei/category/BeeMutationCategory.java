package com.emily.apicraft.compat.jei.category;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import com.emily.apicraft.utils.ItemUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BeeMutationCategory implements IRecipeCategory<MutationRecipe> {
    protected final RecipeType<MutationRecipe> type;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    public BeeMutationCategory(IGuiHelper gui, ItemStack icon, RecipeType<MutationRecipe> type){
        this.type = type;
        this.icon = gui.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);
        this.name = Component.translatable("recipe.mutation.name");
        this.background = gui.drawableBuilder(
                new ResourceLocation(Apicraft.MOD_ID, "textures/gui/recipe/jei_mutation.png"), 0, 0, 162, 61
                ).build();
    }

    @Override
    public @NotNull RecipeType<MutationRecipe> getRecipeType() {
        return type;
    }

    @Override
    public @NotNull Component getTitle() {
        return name;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull MutationRecipe recipe, @NotNull IFocusGroup focuses) {
        Combination<IAllele<AlleleSpecies>> parents = recipe.getParents();
        List<ItemStack> queens = new ArrayList<>();
        ItemStack princess = ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_PRINCESS_ID), parents.getFirst());
        ItemStack drone = ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_DRONE_ID), parents.getSecond());
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(
                List.of(ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_DRONE_ID), parents.getFirst()),
                        ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_QUEEN_ID), parents.getFirst()),
                        ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_LARVA_ID), parents.getFirst()),
                        ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_PRINCESS_ID), parents.getSecond()),
                        ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_QUEEN_ID), parents.getSecond()),
                        ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_LARVA_ID), parents.getSecond())
                )
        );
        Mutation mutation = recipe.getResult();
        queens.add(ItemUtils.getDefaultBeeStack(
                new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_QUEEN_ID), mutation.getResult()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(List.of(
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_PRINCESS_ID), mutation.getResult()),
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_DRONE_ID), mutation.getResult()),
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_LARVA_ID), mutation.getResult())
        ));

        builder.addSlot(RecipeIngredientRole.INPUT, 19, 16).addItemStack(princess);
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 16).addItemStack(drone);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 126, 16).addItemStacks(queens);
    }
}
