package com.emily.apicraft.capabilities.empty;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.capabilities.IBeeProvider;
import com.emily.apicraft.genetics.IAllele;
import com.emily.apicraft.genetics.IAlleleType;

import javax.annotation.Nullable;
import java.util.Optional;

public class EmptyBeeProvider implements IBeeProvider {
    private static final EmptyBeeProvider instance = new EmptyBeeProvider();

    public static EmptyBeeProvider getInstance(){
        return instance;
    }

    @Override
    public Optional<Bee> getBeeIndividual() {
        return Optional.empty();
    }

    @Override
    public void setBeeIndividual(Bee bee) {

    }

    @Override
    public Alleles.Species getBeeSpeciesDirectly(boolean active) {
        return Alleles.Species.FOREST;
    }

    @Override
    @Nullable
    public IAllele<?> getBeeChromosomeDirectly(IAlleleType type, boolean active) {
        return null;
    }
}
