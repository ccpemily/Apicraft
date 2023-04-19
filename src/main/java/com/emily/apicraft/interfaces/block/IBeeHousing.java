package com.emily.apicraft.interfaces.block;

import com.emily.apicraft.interfaces.climatology.IClimateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public interface IBeeHousing extends IClimateProvider {
    Level getBeeHousingLevel();
    BlockPos getBeeHousingPos();
    Biome getBeeHousingBiome();
}
