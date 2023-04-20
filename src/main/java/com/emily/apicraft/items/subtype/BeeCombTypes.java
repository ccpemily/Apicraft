package com.emily.apicraft.items.subtype;

import java.awt.*;
import java.util.Locale;

public enum BeeCombTypes {
    HONEY(new Color(0xe8d56a), new Color(0xffa12b)),
    COCOA(new Color(0x674016), new Color(0xffb62b)),
    SIMMERING(new Color(0x981919), new Color(0xffb62b)),
    STRINGY(new Color(0xc8be67), new Color(0xbda93e)),
    FROZEN(new Color(0xf9ffff), new Color(0xa0ffff)),
    DRIPPING(new Color(0xdc7613), new Color(0xffff00)),
    SILKY(new Color(0x508907), new Color(0xddff00)),
    PARCHED(new Color(0xdcbe13), new Color(0xffff00)),
    MYSTERIOUS(new Color(0x161616), new Color(0xe099ff)),
    IRRADIATED(new Color(0xeafff3), new Color(0xeeff00)),
    POWDERY(new Color(0x676767), new Color(0xffffff)),
    REDDENED(new Color(0x4b0000), new Color(0x6200e7)),
    DARKENED(new Color(0x353535), new Color(0x33ebcb)),
    OMEGA(new Color(0x191919), new Color(0x6dcff6)),
    WHEATEN(new Color(0xfeff8f), new Color(0xffffff)),
    MOSSY(new Color(0x2a3313), new Color(0x7e9939)),
    MELLOW(new Color(0x886000), new Color(0xfff960)),
    PINKISH(new Color(0xd7bee5), new Color(0xfd58ab)),
    PURPLE(new Color(0xa976ff), new Color(0xdc77ff));
    private final int primaryColor;
    private final int secondaryColor;


    BeeCombTypes(Color primary, Color secondary){
        this.primaryColor = primary.getRGB();
        this.secondaryColor = secondary.getRGB();
    }

    public int getColor(int layer){
        return layer == 1 ? primaryColor : secondaryColor;
    }

    @Override
    public String toString(){
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    public String getName(){
        return "bee_comb_" + this;
    }
}
