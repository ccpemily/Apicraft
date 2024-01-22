package com.emily.apicraft.client.gui.icons;

import com.emily.apicraft.Apicraft;
import net.minecraft.resources.ResourceLocation;

public class Icons {
    private Icons() {
    }

    private static final String ICONS_PATH = Apicraft.MOD_ID + ":gui/icons/";
    private static final String HABITATS_PATH = Apicraft.MOD_ID + ":gui/habitats/";
    private static final String ERRORS_PATH = Apicraft.MOD_ID + ":gui/errors/";
    public static ResourceLocation OCEAN = new ResourceLocation(HABITATS_PATH + "ocean");
    public static ResourceLocation PLAINS = new ResourceLocation(HABITATS_PATH + "plains");
    public static ResourceLocation SNOW = new ResourceLocation(HABITATS_PATH + "snow");
    public static ResourceLocation TAIGA = new ResourceLocation(HABITATS_PATH + "taiga");
    public static ResourceLocation SWAMP = new ResourceLocation(HABITATS_PATH + "swamp");
    public static ResourceLocation JUNGLE = new ResourceLocation(HABITATS_PATH + "jungle");
    public static ResourceLocation FOREST = new ResourceLocation(HABITATS_PATH + "forest");
    public static ResourceLocation DESERT = new ResourceLocation(HABITATS_PATH + "desert");
    public static ResourceLocation NETHER = new ResourceLocation(HABITATS_PATH + "nether");
    public static ResourceLocation HILLS = new ResourceLocation(HABITATS_PATH + "hills");
    public static ResourceLocation MUSHROOM = new ResourceLocation(HABITATS_PATH + "mushroom");
    public static ResourceLocation END = new ResourceLocation(HABITATS_PATH + "end");

    public static ResourceLocation ERROR_ILLEGAL = new ResourceLocation(ERRORS_PATH + "illegal");
    public static ResourceLocation ERROR_IS_RAINING = new ResourceLocation(ERRORS_PATH + "is_raining");
    public static ResourceLocation ERROR_NO_DRONE = new ResourceLocation(ERRORS_PATH + "no_drone");
    public static ResourceLocation ERROR_NO_FLOWER = new ResourceLocation(ERRORS_PATH + "no_flower");
    public static ResourceLocation ERROR_NO_QUEEN = new ResourceLocation(ERRORS_PATH + "no_queen");
    public static ResourceLocation ERROR_NO_SKY = new ResourceLocation(ERRORS_PATH + "no_sky");
    public static ResourceLocation ERROR_NO_SPACE = new ResourceLocation(ERRORS_PATH + "no_space");
    public static ResourceLocation ERROR_TOO_BRIGHT = new ResourceLocation(ERRORS_PATH + "too_bright");
    public static ResourceLocation ERROR_TOO_COLD = new ResourceLocation(ERRORS_PATH + "too_cold");
    public static ResourceLocation ERROR_TOO_DAMP = new ResourceLocation(ERRORS_PATH + "too_damp");
    public static ResourceLocation ERROR_TOO_DARK = new ResourceLocation(ERRORS_PATH + "too_dark");
    public static ResourceLocation ERROR_TOO_DRY = new ResourceLocation(ERRORS_PATH + "too_dry");
    public static ResourceLocation ERROR_TOO_HOT = new ResourceLocation(ERRORS_PATH + "too_hot");


    private static final String BLOCK_ATLAS = "minecraft:textures/atlas/blocks.png";
}

