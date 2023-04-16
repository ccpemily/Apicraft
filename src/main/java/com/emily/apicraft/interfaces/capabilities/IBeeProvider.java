package com.emily.apicraft.interfaces.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.genetics.IChromosomeType;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import javax.annotation.Nullable;
import java.util.Optional;

@AutoRegisterCapability
public interface IBeeProvider {
    Optional<Bee> getBeeIndividual();
    void setBeeIndividual(Bee bee);
    Chromosomes.Species getBeeSpeciesDirectly(boolean active);
    @Nullable
    <T extends IChromosomeType> T getBeeChromosomeDirectly(Class<T> type, boolean active);
}
