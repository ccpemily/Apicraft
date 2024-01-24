package com.emily.apicraft.registry;

import cofh.lib.util.DeferredRegisterCoFH;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.block.beehouse.Apiary;
import com.emily.apicraft.block.beehouse.BeeHouse;
import com.emily.apicraft.block.beehouse.ThermalApiary;
import com.emily.apicraft.block.entity.beehouse.ApiaryEntity;
import com.emily.apicraft.block.entity.beehouse.BeeHouseEntity;
import com.emily.apicraft.block.entity.beehouse.ThermalApiaryEntity;
import com.emily.apicraft.capabilities.empty.EmptyBeeProductContainer;
import com.emily.apicraft.capabilities.empty.EmptyBeeProvider;
import com.emily.apicraft.capabilities.implementation.BeeProductFrameCapability;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.client.particles.Particles;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeKaryotype;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.capabilities.IBeeProductContainer;
import com.emily.apicraft.capabilities.IBeeProvider;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.IAlleleType;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.genetics.conditions.IConditionType;
import com.emily.apicraft.inventory.menu.PortableAnalyzerMenu;
import com.emily.apicraft.inventory.menu.blockentity.ApiaryMenu;
import com.emily.apicraft.inventory.menu.blockentity.BeeHouseMenu;
import com.emily.apicraft.inventory.menu.blockentity.ThermalApiaryMenu;
import com.emily.apicraft.items.*;
import com.emily.apicraft.items.subtype.BeeCombTypes;
import com.emily.apicraft.items.subtype.BeeTypes;
import com.emily.apicraft.items.subtype.FrameTypes;
import com.emily.apicraft.recipes.RecipeManagers;
import com.emily.apicraft.recipes.RecipeSerializers;
import com.emily.apicraft.recipes.RecipeTypes;
import com.emily.apicraft.recipes.conditions.ConditionSerializers;
import com.emily.apicraft.recipes.conditions.Conditions;
import com.emily.apicraft.utils.ItemUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static cofh.core.util.ProxyUtils.getClientPlayer;
import static cofh.core.util.ProxyUtils.getClientWorld;
import static com.mojang.logging.LogUtils.getLogger;

public class Registries {
    private static final Logger logger = getLogger();
    // Minecraft Registries
    public static final DeferredRegisterCoFH<CreativeModeTab> CREATIVE_TABS = DeferredRegisterCoFH.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Apicraft.MOD_ID);
    private static final List<RegistryObject<Item>> TAB_BEES = new ArrayList<>();
    private static final List<RegistryObject<Item>> TAB_BLOCKS = new ArrayList<>();
    private static final List<RegistryObject<Item>> TAB_ITEMS = new ArrayList<>();
    // Forge Registries
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<BlockEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<MenuType<?>> MENUS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<RecipeType<?>> RECIPE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_TYPES, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<ParticleType<?>> PARTICLE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.PARTICLE_TYPES, Apicraft.MOD_ID);


    // Custom Registries
    public static final DeferredRegisterCoFH<IAllele<?>> ALLELES = DeferredRegisterCoFH.create(new ResourceLocation(Apicraft.MOD_ID, "alleles"), Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<IConditionType<?>> CONDITION_TYPES = DeferredRegisterCoFH.create(new ResourceLocation(Apicraft.MOD_ID, "conditions"), Apicraft.MOD_ID);
    public static final DeferredRegisterCoFH<IConditionSerializer<?>> CONDITION_SERIALIZERS = DeferredRegisterCoFH.create(new ResourceLocation(Apicraft.MOD_ID, "condition_serializers"), Apicraft.MOD_ID);

    static {
        // Call static classes to trigger classloading.
        Particles.register();
        RecipeTypes.register();
        RecipeSerializers.register();
        RecipeManagers.register();
        Conditions.register();
        ConditionSerializers.register();
    }

    public static void initRegistry(IEventBus modEventBus){
        ALLELES.makeRegistry(RegistryBuilder::new);
        CONDITION_TYPES.makeRegistry(RegistryBuilder::new);
        CONDITION_SERIALIZERS.makeRegistry(RegistryBuilder::new);
        // Attach DeferredRegister to ModEventBus
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        ALLELES.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        CONDITION_TYPES.register(modEventBus);
        CONDITION_SERIALIZERS.register(modEventBus);
    }
    public static void register(){
        logger.debug("Apicraft Registry: starting register:");
        registerItems();
        registerBlocks();
        registerCreativeModeTabs();
        registerBlockEntities();
        registerMenus();
        registerAlleles();
        logger.debug("Apicraft Registry: registration completed.");
    }

    private static void registerItems(){
        for(BeeTypes type : BeeTypes.values()){
            registerItem("bee_" + type.name().toLowerCase(Locale.ENGLISH), () -> new BeeItem(type), TAB_BEES);
        }
        for(BeeCombTypes type : BeeCombTypes.values()){
            registerItem(type.getName(), () -> new BeeCombItem(type), TAB_ITEMS);
        }
        for(FrameTypes type : FrameTypes.values()){
            registerItem(type.getName(), () -> new FrameItem(type), TAB_ITEMS);
            registerItem(type.getName() + "_broken", () -> new BrokenFrameItem(type), TAB_ITEMS);
        }
        registerItem("portable_analyzer", PortableAnalyzer::new, TAB_ITEMS);
    }
    private static void registerBlocks(){
        registerBlock("bee_house", BeeHouse::new, TAB_BLOCKS);
        registerBlock("apiary", Apiary::new, TAB_BLOCKS);
        registerBlock("thermal_apiary", ThermalApiary::new, TAB_BLOCKS);
    }

    public static void registerCreativeModeTabs(){
        CREATIVE_TABS.register(Apicraft.MOD_ID + ".bees",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.apicraft.bees"))
                        .icon(ItemUtils::getDefaultDroneStack)
                        .displayItems((pParameters, pOutput) -> TAB_BEES.forEach((item) -> {
                            ItemStack stack = new ItemStack(item.get());
                            IBeeProvider provider = BeeProviderCapability.get(stack);
                            if (provider instanceof EmptyBeeProvider) {
                                pOutput.accept(new ItemStack(item.get()));
                            } else {
                                for (Alleles.Species species : Alleles.Species.values()) {
                                    ItemStack bee = new ItemStack(item.get());
                                    IBeeProvider beeProvider = BeeProviderCapability.get(bee);
                                    beeProvider.setBeeIndividual(Bee.getPure(species));
                                    pOutput.accept(bee);
                                }
                            }
                        })
                        )
                        .build()
        );
        CREATIVE_TABS.register(Apicraft.MOD_ID + ".blocks",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.apicraft.blocks"))
                        .icon(() -> new ItemStack(ITEMS.get("apiary")))
                        .displayItems((pParameters, pOutput) -> TAB_BLOCKS.forEach((item) -> pOutput.accept(new ItemStack(item.get()))))
                        .build()
        );
        CREATIVE_TABS.register(Apicraft.MOD_ID + ".items",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.apicraft.items"))
                        .icon(() -> new ItemStack(ITEMS.get("bee_comb_honey")))
                        .displayItems((pParameters, pOutput) -> TAB_ITEMS.forEach((item) -> {
                            ItemStack stack = new ItemStack(item.get());
                            IBeeProductContainer container = BeeProductFrameCapability.get(stack);
                            if(container instanceof EmptyBeeProductContainer){
                                pOutput.accept(stack);
                            }
                            else {
                                FrameTypes type = ((FrameItem)(stack.getItem())).getType();
                                container.setProductData(new BeeProductData(type.maxUse));
                                stack.setDamageValue(type.maxUse);
                                pOutput.accept(stack);
                            }
                        }))
                        .build()
        );
    }
    private static void registerBlockEntities(){
        registerBlockEntity("bee_house", () -> BlockEntityType.Builder.of(BeeHouseEntity::new, BLOCKS.get("bee_house")).build(null));
        registerBlockEntity("apiary", () -> BlockEntityType.Builder.of(ApiaryEntity::new, BLOCKS.get("apiary")).build(null));
        registerBlockEntity("thermal_apiary", () -> BlockEntityType.Builder.of(ThermalApiaryEntity::new, BLOCKS.get("thermal_apiary")).build(null));
    }
    private static void registerMenus(){
        registerMenu("portable_analyzer", () -> IForgeMenuType.create(((windowId, inv, data) -> new PortableAnalyzerMenu(windowId, inv, getClientPlayer()))));

        registerMenu("bee_house", () -> IForgeMenuType.create((((windowId, inv, data) -> new BeeHouseMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())))));
        registerMenu("apiary", () -> IForgeMenuType.create((((windowId, inv, data) -> new ApiaryMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())))));
        registerMenu("thermal_apiary", () -> IForgeMenuType.create((((windowId, inv, data) -> new ThermalApiaryMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())))));
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
    private static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier){
        logger.debug("Registering item: " + name);
        return ITEMS.register(name, supplier);
    }
    private static void registerItem(String name, Supplier<Item> supplier, List<RegistryObject<Item>> creativeTab){
        creativeTab.add(registerItem(name, supplier));
    }

    private static RegistryObject<Item> registerBlock(String name, Supplier<Block> supplier){
        logger.debug("Registering block: " + name);
        RegistryObject<? extends Block> block = BLOCKS.register(name, supplier);
        logger.debug("Registering blockitem: " + name);
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static void registerBlock(String name, Supplier<Block> supplier, List<RegistryObject<Item>> creativeTab){
        creativeTab.add(registerBlock(name, supplier));
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
