package com.emily.apicraft.registry;

import cofh.lib.util.DeferredRegisterCoFH;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.block.Apiary;
import com.emily.apicraft.block.BeeHouse;
import com.emily.apicraft.block.entity.ApiaryEntity;
import com.emily.apicraft.block.entity.BeeHouseEntity;
import com.emily.apicraft.genetics.BeeKaryotype;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import com.emily.apicraft.inventory.menu.PortableAnalyzerMenu;
import com.emily.apicraft.inventory.menu.tile.ApiaryMenu;
import com.emily.apicraft.inventory.menu.tile.BeeHouseMenu;
import com.emily.apicraft.items.*;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import com.emily.apicraft.items.subtype.BeeCombTypes;
import com.emily.apicraft.items.subtype.BeeTypes;
import com.emily.apicraft.items.subtype.FrameTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static final DeferredRegisterCoFH<MenuType<?>> MENUS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, Apicraft.MODID);
    public static final DeferredRegisterCoFH<RecipeType<?>> RECIPE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_TYPES, Apicraft.MODID);
    public static final DeferredRegisterCoFH<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, Apicraft.MODID);

    // Custom Registries
    public static final DeferredRegisterCoFH<IAllele<?>> ALLELES = DeferredRegisterCoFH.create(new ResourceLocation(Apicraft.MODID, "alleles"), Apicraft.MODID);

    public static void initRegistry(IEventBus modEventBus){
        ALLELES.makeRegistry(RegistryBuilder::new);
        // Attach DeferredRegister to ModEventBus
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        ALLELES.register(modEventBus);
    }
    public static void register(){
        logger.debug("Apiculture Registry: starting register:");
        registerItems();
        registerBlocks();
        registerBlockEntities();
        registerMenus();
        registerAlleles();
        logger.debug("Apiculture Registry: registration completed.");
    }

    private static void registerItems(){
        for(BeeTypes type : BeeTypes.values()){
            registerItem("bee_" + type.name().toLowerCase(Locale.ENGLISH), () -> new BeeItem(type));
        }
        for(BeeCombTypes type : BeeCombTypes.values()){
            registerItem(type.getName(), () -> new BeeCombItem(type));
        }
        for(FrameTypes type : FrameTypes.values()){
            registerItem(type.getName(), () -> new FrameItem(type));
        }
        registerItem("portable_analyzer", PortableAnalyzer::new);
    }
    private static void registerBlocks(){
        registerBlock("bee_house", BeeHouse::new);
        registerBlock("apiary", Apiary::new);
    }
    private static void registerBlockEntities(){
        registerBlockEntity("bee_house", () -> BlockEntityType.Builder.of(BeeHouseEntity::new, BLOCKS.get("bee_house")).build(null));
        registerBlockEntity("apiary", () -> BlockEntityType.Builder.of(ApiaryEntity::new, BLOCKS.get("apiary")).build(null));
    }
    private static void registerMenus(){
        registerMenu("portable_analyzer", () -> IForgeMenuType.create(((windowId, inv, data) -> new PortableAnalyzerMenu(windowId, inv, getClientPlayer()))));

        registerMenu("bee_house", () -> IForgeMenuType.create((((windowId, inv, data) -> new BeeHouseMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())))));
        registerMenu("apiary", () -> IForgeMenuType.create((((windowId, inv, data) -> new ApiaryMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())))));
    }
    private static void registerAlleles(){
        registerAllele(Alleles.Species.class, AlleleTypes.SPECIES, Alleles.Species.FOREST);
        registerAllele(Alleles.LifeSpan.class, AlleleTypes.LIFESPAN, Alleles.LifeSpan.SHORT);
        registerAllele(Alleles.Productivity.class, AlleleTypes.PRODUCTIVITY, Alleles.Productivity.SLOWEST);
        registerAllele(Alleles.Fertility.class, AlleleTypes.FERTILITY, Alleles.Fertility.FERTILE);
        registerAllele(Alleles.Behavior.class, AlleleTypes.BEHAVIOR, Alleles.Behavior.DIURNAL);
        registerAllele(Alleles.RainTolerance.class, AlleleTypes.RAIN_TOLERANCE, Alleles.RainTolerance.FALSE);
        registerAllele(Alleles.CaveDwelling.class, AlleleTypes.CAVE_DWELLING, Alleles.CaveDwelling.FALSE);
        registerAllele(Alleles.AcceptedFlowers.class, AlleleTypes.ACCEPTED_FLOWERS, Alleles.AcceptedFlowers.VANILLA);
        registerAllele(Alleles.TemperatureTolerance.class, AlleleTypes.TEMPERATURE_TOLERANCE, Alleles.TemperatureTolerance.NONE);
        registerAllele(Alleles.HumidityTolerance.class, AlleleTypes.HUMIDITY_TOLERANCE, Alleles.HumidityTolerance.NONE);
        registerAllele(Alleles.Territory.class, AlleleTypes.TERRITORY, Alleles.Territory.AVERAGE);
        registerAllele(Alleles.Effect.class, AlleleTypes.EFFECT, Alleles.Effect.NONE);
    }


    // region Single Register Method
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
    private static void registerBlockEntity(String name, Supplier<BlockEntityType<?>> supplier){
        logger.debug("Registering block entity type: " + name);
        TILE_ENTITIES.register(name, supplier);
    }

    private static void registerMenu(String name, Supplier<MenuType<?>> supplier){
        logger.debug("Registering menu type: " + name);
        MENUS.register(name, supplier);
    }

    private static void registerAllele(Class<? extends IAllele<?>> alleleClass, IAlleleType type, IAllele<?> defaultValue){
        logger.debug("Registering allele type: " + type.getName());
        IAllele<?>[] types = alleleClass.getEnumConstants();
        BeeKaryotype.INSTANCE.registerToKaryotype(type, defaultValue);
        for(IAllele<?> t : types){
            ALLELES.register(t.toString(), () -> t);
        }
    }
    // endregion
}
