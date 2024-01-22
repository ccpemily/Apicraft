package com.emily.apicraft.client.gui.icons;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.core.lib.ErrorStates;
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
    private static final String ERRORS_PATH = Apicraft.MODID + ":gui/errors/";
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

    public static TextureAtlasSprite ERROR_ILLEGAL;
    public static TextureAtlasSprite ERROR_IS_RAINING;
    public static TextureAtlasSprite ERROR_NO_DRONE;
    public static TextureAtlasSprite ERROR_NO_FLOWER;
    public static TextureAtlasSprite ERROR_NO_QUEEN;
    public static TextureAtlasSprite ERROR_NO_SKY;
    public static TextureAtlasSprite ERROR_NO_SPACE;
    public static TextureAtlasSprite ERROR_TOO_BRIGHT;
    public static TextureAtlasSprite ERROR_TOO_COLD;
    public static TextureAtlasSprite ERROR_TOO_DAMP;
    public static TextureAtlasSprite ERROR_TOO_DARK;
    public static TextureAtlasSprite ERROR_TOO_DRY;
    public static TextureAtlasSprite ERROR_TOO_HOT;


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

        event.addSprite(new ResourceLocation(ERRORS_PATH + "illegal"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "is_raining"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "no_drone"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "no_flower"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "no_queen"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "no_sky"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "no_space"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_bright"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_cold"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_damp"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_dark"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_dry"));
        event.addSprite(new ResourceLocation(ERRORS_PATH + "too_hot"));
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

        ERROR_ILLEGAL = map.getSprite(new ResourceLocation(ERRORS_PATH + "illegal"));
        ERROR_IS_RAINING = map.getSprite(new ResourceLocation(ERRORS_PATH + "is_raining"));
        ERROR_NO_DRONE = map.getSprite(new ResourceLocation(ERRORS_PATH + "no_drone"));
        ERROR_NO_FLOWER = map.getSprite(new ResourceLocation(ERRORS_PATH + "no_flower"));
        ERROR_NO_QUEEN = map.getSprite(new ResourceLocation(ERRORS_PATH + "no_queen"));
        ERROR_NO_SKY = map.getSprite(new ResourceLocation(ERRORS_PATH + "no_sky"));
        ERROR_NO_SPACE = map.getSprite(new ResourceLocation(ERRORS_PATH + "no_space"));
        ERROR_TOO_BRIGHT = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_bright"));
        ERROR_TOO_COLD = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_cold"));
        ERROR_TOO_DAMP = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_damp"));
        ERROR_TOO_DARK = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_dark"));
        ERROR_TOO_DRY = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_dry"));
        ERROR_TOO_HOT = map.getSprite(new ResourceLocation(ERRORS_PATH + "too_hot"));
    }
}
