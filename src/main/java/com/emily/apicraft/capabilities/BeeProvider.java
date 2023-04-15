package com.emily.apicraft.capabilities;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public class BeeProvider implements IBeeProvider {
    private final LazyOptional<IBeeProvider> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    public BeeProvider(ItemStack container){
        this.container = container;
    }

    @Override
    public Optional<Bee> getBeeIndividual() {
        return Optional.empty();
    }

    @Override
    public void setBeeIndividual(Bee bee) {

    }
}
