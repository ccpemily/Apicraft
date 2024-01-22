package com.emily.apicraft.climatology;

import java.util.Locale;

public enum EnumHumidity {
    NONE, ARID, NORMAL, DAMP;
    public String getName(){
        return "climatology.humidity." + this.name().toLowerCase(Locale.ENGLISH);
    }
}
