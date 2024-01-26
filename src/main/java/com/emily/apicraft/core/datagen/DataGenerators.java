package com.emily.apicraft.core.datagen;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.datagen.providers.localization.ZhCNLanguageProvider;
import com.emily.apicraft.core.datagen.providers.recipe.MutationRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Apicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        //generator.addProvider(event.includeServer(), new MutationRecipeProvider(generator.getPackOutput(), event.getExistingFileHelper()));
        //generator.addProvider(event.includeClient(), new ZhCNLanguageProvider(generator, "zh_cn"));
    }
}
