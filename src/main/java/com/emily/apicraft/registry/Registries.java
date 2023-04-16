package com.emily.apicraft.registry;

import cofh.lib.util.DeferredRegisterCoFH;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.BeeGenome;
import com.emily.apicraft.genetics.BeeKaryotype;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import com.emily.apicraft.items.BeeItem;
import com.emily.apicraft.items.BeeTypes;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

import static com.mojang.logging.LogUtils.getLogger;

public class Registries {
    private static final Logger logger = getLogger();
    // Forge Registries
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, Apicraft.MODID);
    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, Apicraft.MODID);
    public static final DeferredRegisterCoFH<BlockEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Apicraft.MODID);
    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, Apicraft.MODID);

    // Custom Registries
    public static final DeferredRegisterCoFH<IChromosomeType> CHROMOSOMES = DeferredRegisterCoFH.create(new ResourceLocation(Apicraft.MODID, "chromosomes"), Apicraft.MODID);

    public static void initRegistry(IEventBus modEventBus){
        CHROMOSOMES.makeRegistry(RegistryBuilder::new);
        // 向事件总线注册DeferredRegister
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        CHROMOSOMES.register(modEventBus);
    }
    public static void register(){
        logger.debug("Apiculture Registry: starting register:");
        registerItems();
        registerBlocks();
        registerTileEntities();
        registerContainers();
        registerChromosomeTypes();
        logger.debug("Apiculture Registry: registration completed.");
    }

    private static void registerItems(){
        for(BeeTypes type : BeeTypes.values()){
            registerItem("bee_" + type.name().toLowerCase(Locale.ENGLISH), () -> new BeeItem(type));
        }
    }
    private static void registerBlocks(){
    }
    private static void registerTileEntities(){
    }
    private static void registerContainers(){
    }
    private static void registerChromosomeTypes(){
        registerChromosomeType(Chromosomes.Species.class, Chromosomes.Species.FOREST);
        registerChromosomeType(Chromosomes.LifeSpan.class, Chromosomes.LifeSpan.SHORT);
        registerChromosomeType(Chromosomes.Productivity.class, Chromosomes.Productivity.SLOWEST);
        registerChromosomeType(Chromosomes.Fertility.class, Chromosomes.Fertility.FERTILE);
        registerChromosomeType(Chromosomes.Behavior.class, Chromosomes.Behavior.DIURNAL);
        registerChromosomeType(Chromosomes.RainTolerance.class, Chromosomes.RainTolerance.FALSE);
        registerChromosomeType(Chromosomes.CaveDwelling.class, Chromosomes.CaveDwelling.FALSE);
        registerChromosomeType(Chromosomes.AcceptedFlowers.class, Chromosomes.AcceptedFlowers.VANILLA);
    }

    private static void registerItem(String name, Supplier<Item> supplier){
        logger.debug("Registering item: " + name);
        ITEMS.register(name, supplier);
    }
    private static void registerBlock(String name, Supplier<Block> supplier){
        logger.debug("Registering block: " + name);
        RegistryObject<? extends Block> block = BLOCKS.register(name, supplier);
        logger.debug("Registering blockitem: " + name);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeTabs.TAB_BLOCKS)));
    }
    private static void registerTileEntity(String name, Supplier<BlockEntityType<?>> supplier){
        logger.debug("Registering tile entity type: " + name);
        TILE_ENTITIES.register(name, supplier);
    }

    private static void registerChromosomeType(Class<? extends IChromosomeType> type, IChromosomeType defaultValue){
        logger.debug("Registering chromosome type: " + type);
        IChromosomeType[] types = type.getEnumConstants();
        BeeKaryotype.INSTANCE.registerToKaryotype(type, defaultValue);
        for(IChromosomeType t : types){
            CHROMOSOMES.register(t.toString(), () -> t);
        }
    }
}
