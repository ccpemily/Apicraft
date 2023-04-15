package com.emily.apicraft.items;

import java.util.Locale;

public enum BeeTypes {
    DRONE, QUEEN, LARVA;

    public String getName(){
        return this.name().toLowerCase(Locale.ENGLISH);
    }
}
