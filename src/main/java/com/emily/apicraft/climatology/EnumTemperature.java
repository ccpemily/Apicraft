package com.emily.apicraft.climatology;

import com.emily.apicraft.core.client.gui.icons.Icons;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Locale;

public enum EnumTemperature {
    NONE{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.OCEAN;
        }
    }, ICY{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.SNOW;
        }
    }, COLD{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.TAIGA;
        }
    }, NORMAL{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.PLAINS;
        }
    }, WARM{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.JUNGLE;
        }
    }, HOT{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.DESERT;
        }
    }, HELLISH{
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.NETHER;
        }
    };

    public String getName(){
        return "climatology.temperature." + this.name().toLowerCase(Locale.ENGLISH);
    }
    public abstract TextureAtlasSprite getIcon();
}
