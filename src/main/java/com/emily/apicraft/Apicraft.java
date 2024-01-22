package com.emily.apicraft;

import com.emily.apicraft.registry.Registries;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Apicraft.MOD_ID)
public class Apicraft {
    public static final String MOD_ID = "apicraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Apicraft()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Initialize registries
        Registries.initRegistry(modEventBus);
        // Register all entries
        Registries.register();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
