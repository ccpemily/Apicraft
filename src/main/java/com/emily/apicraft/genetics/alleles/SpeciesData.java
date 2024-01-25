package com.emily.apicraft.genetics.alleles;

import com.emily.apicraft.climatology.EnumHumidity;
import com.emily.apicraft.climatology.EnumTemperature;
import com.emily.apicraft.genetics.branches.BeeBranches;
import com.emily.apicraft.genetics.branches.IBeeBranch;

import java.awt.*;
import java.util.HashMap;

public class SpeciesData {
    private final IBeeBranch branch;
    private final int firstColor;
    private final int secondColor;
    private final boolean isFoil;
    private final EnumTemperature temperature;
    private final EnumHumidity humidity;
    private final HashMap<IAlleleType, IAllele<?>> template;

    SpeciesData(IBeeBranch branch, BeeSpeciesProperties builder){
        this.firstColor = builder.firstColor;
        this.secondColor = builder.secondColor;
        this.branch = branch;
        this.isFoil = builder.isFoil;
        this.temperature = builder.temperature;
        this.humidity = builder.humidity;
        this.template = builder.template;
        for(IAlleleType key : branch.getTemplate().keySet()){
            if(!template.containsKey(key)){
                template.put(key, branch.getTemplate().get(key));
            }
        }
    }

    public int getColor(int tintIndex){
        return tintIndex == 0 ? firstColor : tintIndex == 1 ? secondColor : 0xffffff;
    }

    public boolean isFoil(){
        return isFoil;
    }

    public EnumTemperature getTemperature() {
        return temperature;
    }

    public EnumHumidity getHumidity() {
        return humidity;
    }

    public HashMap<IAlleleType, IAllele<?>> getTemplate() {
        return template;
    }

    public IBeeBranch getBranch(){
        return branch;
    }

    public static BeeSpeciesProperties getBuilder(){
        return new BeeSpeciesProperties();
    }

    public static class BeeSpeciesProperties {
        public int firstColor = 0x000000;
        public int secondColor = 0xffffff;
        public boolean isFoil = false;
        public EnumTemperature temperature = EnumTemperature.NORMAL;
        public EnumHumidity humidity = EnumHumidity.NORMAL;
        public HashMap<IAlleleType, IAllele<?>> template = new HashMap<>();

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

        public BeeSpeciesProperties setTemperature(EnumTemperature temperature){
            this.temperature = temperature;
            return this;
        }

        public BeeSpeciesProperties setHumidity(EnumHumidity humidity){
            this.humidity = humidity;
            return this;
        }

        public BeeSpeciesProperties addToTemplate(IAllele<?> allele){
            template.put(allele.getType(), allele);
            return this;
        }

        public SpeciesData build(BeeBranches branch){
            return new SpeciesData(branch, this);
        }

        public static BeeSpeciesProperties get(){
            return new BeeSpeciesProperties();
        }
    }
}
