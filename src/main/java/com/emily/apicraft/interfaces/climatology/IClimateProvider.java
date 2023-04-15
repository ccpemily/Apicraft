package com.emily.apicraft.interfaces.climatology;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;

public interface IClimateProvider {
    int getExactTemperature();
    int getExactHumidity();
    default EnumTemperature getTemperature(){
        int rawTemp = getExactTemperature();
        if(rawTemp > 180){
            return EnumTemperature.HELLISH;
        } else if (rawTemp > 100) {
            return EnumTemperature.HOT;
        } else if (rawTemp > 85) {
            return EnumTemperature.WARM;
        } else if (rawTemp > 35) {
            return EnumTemperature.NORMAL;
        } else if (rawTemp > 0) {
            return EnumTemperature.COLD;
        } else {
            return EnumTemperature.ICY;
        }
    }
    default EnumHumidity getHumidity(){
        int rawHumidity = getExactHumidity();
        if(rawHumidity > 85){
            return EnumHumidity.DAMP;
        } else if(rawHumidity >= 30){
            return EnumHumidity.NORMAL;
        } else{
            return EnumHumidity.ARID;
        }
    }
}
