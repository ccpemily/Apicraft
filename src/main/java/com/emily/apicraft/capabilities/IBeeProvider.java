package com.emily.apicraft.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.IAlleleType;
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
