package com.emily.apicraft.block;

import com.emily.apicraft.block.entity.beehousing.ThermalApiaryEntity;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.material.Material;


public class ThermalApiary extends AbstractBeeHousingBlock {
    public ThermalApiary() {
        super(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops(), ThermalApiaryEntity.class, Registries.TILE_ENTITIES.getSup("thermal_apiary"));
    }
}
