package com.emily.apicraft.interfaces.block;

import com.emily.apicraft.core.lib.ErrorStates;
import com.emily.apicraft.interfaces.climatology.IClimateModifierProvider;
import com.emily.apicraft.interfaces.climatology.IClimateProvider;
import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import com.emily.apicraft.inventory.BeeHousingItemInv;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.joml.Vector3d;

import java.util.Optional;

public interface IBeeHousing extends IClimateProvider, IBeeBreeder, IBeeModifierProvider, IClimateModifierProvider {
    Level getBeeHousingLevel();
    BlockPos getBeeHousingPos();
    Optional<Biome> getBeeHousingBiome();
    BeeHousingItemInv getBeeHousingInv();
    String getBeeHousingOwnerName();
    void setErrorState(ErrorStates state);
    ErrorStates getErrorState();
    Vector3d getBeeFXCoordinates();
}
