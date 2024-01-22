package com.emily.apicraft.core.lib;

import com.emily.apicraft.client.gui.icons.Icons;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum ErrorStates {
    NONE(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }

        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_ILLEGAL;
        }
    },
    TOO_DARK(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }

        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_DARK;
        }
    },
    TOO_BRIGHT(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_BRIGHT;
        }
    },
    TOO_HOT(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_HOT;
        }
    },
    TOO_COLD(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_COLD;
        }
    },
    TOO_DRY(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_DRY;
        }
    },
    TOO_DAMP(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_TOO_DAMP;
        }
    },
    IS_RAINING(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_IS_RAINING;
        }
    },
    CANT_SEE_SKY(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_NO_SKY;
        }
    },
    INVENTORY_FULL(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_NO_SPACE;
        }
    },
    NO_QUEEN(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_NO_QUEEN;
        }
    },
    NO_DRONE(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_NO_DRONE;
        }
    },
    NO_FLOWER(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_NO_FLOWER;
        }
    },
    ILLEGAL_STATE(){
        @Override
        public List<Component> getTooltips() {
            return Collections.emptyList();
        }
        @Override
        public TextureAtlasSprite getIcon() {
            return Icons.ERROR_ILLEGAL;
        }
    };
    public String getName(){
        return "errors." + this.name().toLowerCase(Locale.ENGLISH);
    }
    public abstract List<Component> getTooltips();
    public abstract TextureAtlasSprite getIcon();
}
