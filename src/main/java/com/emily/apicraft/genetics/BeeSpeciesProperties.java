package com.emily.apicraft.genetics;

import java.awt.*;

public class BeeSpeciesProperties {
    public int firstColor = 0x000000;
    public int secondColor = 0xffffff;
    public boolean isFoil = false;


    private BeeSpeciesProperties(){

    }

    public BeeSpeciesProperties setColor(Color first, Color second){
        this.firstColor = first.getRGB();
        this.secondColor = second.getRGB();
        return this;
    }

    public BeeSpeciesProperties setFoil(){
        this.isFoil = true;
        return this;
    }

    public static BeeSpeciesProperties get(){
        return new BeeSpeciesProperties();
    }
}
