package com.emily.apicraft.core.client.gui.icons;

import com.emily.apicraft.Apicraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.mojang.logging.LogUtils.getLogger;

// Class by CofhCore
@Mod.EventBusSubscriber (value = Dist.CLIENT, modid = Apicraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Icons {
    private Icons(){}
    private static final String ICONS_PATH = Apicraft.MODID + ":gui/icons/";
    private static final String HABITATS_PATH = Apicraft.MODID + ":gui/habitats/";

    public static TextureAtlasSprite OCEAN;
    public static TextureAtlasSprite PLAINS;
    public static TextureAtlasSprite SNOW;
    public static TextureAtlasSprite TAIGA;
    public static TextureAtlasSprite SWAMP;
    public static TextureAtlasSprite JUNGLE;
    public static TextureAtlasSprite FOREST;
    public static TextureAtlasSprite DESERT;
    public static TextureAtlasSprite NETHER;
    public static TextureAtlasSprite HILLS;
    public static TextureAtlasSprite MUSHROOM;
    public static TextureAtlasSprite END;


    private static final String BLOCK_ATLAS = "minecraft:textures/atlas/blocks.png";
    @SubscribeEvent
    public static void preStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().toString().equals(BLOCK_ATLAS)) {
            return;
        }
        event.addSprite(new ResourceLocation(HABITATS_PATH + "ocean"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "snow"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "taiga"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "plains"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "jungle"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "desert"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "nether"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "swamp"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "hills"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "forest"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "mushroom"));
        event.addSprite(new ResourceLocation(HABITATS_PATH + "end"));
    }

    @SubscribeEvent
    public static void postStitch(TextureStitchEvent.Post event) {

        if (!event.getAtlas().location().toString().equals(BLOCK_ATLAS)) {
            return;
        }
        TextureAtlas map = event.getAtlas();
        OCEAN = map.getSprite(new ResourceLocation(HABITATS_PATH + "ocean"));
        SNOW = map.getSprite(new ResourceLocation(HABITATS_PATH + "snow"));
        TAIGA = map.getSprite(new ResourceLocation(HABITATS_PATH + "taiga"));
        PLAINS = map.getSprite(new ResourceLocation(HABITATS_PATH + "plains"));
        JUNGLE = map.getSprite(new ResourceLocation(HABITATS_PATH + "jungle"));
        DESERT = map.getSprite(new ResourceLocation(HABITATS_PATH + "desert"));
        NETHER = map.getSprite(new ResourceLocation(HABITATS_PATH + "nether"));
        SWAMP = map.getSprite(new ResourceLocation(HABITATS_PATH + "swamp"));
        HILLS = map.getSprite(new ResourceLocation(HABITATS_PATH + "hills"));
        FOREST = map.getSprite(new ResourceLocation(HABITATS_PATH + "forest"));
        MUSHROOM = map.getSprite(new ResourceLocation(HABITATS_PATH + "mushroom"));
        END = map.getSprite(new ResourceLocation(HABITATS_PATH + "end"));
        Logger logger = getLogger();
        logger.debug("Got sprite: " + OCEAN.toString());
    }
}
