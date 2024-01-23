package com.emily.apicraft.block.beehouse;

import com.emily.apicraft.block.entity.beehouse.BeeHouseEntity;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.material.Material;


public class BeeHouse extends AbstractBeeHousingBlock {

    public BeeHouse() {
        super(BlockBehaviour.Properties.of().strength(1.5f), BeeHouseEntity.class, Registries.TILE_ENTITIES.getSup("bee_house"));
    }
}
