package com.emily.apicraft.genetics;

import com.emily.apicraft.interfaces.genetics.IChromosomeType;

import java.util.HashMap;

public enum BeeBranches {
    HONEY,
    NOBLE,
    INDUSTRIOUS,
    HEROIC,
    INFERNAL(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.TemperatureTolerance.DOWN_2)
            .addToTemplate(Chromosomes.Behavior.NOCTURNAL)
            .addToTemplate(Chromosomes.AcceptedFlowers.NETHER)),
    AUSTERE(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.Productivity.SLOW)
            .addToTemplate(Chromosomes.LifeSpan.NORMAL)
            .addToTemplate(Chromosomes.Behavior.NOCTURNAL)
            .addToTemplate(Chromosomes.TemperatureTolerance.BOTH_1)
            .addToTemplate(Chromosomes.HumidityTolerance.DOWN_1)
            .addToTemplate(Chromosomes.AcceptedFlowers.CACTI)),
    TROPICAL(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.TemperatureTolerance.UP_1)
            .addToTemplate(Chromosomes.HumidityTolerance.UP_1)
            .addToTemplate(Chromosomes.AcceptedFlowers.JUNGLE)),
    END(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.Fertility.UNLUCKY)
            .addToTemplate(Chromosomes.Productivity.SLOWEST)
            .addToTemplate(Chromosomes.LifeSpan.ANCIENT)
            .addToTemplate(Chromosomes.Behavior.NOCTURNAL)
            .addToTemplate(Chromosomes.TemperatureTolerance.UP_1)
            .addToTemplate(Chromosomes.AcceptedFlowers.END)
            .addToTemplate(Chromosomes.Territory.LARGE)),
    FROZEN(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.LifeSpan.NORMAL)
            .addToTemplate(Chromosomes.Fertility.PROLIFIC)
            .addToTemplate(Chromosomes.TemperatureTolerance.UP_1)
            .addToTemplate(Chromosomes.HumidityTolerance.BOTH_1)
            .addToTemplate(Chromosomes.AcceptedFlowers.SNOW)),
    VENGEFUL(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.Territory.LARGEST)),
    AGRARIAN(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.Productivity.NORMAL)
            .addToTemplate(Chromosomes.LifeSpan.NORMAL)
            .addToTemplate(Chromosomes.AcceptedFlowers.WHEAT)
            .addToTemplate(Chromosomes.Territory.LARGE)),
    BOGGY(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.AcceptedFlowers.MUSHROOMS)
            .addToTemplate(Chromosomes.TemperatureTolerance.BOTH_1)),
    MONASTIC(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.Productivity.SLOWEST)
            .addToTemplate(Chromosomes.LifeSpan.ANCIENT)
            .addToTemplate(Chromosomes.Fertility.INFERTILE)
            .addToTemplate(Chromosomes.TemperatureTolerance.BOTH_1)
            .addToTemplate(Chromosomes.HumidityTolerance.BOTH_1)
            .addToTemplate(Chromosomes.Behavior.CREPUSCULAR)
            .addToTemplate(Chromosomes.CaveDwelling.TRUE)
            .addToTemplate(Chromosomes.AcceptedFlowers.WHEAT)),
    EMILY(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.CaveDwelling.TRUE)
            .addToTemplate(Chromosomes.RainTolerance.TRUE)
            .addToTemplate(Chromosomes.Productivity.BRISK)),
    AKINA(BeeBranchProperties.get()
            .addToTemplate(Chromosomes.TemperatureTolerance.BOTH_3)
            .addToTemplate(Chromosomes.HumidityTolerance.BOTH_2));

    private final HashMap<Class<? extends IChromosomeType>, IChromosomeType> template;

    BeeBranches(){
        this.template = new HashMap<>();
    }
    BeeBranches(BeeBranchProperties properties){
        this.template = properties.template;
    }

    public HashMap<Class<? extends IChromosomeType>, IChromosomeType> getTemplate(){
        return template;
    }



    private static class BeeBranchProperties {
        private final HashMap<Class<? extends IChromosomeType>, IChromosomeType> template = new HashMap<>();

        private BeeBranchProperties(){}

        public BeeBranchProperties addToTemplate(IChromosomeType chromosome){
            template.put(chromosome.getClass(), chromosome);
            return this;
        }

        public static BeeBranchProperties get(){
            return new BeeBranchProperties();
        }
    }
}
