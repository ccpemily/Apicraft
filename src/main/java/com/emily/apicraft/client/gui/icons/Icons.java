package com.emily.apicraft.client.gui.icons;

import com.emily.apicraft.Apicraft;
import net.minecraft.resources.ResourceLocation;

public class Icons {
    private Icons() {
    }

    private static final String ICONS_PATH = Apicraft.MOD_ID + ":textures/gui/icons/";
    private static final String HABITATS_PATH = Apicraft.MOD_ID + ":textures/gui/habitats/";
    private static final String ERRORS_PATH = Apicraft.MOD_ID + ":textures/gui/errors/";
    public static ResourceLocation OCEAN = new ResourceLocation(HABITATS_PATH + "ocean.png");
    public static ResourceLocation PLAINS = new ResourceLocation(HABITATS_PATH + "plains.png");
    public static ResourceLocation SNOW = new ResourceLocation(HABITATS_PATH + "snow.png");
    public static ResourceLocation TAIGA = new ResourceLocation(HABITATS_PATH + "taiga.png");
    public static ResourceLocation SWAMP = new ResourceLocation(HABITATS_PATH + "swamp.png");
    public static ResourceLocation JUNGLE = new ResourceLocation(HABITATS_PATH + "jungle.png");
    public static ResourceLocation FOREST = new ResourceLocation(HABITATS_PATH + "forest.png");
    public static ResourceLocation DESERT = new ResourceLocation(HABITATS_PATH + "desert.png");
    public static ResourceLocation NETHER = new ResourceLocation(HABITATS_PATH + "nether.png");
    public static ResourceLocation HILLS = new ResourceLocation(HABITATS_PATH + "hills.png");
    public static ResourceLocation MUSHROOM = new ResourceLocation(HABITATS_PATH + "mushroom.png");
    public static ResourceLocation END = new ResourceLocation(HABITATS_PATH + "end.png");

    public static ResourceLocation ERROR_ILLEGAL = new ResourceLocation(ERRORS_PATH + "illegal.png");
    public static ResourceLocation ERROR_IS_RAINING = new ResourceLocation(ERRORS_PATH + "is_raining.png");
    public static ResourceLocation ERROR_NO_DRONE = new ResourceLocation(ERRORS_PATH + "no_drone.png");
    public static ResourceLocation ERROR_NO_FLOWER = new ResourceLocation(ERRORS_PATH + "no_flower.png");
    public static ResourceLocation ERROR_NO_QUEEN = new ResourceLocation(ERRORS_PATH + "no_queen.png");
    public static ResourceLocation ERROR_NO_SKY = new ResourceLocation(ERRORS_PATH + "no_sky.png");
    public static ResourceLocation ERROR_NO_SPACE = new ResourceLocation(ERRORS_PATH + "no_space.png");
    public static ResourceLocation ERROR_TOO_BRIGHT = new ResourceLocation(ERRORS_PATH + "too_bright.png");
    public static ResourceLocation ERROR_TOO_COLD = new ResourceLocation(ERRORS_PATH + "too_cold.png");
    public static ResourceLocation ERROR_TOO_DAMP = new ResourceLocation(ERRORS_PATH + "too_damp.png");
    public static ResourceLocation ERROR_TOO_DARK = new ResourceLocation(ERRORS_PATH + "too_dark.png");
    public static ResourceLocation ERROR_TOO_DRY = new ResourceLocation(ERRORS_PATH + "too_dry.png");
    public static ResourceLocation ERROR_TOO_HOT = new ResourceLocation(ERRORS_PATH + "too_hot.png");
}

