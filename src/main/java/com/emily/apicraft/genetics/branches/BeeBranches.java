package com.emily.apicraft.genetics.branches;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.IAlleleType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Locale;

public enum BeeBranches implements IBeeBranch{
    HONEY,
    NOBLE,
    INDUSTRIOUS,
    HEROIC,
    INFERNAL(BeeBranchProperties.get()
            .addToTemplate(Alleles.TemperatureTolerance.DOWN_2)
            .addToTemplate(Alleles.Behavior.NOCTURNAL)
            .addToTemplate(Alleles.AcceptedFlowers.NETHER)),
    AUSTERE(BeeBranchProperties.get()
            .addToTemplate(Alleles.Productivity.SLOW)
            .addToTemplate(Alleles.LifeSpan.NORMAL)
            .addToTemplate(Alleles.Behavior.NOCTURNAL)
            .addToTemplate(Alleles.TemperatureTolerance.BOTH_1)
            .addToTemplate(Alleles.HumidityTolerance.DOWN_1)
            .addToTemplate(Alleles.AcceptedFlowers.CACTI)),
    TROPICAL(BeeBranchProperties.get()
            .addToTemplate(Alleles.TemperatureTolerance.UP_1)
            .addToTemplate(Alleles.HumidityTolerance.UP_1)
            .addToTemplate(Alleles.AcceptedFlowers.JUNGLE)),
    END(BeeBranchProperties.get()
            .addToTemplate(Alleles.Fertility.UNLUCKY)
            .addToTemplate(Alleles.Productivity.SLOWEST)
            .addToTemplate(Alleles.LifeSpan.ANCIENT)
            .addToTemplate(Alleles.Behavior.NOCTURNAL)
            .addToTemplate(Alleles.TemperatureTolerance.UP_1)
            .addToTemplate(Alleles.AcceptedFlowers.END)
            .addToTemplate(Alleles.Territory.LARGE)),
    FROZEN(BeeBranchProperties.get()
            .addToTemplate(Alleles.LifeSpan.NORMAL)
            .addToTemplate(Alleles.Fertility.PROLIFIC)
            .addToTemplate(Alleles.TemperatureTolerance.UP_1)
            .addToTemplate(Alleles.HumidityTolerance.BOTH_1)
            .addToTemplate(Alleles.AcceptedFlowers.SNOW)),
    VENGEFUL(BeeBranchProperties.get()
            .addToTemplate(Alleles.Territory.LARGEST)),
    AGRARIAN(BeeBranchProperties.get()
            .addToTemplate(Alleles.Productivity.NORMAL)
            .addToTemplate(Alleles.LifeSpan.NORMAL)
            .addToTemplate(Alleles.AcceptedFlowers.WHEAT)
            .addToTemplate(Alleles.Territory.LARGE)),
    BOGGY(BeeBranchProperties.get()
            .addToTemplate(Alleles.AcceptedFlowers.MUSHROOMS)
            .addToTemplate(Alleles.TemperatureTolerance.BOTH_1)),
    MONASTIC(BeeBranchProperties.get()
            .addToTemplate(Alleles.Productivity.SLOWEST)
            .addToTemplate(Alleles.LifeSpan.ANCIENT)
            .addToTemplate(Alleles.Fertility.INFERTILE)
            .addToTemplate(Alleles.TemperatureTolerance.BOTH_1)
            .addToTemplate(Alleles.HumidityTolerance.BOTH_1)
            .addToTemplate(Alleles.Behavior.CREPUSCULAR)
            .addToTemplate(Alleles.CaveDwelling.TRUE)
            .addToTemplate(Alleles.AcceptedFlowers.WHEAT)),
    EMILY(BeeBranchProperties.get()
            .addToTemplate(Alleles.CaveDwelling.TRUE)
            .addToTemplate(Alleles.RainTolerance.TRUE)
            .addToTemplate(Alleles.Productivity.BRISK)),
    AKINA(BeeBranchProperties.get()
            .addToTemplate(Alleles.TemperatureTolerance.BOTH_3)
            .addToTemplate(Alleles.HumidityTolerance.BOTH_2));

    private final HashMap<IAlleleType, IAllele<?>> template;

    BeeBranches(){
        this.template = new HashMap<>();
    }
    BeeBranches(BeeBranchProperties properties){
        this.template = properties.template;
    }

    public HashMap<IAlleleType, IAllele<?>> getTemplate(){
        return template;
    }

    public ResourceLocation location(){
        return new ResourceLocation(Apicraft.MOD_ID, this.name().toLowerCase(Locale.ENGLISH));
    }
    public String getLocalizationKey(){
        return "branch." + this.name().toLowerCase(Locale.ENGLISH);
    }

    private static class BeeBranchProperties {
        private final HashMap<IAlleleType, IAllele<?>> template = new HashMap<>();

        private BeeBranchProperties(){}

        public BeeBranchProperties addToTemplate(IAllele<?> allele){
            template.put(allele.getType(), allele);
            return this;
        }

        public static BeeBranchProperties get(){
            return new BeeBranchProperties();
        }
    }
}
