package com.emily.apicraft.block.entity;

import cofh.core.block.entity.SecurableBlockEntity;
import cofh.lib.api.block.entity.ITickableTile;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class AbstractBeeHousingBlockEntity extends SecurableBlockEntity implements IBeeHousing, ITickableTile.IServerTickable, MenuProvider {
    public AbstractBeeHousingBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    // region IServerTickable
    @Override
    public void tickServer() {

    }
    // endregion

    // region IBeeHousing
    @Override
    public Level getBeeHousingLevel() {
        return null;
    }

    @Override
    public BlockPos getBeeHousingPos() {
        return null;
    }

    @Override
    public Biome getBeeHousingBiome() {
        return null;
    }
    // endregion

    // region IClimateProvider
    @Override
    public int getExactTemperature() {
        return 0;
    }

    @Override
    public int getExactHumidity() {
        return 0;
    }
    // endregion
}
