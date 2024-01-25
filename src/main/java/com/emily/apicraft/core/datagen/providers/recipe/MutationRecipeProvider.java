package com.emily.apicraft.core.datagen.providers.recipe;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.core.datagen.providers.recipe.builder.MutationRecipeBuilder;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.mutation.MutationRecipe;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MutationRecipeProvider extends RecipeProvider {
    private final ExistingFileHelper existingFileHelper;

    public MutationRecipeProvider(PackOutput pOutput, ExistingFileHelper helper) {
        super(pOutput);
        this.existingFileHelper = helper;
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        Consumer<FinishedRecipe> trackingWriter = pWriter.andThen(
                recipe -> existingFileHelper.trackGenerated(recipe.getId(), PackType.SERVER_DATA, ".json", "recipes")
        );
        addRecipes(trackingWriter);
    }

    protected void addRecipes(Consumer<FinishedRecipe> writer){
        // region Common branch
        String[] overworldSpecies = {
                "species.forest", "species.meadows", "species.wintry",
                "species.modest", "species.tropical", "species.marshy"
        };
        for(int i = 0; i < overworldSpecies.length; i++){
            for(int j = i + 1; j < overworldSpecies.length; j++){
                MutationRecipeBuilder.get()
                        .setParents(overworldSpecies[i], overworldSpecies[j])
                        .setResult("species.common").setChance(0.15f)
                        .buildAndAccept(writer);
            }
            MutationRecipeBuilder.get()
                    .setParents("species.common", overworldSpecies[i])
                    .setResult("species.cultivated").setChance(0.12f)
                    .buildAndAccept(writer);
        }
        //endregion

        // region Noble branch
        MutationRecipeBuilder.get()
                .setParents("species.cultivated", "species.common")
                .setResult("species.noble").setChance(0.10f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.noble", "species.cultivated")
                .setResult("species.majestic").setChance(0.08f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.majestic", "species.noble")
                .setResult("species.imperial").setChance(0.05f)
                .buildAndAccept(writer);
        // endregion

        // region Industrious branch
        MutationRecipeBuilder.get()
                .setParents("species.cultivated", "species.common")
                .setResult("species.diligent").setChance(0.1f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.diligent", "species.cultivated")
                .setResult("species.unweary").setChance(0.08f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.unweary", "species.diligent")
                .setResult("species.industrious").setChance(0.05f)
                .buildAndAccept(writer);
        // endregion

        // region Heroic branch
        MutationRecipeBuilder.get()
                .setParents("species.steadfast", "species.valiant")
                .setResult("species.heroic").setChance(0.03f)
                .restrictBiomeTag(BiomeTags.IS_FOREST)
                .buildAndAccept(writer);
        // endregion

        // region Infernal branch
        MutationRecipeBuilder.get()
                .setParents("species.cultivated", "species.modest")
                .setResult("species.sinister").setChance(0.6f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.cultivated", "species.tropical")
                .setResult("species.sinister").setChance(0.6f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.sinister", "species.modest")
                .setResult("species.fiendish").setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.sinister", "species.tropical")
                .setResult("species.fiendish").setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.sinister", "species.cultivated")
                .setResult("species.fiendish").setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.fiendish", "species.sinister")
                .setResult("species.demonic").setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .requireBlock(Blocks.MAGMA_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Austere branch
        MutationRecipeBuilder.get()
                .setParents("species.modest", "species.sinister")
                .setResult("species.frugal").setChance(0.16f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.modest", "species.fiendish")
                .setResult("species.frugal").setChance(0.1f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.frugal", "species.modest")
                .setResult("species.austere").setChance(0.08f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        // endregion

        // region Tropical branch
        MutationRecipeBuilder.get()
                .setParents("species.austere", "species.tropical")
                .setResult("species.exotic").setChance(0.12f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.exotic", "species.tropical")
                .setResult("species.edenic").setChance(0.08f)
                .requireBlock(Blocks.LAPIS_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Spectral branch
        MutationRecipeBuilder.get()
                .setParents("species.hermitic", "species.ended")
                .setResult("species.spectral").setChance(0.04f)
                .requireBlock(Blocks.END_STONE)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.spectral", "species.ended")
                .setResult("species.phantasmal").setChance(0.02f)
                .requireBlock(Blocks.PURPUR_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Glacial branch
        MutationRecipeBuilder.get()
                .setParents("species.industrious", "species.wintry")
                .setResult("species.icy").setChance(0.12f)
                .restrictTemperatureRange(EnumTemperature.ICY, EnumTemperature.COLD)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.icy", "species.wintry")
                .setResult("species.glacial").setChance(0.08f)
                .restrictTemperature(EnumTemperature.ICY)
                .buildAndAccept(writer);
        // endregion

        // region Vengeful branch
        MutationRecipeBuilder.get()
                .setParents("species.monastic", "species.demonic")
                .setResult("species.vindictive").setChance(0.04f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.vindictive", "species.demonic")
                .setResult("species.vengeful").setChance(0.08f)
                .requireBlock(Blocks.CRYING_OBSIDIAN)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.vindictive", "species.monastic")
                .setResult("species.vengeful").setChance(0.08f)
                .requireBlock(Blocks.CRYING_OBSIDIAN)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.vindictive", "species.vengeful")
                .setResult("species.avenging").setChance(0.02f)
                .restrictTemperature(EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .requireBlock(Blocks.SCULK)
                .buildAndAccept(writer);
        // endregion

        // region Agrarian branch
        MutationRecipeBuilder.get()
                .setParents("species.diligent", "species.meadows")
                .setResult("species.rural").setChance(0.12f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.rural", "species.unweary")
                .setResult("species.farmer").setChance(0.1f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.farmer", "species.industrious")
                .setResult("species.agrarian").setChance(0.06f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Boggy branch
        MutationRecipeBuilder.get()
                .setParents("species.marshy", "species.noble")
                .setResult("species.miry").setChance(0.15f)
                .restrictTemperature(EnumTemperature.WARM)
                .restrictHumidity(EnumHumidity.DAMP)
                .requireBlock(Blocks.MUD)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.miry", "species.marshy")
                .setResult("species.boggy").setChance(0.08f)
                .restrictTemperature(EnumTemperature.WARM)
                .restrictHumidity(EnumHumidity.DAMP)
                .requireBlock(Blocks.MUD)
                .buildAndAccept(writer);
        // endregion

        // region Monastic branch
        MutationRecipeBuilder.get()
                .setParents("species.monastic", "species.austere")
                .setResult("species.secluded").setChance(0.1f)
                .requireBlockTag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents("species.secluded", "species.monastic")
                .setResult("species.hermitic").setChance(0.04f)
                .requireBlockTag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .buildAndAccept(writer);
        // endregion
    }
}
