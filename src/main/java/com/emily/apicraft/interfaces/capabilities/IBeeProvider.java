package com.emily.apicraft.interfaces.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.interfaces.genetics.IAlleleType;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import javax.annotation.Nullable;
import java.util.Optional;

@AutoRegisterCapability
public interface IBeeProvider {
    Optional<Bee> getBeeIndividual();
    void setBeeIndividual(Bee bee);
    Alleles.Species getBeeSpeciesDirectly(boolean active);
    @Nullable
    IAllele<?> getBeeChromosomeDirectly(IAlleleType type, boolean active);
}
