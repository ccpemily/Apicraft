package com.emily.apicraft.interfaces.capabilities;

import com.emily.apicraft.genetics.Bee;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Optional;

@AutoRegisterCapability
public interface IBeeProvider {
    Optional<Bee> getBeeIndividual();
    void setBeeIndividual(Bee bee);
}
