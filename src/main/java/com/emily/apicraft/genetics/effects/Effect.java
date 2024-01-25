package com.emily.apicraft.genetics.effects;

import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.emily.apicraft.genetics.BeeGenome;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class Effect {
    public Effect(){

    }
    public EffectData doEffect(BeeGenome genome, EffectData data, IBeeHousing housing){
        return data;
    }

    @OnlyIn(Dist.CLIENT)
    public EffectData doEffectFX(BeeGenome genome, EffectData data, IBeeHousing housing){
        return data;
    }

    public static AABB getEffectAreaBox(BeeGenome genome, IBeeHousing housing){
        Vec3i territory = housing.applyTerritoryModifier(genome.getTerritory());
        Vec3i offset = new Vec3i(-territory.getX() / 2, -territory.getY() / 2, -territory.getZ() / 2);
        BlockPos min = housing.getBeeHousingPos().offset(offset);
        BlockPos max = min.offset(offset);

        return new AABB(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    public static <T extends Entity> List<T> getEntitiesInRange(BeeGenome genome, IBeeHousing housing, Class<T> entityClass){
        AABB boundingBox = getEffectAreaBox(genome, housing);
        return housing.getBeeHousingLevel().getEntitiesOfClass(entityClass, boundingBox);
    }
}
