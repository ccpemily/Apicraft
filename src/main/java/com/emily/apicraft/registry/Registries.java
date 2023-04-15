package com.emily.apicraft.registry;

import cofh.lib.util.DeferredRegisterCoFH;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.items.creativetab.ApicultureCreativeTab;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.function.Supplier;

import static cofh.core.util.ProxyUtils.getClientPlayer;
import static cofh.core.util.ProxyUtils.getClientWorld;
import static com.mojang.logging.LogUtils.getLogger;

public class Registries {
    private static final Logger logger = getLogger();
    // Forge Registries
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, Apicraft.MODID);
    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, Apicraft.MODID);
    public static final DeferredRegisterCoFH<BlockEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Apicraft.MODID);
    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, Apicraft.MODID);

    public static void initRegistry(IEventBus modEventBus){
        // 向事件总线注册DeferredRegister
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
    }
    public static void register(){
        logger.debug("Apiculture Registry: starting register:");
        registerItems();
        registerBlocks();
        registerTileEntities();
        registerContainers();
        logger.debug("Apiculture Registry: registration completed.");
    }

    public static void clientSetup(final FMLClientSetupEvent event){
        event.enqueueWork(Registries::registerScreens);
    }
    private static void registerItems(){
    }
    private static void registerBlocks(){
    }
    private static void registerTileEntities(){
    }
    private static void registerContainers(){
    }

    @SuppressWarnings("unchecked")
    private static void registerScreens(){

    }

    private static void registerItem(String name, Supplier<Item> supplier){
        logger.debug("Registering item: " + name);
        ITEMS.register(name, supplier);
    }
    private static void registerBlock(String name, Supplier<Block> supplier){
        logger.debug("Registering block: " + name);
        RegistryObject<? extends Block> block = BLOCKS.register(name, supplier);
        logger.debug("Registering blockitem: " + name);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ApicultureCreativeTab.TAB_BLOCKS)));
    }
    private static void registerTileEntity(String name, Supplier<BlockEntityType<?>> supplier){
        logger.debug("Registering tile entity type: " + name);
        TILE_ENTITIES.register(name, supplier);
    }
}
