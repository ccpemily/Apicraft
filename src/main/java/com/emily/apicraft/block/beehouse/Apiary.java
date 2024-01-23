package com.emily.apicraft.block.beehouse;

import com.emily.apicraft.block.entity.beehouse.ApiaryEntity;
import com.emily.apicraft.registry.Registries;
import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.material;

public class Apiary extends AbstractBeeHousingBlock{
    public Apiary(){
        super(BlockBehaviour.Properties.of().strength(1.5f), ApiaryEntity.class, Registries.TILE_ENTITIES.getSup("apiary"));
    }
}
