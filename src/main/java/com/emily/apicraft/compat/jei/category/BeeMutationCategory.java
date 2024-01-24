package com.emily.apicraft.compat.jei.category;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.Combination;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.genetics.conditions.*;
import com.emily.apicraft.genetics.mutations.Mutation;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import com.emily.apicraft.utils.ItemUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class BeeMutationCategory implements IRecipeCategory<MutationRecipe> {
    protected final RecipeType<MutationRecipe> type;

    protected Component name;
    protected IDrawable background;
    protected IDrawable icon;
    protected IGuiHelper gui;


    public BeeMutationCategory(IGuiHelper gui, ItemStack icon, RecipeType<MutationRecipe> type){
        this.type = type;
        this.icon = gui.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);
        this.name = Component.translatable("recipe.mutation.name");
        this.gui = gui;
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
        ItemStack queen;
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
        queen = ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_QUEEN_ID), mutation.getResult());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(List.of(
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_PRINCESS_ID), mutation.getResult()),
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_DRONE_ID), mutation.getResult()),
                ItemUtils.getDefaultBeeStack(new ResourceLocation(Apicraft.MOD_ID, ItemUtils.BEE_LARVA_ID), mutation.getResult())
        ));

        builder.addSlot(RecipeIngredientRole.INPUT, 17, 16).addItemStack(princess);
        builder.addSlot(RecipeIngredientRole.INPUT, 69, 16).addItemStack(drone);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 16).addItemStack(queen);

        List<IBeeCondition> conditions = recipe.getResult().getConditions();
        boolean alreadySet = false;
        for(var cond : conditions){
            if(cond instanceof ConditionRequireBlock blockCond){
                IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.CATALYST, 97, 38).addTooltipCallback(
                        (recipeSlotView, tooltip) -> {
                            if(!blockCond.getAcceptedBlocks().isEmpty()){
                                tooltip.add(Component.translatable("tooltip.accept_blocks").withStyle(ChatFormatting.DARK_GRAY));
                                boolean firstBlock = true;
                                MutableComponent component = Component.literal("  ");
                                for(var b : blockCond.getAcceptedBlocks()){
                                    if(!firstBlock){
                                        component.append(Component.literal(", "));
                                    }
                                    else{
                                        firstBlock = false;
                                    }
                                    component.append(b.getName().withStyle(ChatFormatting.YELLOW));
                                }
                                tooltip.add(component);
                            }
                            if(!blockCond.getAcceptedTags().isEmpty()){
                                tooltip.add(Component.translatable("tooltip.accept_block_tags").withStyle(ChatFormatting.DARK_GRAY));
                                blockCond.getAcceptedTags().forEach(
                                        tag -> tooltip.add(Component.literal("  #" + tag.location()).withStyle(ChatFormatting.GRAY))
                                );
                            }
                        }
                );
                for(var block : blockCond.getAcceptedBlocks()){
                    slot.addItemStack(new ItemStack(block));
                }
                for(var tag : blockCond.getAcceptedTags()){
                    List<Block> accepted = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(tag).stream().toList();
                    accepted.forEach(block -> slot.addItemStack(new ItemStack(block)));
                }
                alreadySet = true;
            }
            else if(!alreadySet && cond instanceof ConditionOwnerName playerCond){
                ItemStack headStack = Items.PLAYER_HEAD.getDefaultInstance();
                CompoundTag tag = new CompoundTag();
                tag.putString("SkullOwner", playerCond.getName());
                headStack.setTag(tag);
                builder.addSlot(RecipeIngredientRole.CATALYST, 97, 38).addItemStack(headStack).addTooltipCallback(
                        (recipeSlotView, tooltip) -> tooltip.add(
                                Component.translatable("tooltip.require_specific_player")
                                        .append(Component.literal(playerCond.getName()).withStyle(ChatFormatting.YELLOW))
                        )
                );
            }
        }

    }

    @Override
    public void draw(
            @NotNull MutationRecipe recipe,
            @NotNull IRecipeSlotsView recipeSlotsView,
            @NotNull GuiGraphics gui,
            double mouseX, double mouseY
    ){
        PoseStack stack = gui.pose();
        stack.pushPose();
        Font fontRenderer = Minecraft.getInstance().font;
        gui.drawCenteredString(fontRenderer, Component.translatable(recipe.getParents().getFirst().getName()), 26, 38, 0xffffff);
        gui.drawCenteredString(fontRenderer, Component.translatable(recipe.getParents().getSecond().getName()), 78, 38, 0xffffff);
        gui.drawCenteredString(fontRenderer, Component.translatable(recipe.getResult().getResult().getName()), 137, 38, 0xffffff);
        List<IBeeCondition> conditions = recipe.getResult().getConditions();
        if(conditions.isEmpty()){
            gui.drawCenteredString(fontRenderer, "%d%%".formatted(recipe.getResult().getBaseChance()), 105, 12, 0xffffff);
        }
        else {
            gui.drawCenteredString(fontRenderer, "[%d%%]".formatted(recipe.getResult().getBaseChance()), 105, 12,
                    displayAdditionalCondition(recipe) ? 0xff9999 : 0xffffff);
            ConditionRequireBlock blockCond = null;
            ConditionOwnerName playerCond = null;
            ConditionTemperature temperatureCond = null;
            ConditionHumidity humidityCond = null;
            for(var cond : conditions){
                if(cond instanceof ConditionRequireBlock){
                    blockCond = (ConditionRequireBlock) cond;
                }
                else if(cond instanceof ConditionOwnerName){
                    playerCond = (ConditionOwnerName) cond;
                }
                else if(cond instanceof ConditionTemperature){
                    temperatureCond = (ConditionTemperature) cond;
                }
                else if(cond instanceof ConditionHumidity){
                    humidityCond = (ConditionHumidity) cond;
                }
            }
            if(blockCond == null && playerCond == null && (temperatureCond != null || humidityCond != null)){
                int yOffsetTemperature = humidityCond == null ? 43 : 39;
                if(temperatureCond != null){
                    gui.drawCenteredString(
                            fontRenderer,
                            Component.literal("T:").withStyle(ChatFormatting.YELLOW).append(
                                    temperatureCond.isRestrict() ?
                                    Component.translatable(temperatureCond.getTemperatureStart().getName()).withStyle(ChatFormatting.GREEN) :
                                    Component.translatable(temperatureCond.getTemperatureStart().getName()).append("+").withStyle(ChatFormatting.GREEN)
                            ),
                            107, yOffsetTemperature, 0xffffff
                    );
                }
                if(humidityCond != null){
                    int yOffset = temperatureCond == null ? 4 : 10;
                    gui.drawCenteredString(
                            fontRenderer,
                            Component.literal("H:").withStyle(ChatFormatting.YELLOW).append(
                                    humidityCond.isRestrict() ?
                                            Component.translatable(humidityCond.getHumidityStart().getName()).withStyle(ChatFormatting.AQUA) :
                                            Component.translatable(humidityCond.getHumidityStart().getName()).append("+").withStyle(ChatFormatting.AQUA)
                            ),
                            108, 39 + yOffset, 0xffffff
                    );
                }
            }
            if(displayAdditionalCondition(recipe)){
                gui.drawCenteredString(fontRenderer, Component.literal("+").withStyle(ChatFormatting.RED), 118, 32, 0xffffff);
            }
        }
        stack.popPose();
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(
            @NotNull MutationRecipe recipe,
            @NotNull IRecipeSlotsView recipeSlotsView,
            double mouseX, double mouseY
    ) {
        List<IBeeCondition> conditions = recipe.getResult().getConditions();
        if(!conditions.isEmpty() && mouseX >= 90 && mouseX <= 120 && mouseY >= 11 && mouseY <= 19){
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable("tooltip.condition.title").withStyle(ChatFormatting.YELLOW));
            for(var cond : conditions){
                components.addAll(cond.getConditionTooltip());
            }
            return components;
        }
        if(displayAdditionalCondition(recipe) && mouseX >= 114 && mouseX <= 118 && mouseY >= 34 && mouseY <= 38){
            return List.of(
                    Component.translatable("tooltip.condition.contains_multiple"),
                    Component.translatable("tooltip.condition.contains_multiple.info")
            );
        }
        return List.of();
    }

    private static boolean displayAdditionalCondition(MutationRecipe recipe){
        return (recipe.hasConditionBlock() && recipe.hasConditionPlayer()) ||
                ((recipe.hasConditionBlock() || recipe.hasConditionPlayer()) && (recipe.hasConditionTemperature() || recipe.hasConditionHumidity()) ||
                        recipe.hasOtherConditions()
                );
    }
}
