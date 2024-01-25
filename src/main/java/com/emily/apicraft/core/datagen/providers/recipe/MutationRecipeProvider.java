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
        Alleles.Species[] overworldSpecies = {
                Alleles.Species.FOREST, Alleles.Species.MEADOWS, Alleles.Species.WINTRY,
                Alleles.Species.MODEST, Alleles.Species.TROPICAL, Alleles.Species.MARSHY
        };
        for(int i = 0; i < overworldSpecies.length; i++){
            for(int j = i + 1; j < overworldSpecies.length; j++){
                MutationRecipeBuilder.get()
                        .setParents(overworldSpecies[i], overworldSpecies[j])
                        .setResult(Alleles.Species.COMMON).setChance(0.15f)
                        .buildAndAccept(writer);
            }
            MutationRecipeBuilder.get()
                    .setParents(Alleles.Species.COMMON, overworldSpecies[i])
                    .setResult(Alleles.Species.CULTIVATED).setChance(0.12f)
                    .buildAndAccept(writer);
        }
        //endregion

        // region Noble branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.CULTIVATED, Alleles.Species.COMMON)
                .setResult(Alleles.Species.NOBLE).setChance(0.10f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.NOBLE, Alleles.Species.CULTIVATED)
                .setResult(Alleles.Species.MAJESTIC).setChance(0.08f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MAJESTIC, Alleles.Species.NOBLE)
                .setResult(Alleles.Species.IMPERIAL).setChance(0.05f)
                .buildAndAccept(writer);
        // endregion

        // region Industrious branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.CULTIVATED, Alleles.Species.COMMON)
                .setResult(Alleles.Species.DILIGENT).setChance(0.1f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.DILIGENT, Alleles.Species.CULTIVATED)
                .setResult(Alleles.Species.UNWEARY).setChance(0.08f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.UNWEARY, Alleles.Species.DILIGENT)
                .setResult(Alleles.Species.INDUSTRIOUS).setChance(0.05f)
                .buildAndAccept(writer);
        // endregion

        // region Heroic branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.STEADFAST, Alleles.Species.VALIANT)
                .setResult(Alleles.Species.HEROIC).setChance(0.03f)
                .restrictBiomeTag(BiomeTags.IS_FOREST)
                .buildAndAccept(writer);
        // endregion

        // region Infernal branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.CULTIVATED, Alleles.Species.MODEST)
                .setResult(Alleles.Species.SINISTER).setChance(0.6f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.CULTIVATED, Alleles.Species.TROPICAL)
                .setResult(Alleles.Species.SINISTER).setChance(0.6f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.SINISTER, Alleles.Species.MODEST)
                .setResult(Alleles.Species.FIENDISH).setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.SINISTER, Alleles.Species.TROPICAL)
                .setResult(Alleles.Species.FIENDISH).setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.SINISTER, Alleles.Species.CULTIVATED)
                .setResult(Alleles.Species.FIENDISH).setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.FIENDISH, Alleles.Species.SINISTER)
                .setResult(Alleles.Species.DEMONIC).setChance(0.4f)
                .restrictBiomeTag(BiomeTags.IS_NETHER)
                .requireBlock(Blocks.MAGMA_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Austere branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MODEST, Alleles.Species.SINISTER)
                .setResult(Alleles.Species.FRUGAL).setChance(0.16f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MODEST, Alleles.Species.FIENDISH)
                .setResult(Alleles.Species.FRUGAL).setChance(0.1f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.FRUGAL, Alleles.Species.MODEST)
                .setResult(Alleles.Species.AUSTERE).setChance(0.08f)
                .restrictTemperatureRange(EnumTemperature.HOT, EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .buildAndAccept(writer);
        // endregion

        // region Tropical branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.AUSTERE, Alleles.Species.TROPICAL)
                .setResult(Alleles.Species.EXOTIC).setChance(0.12f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.EXOTIC, Alleles.Species.TROPICAL)
                .setResult(Alleles.Species.EDENIC).setChance(0.08f)
                .requireBlock(Blocks.LAPIS_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Spectral branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.HERMITIC, Alleles.Species.ENDED)
                .setResult(Alleles.Species.SPECTRAL).setChance(0.04f)
                .requireBlock(Blocks.END_STONE)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.SPECTRAL, Alleles.Species.ENDED)
                .setResult(Alleles.Species.PHANTASMAL).setChance(0.02f)
                .requireBlock(Blocks.PURPUR_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Glacial branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.INDUSTRIOUS, Alleles.Species.WINTRY)
                .setResult(Alleles.Species.ICY).setChance(0.12f)
                .restrictTemperatureRange(EnumTemperature.ICY, EnumTemperature.COLD)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.ICY, Alleles.Species.WINTRY)
                .setResult(Alleles.Species.GLACIAL).setChance(0.08f)
                .restrictTemperature(EnumTemperature.ICY)
                .buildAndAccept(writer);
        // endregion

        // region Vengeful branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MONASTIC, Alleles.Species.DEMONIC)
                .setResult(Alleles.Species.VINDICTIVE).setChance(0.04f)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.VINDICTIVE, Alleles.Species.DEMONIC)
                .setResult(Alleles.Species.VENGEFUL).setChance(0.08f)
                .requireBlock(Blocks.CRYING_OBSIDIAN)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.VINDICTIVE, Alleles.Species.MONASTIC)
                .setResult(Alleles.Species.VENGEFUL).setChance(0.08f)
                .requireBlock(Blocks.CRYING_OBSIDIAN)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.VINDICTIVE, Alleles.Species.VENGEFUL)
                .setResult(Alleles.Species.AVENGING).setChance(0.02f)
                .restrictTemperature(EnumTemperature.HELLISH)
                .restrictHumidity(EnumHumidity.ARID)
                .requireBlock(Blocks.SCULK)
                .buildAndAccept(writer);
        // endregion

        // region Agrarian branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.DILIGENT, Alleles.Species.MEADOWS)
                .setResult(Alleles.Species.RURAL).setChance(0.12f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.RURAL, Alleles.Species.UNWEARY)
                .setResult(Alleles.Species.FARMER).setChance(0.1f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.FARMER, Alleles.Species.INDUSTRIOUS)
                .setResult(Alleles.Species.AGRARIAN).setChance(0.06f)
                .restrictBiome(Biomes.PLAINS.location())
                .requireBlock(Blocks.HAY_BLOCK)
                .buildAndAccept(writer);
        // endregion

        // region Boggy branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MARSHY, Alleles.Species.NOBLE)
                .setResult(Alleles.Species.MIRY).setChance(0.15f)
                .restrictTemperature(EnumTemperature.WARM)
                .restrictHumidity(EnumHumidity.DAMP)
                .requireBlock(Blocks.MUD)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MIRY, Alleles.Species.MARSHY)
                .setResult(Alleles.Species.BOGGY).setChance(0.08f)
                .restrictTemperature(EnumTemperature.WARM)
                .restrictHumidity(EnumHumidity.DAMP)
                .requireBlock(Blocks.MUD)
                .buildAndAccept(writer);
        // endregion

        // region Monastic branch
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.MONASTIC, Alleles.Species.AUSTERE)
                .setResult(Alleles.Species.SECLUDED).setChance(0.1f)
                .requireBlockTag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .buildAndAccept(writer);
        MutationRecipeBuilder.get()
                .setParents(Alleles.Species.SECLUDED, Alleles.Species.MONASTIC)
                .setResult(Alleles.Species.HERMITIC).setChance(0.04f)
                .requireBlockTag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .buildAndAccept(writer);
        // endregion
    }
}
