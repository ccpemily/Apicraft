package com.emily.apicraft.climatology;

import com.emily.apicraft.client.gui.icons.Icons;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public enum EnumTemperature {
    NONE{
        @Override
        public ResourceLocation getIcon() {
            return Icons.OCEAN;
        }
    }, ICY{
        @Override
        public ResourceLocation getIcon() {
            return Icons.SNOW;
        }
    }, COLD{
        @Override
        public ResourceLocation getIcon() {
            return Icons.TAIGA;
        }
    }, NORMAL{
        @Override
        public ResourceLocation getIcon() {
            return Icons.PLAINS;
        }
    }, WARM{
        @Override
        public ResourceLocation getIcon() {
            return Icons.JUNGLE;
        }
    }, HOT{
        @Override
        public ResourceLocation getIcon() {
            return Icons.DESERT;
        }
    }, HELLISH{
        @Override
        public ResourceLocation getIcon() {
            return Icons.NETHER;
        }
    };

    public String getName(){
        return "climatology.temperature." + this.name().toLowerCase(Locale.ENGLISH);
    }
    public abstract ResourceLocation getIcon();
}
