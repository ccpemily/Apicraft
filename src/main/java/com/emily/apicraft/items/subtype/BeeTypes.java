package com.emily.apicraft.items.subtype;

import java.util.Locale;

public enum BeeTypes {
    DRONE, QUEEN, PRINCESS, LARVA;

    public String getName(){
        return "bee.type." + this.name().toLowerCase(Locale.ENGLISH);
    }
}
