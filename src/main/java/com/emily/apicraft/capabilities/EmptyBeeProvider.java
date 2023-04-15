package com.emily.apicraft.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;

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
    public Chromosomes.Species getBeeSpeciesDirectly() {
        return Chromosomes.Species.FOREST;
    }
}
