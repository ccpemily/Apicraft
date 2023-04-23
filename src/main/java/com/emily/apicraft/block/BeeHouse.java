package com.emily.apicraft.block;

import com.emily.apicraft.block.entity.beehousing.BeeHouseEntity;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;


public class BeeHouse extends AbstractBeeHousingBlock {

    public BeeHouse() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(1.5f), BeeHouseEntity.class, Registries.TILE_ENTITIES.getSup("bee_house"));
    }
}
