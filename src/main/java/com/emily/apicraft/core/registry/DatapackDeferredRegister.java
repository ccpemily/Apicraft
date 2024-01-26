package com.emily.apicraft.core.registry;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.alleles.Species;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.branches.BeeBranches;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.mojang.logging.LogUtils.getLogger;

public class DatapackDeferredRegister<T>{
    private ResourceKey<Registry<T>> resourceKey;
    private Map<ResourceLocation, Supplier<T>> regMap = new HashMap<>();

    private DatapackDeferredRegister(){

    }

    public void onDatapackNewRegistry(final DataPackRegistryEvent.NewRegistry event){
        Logger logger = getLogger();
        logger.debug("Message from DatapackNewRegistry: DatapackRegistryEvent.NewRegistry");
        //event.dataPackRegistry(resourceKey, null, null);
    }


    public void onDatapackRegister(final DataPackRegistryEvent event){
        Logger logger = getLogger();
        logger.debug("Message from DatapackNewRegistry: DatapackRegistryEvent");
        //Registries.ALLELES.register("my_allele", () -> new Species("my_allele", SpeciesData.getBuilder().build(BeeBranches.END)));
    }
}
