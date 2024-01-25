package com.emily.apicraft.client.particles;

import com.emily.apicraft.block.entity.beehouse.AbstractBeeHousingBlockEntity;
import com.emily.apicraft.genetics.genome.BeeGenome;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ParticleRenderer {
    private static final DustParticleOptions POLLEN_PARTICLE = new DustParticleOptions(new Vector3f(0.9f, 0.75f, 0), 1.0f);
    private static final SimpleParticleType DRIPPING_HONEY_PARTICLE = ParticleTypes.DRIPPING_HONEY;

    public static boolean shouldSpawnParticle(Level world) {
        Minecraft mc = Minecraft.getInstance();
        ParticleStatus particleSetting = mc.options.particles().get();

        if (particleSetting == ParticleStatus.MINIMAL) { // Minimal, 10%
            return world.random.nextInt(10) == 0;
        } else if (particleSetting == ParticleStatus.DECREASED) { // Decreased, 67%
            return world.random.nextInt(3) != 0;
        } else { // All, 100%
            return true;
        }
    }

    public static void addBeeHiveFX(AbstractBeeHousingBlockEntity housing, BeeGenome genome, List<BlockPos> flowers){
        ClientLevel level = (ClientLevel) housing.getBeeHousingLevel();
        if(level == null || !shouldSpawnParticle(level) || Minecraft.getInstance().player == null){
            return;
        }
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        BlockPos playerPos = Minecraft.getInstance().player.getOnPos();
        Vector3d particleStartPos = housing.getBeeFXCoordinates();

        double playerDistanceSq = playerPos.distSqr(new Vec3i((int) particleStartPos.x, (int) particleStartPos.y, (int) particleStartPos.z));
        if (level.random.nextInt(1024) < playerDistanceSq) {
            return;
        }

        int color = genome.getSpecies().getValue().getColor(0);
        int rand = level.random.nextInt(100);

        if(rand < 75 && !flowers.isEmpty()){
            BlockPos destination = flowers.get(level.random.nextInt(flowers.size()));
            //LOGGER.debug("Adding particles : BeeRoundTripParticle");
            level.addParticle(new BeeParticleOptions(Particles.BEE_ROUND_TRIP_PARTICLE, destination, color),
                    particleStartPos.x, particleStartPos.y, particleStartPos.z, 0, 0, 0);
        } else {
            Vec3i area = housing.applyTerritoryModifier(((Alleles.Territory) genome.getAllele(AlleleTypes.TERRITORY, true)).getValue());
            Vec3i offset = housing.getBeeHousingPos().offset(-area.getX() / 2, -area.getY() / 4, -area.getZ() / 2);
            BlockPos destination = new BlockPos(
                    level.random.nextInt(area.getX()), level.random.nextInt(area.getY()), level.random.nextInt(area.getZ())
            ).offset(offset);
            //LOGGER.debug("Adding particles : BeeExploreParticle");
            level.addParticle(new BeeParticleOptions(Particles.BEE_EXPLORE_PARTICLE, destination, color),
                    particleStartPos.x, particleStartPos.y, particleStartPos.z, 0, 0, 0);
        }
    }

    public static void addEntityHoneyDustFX(Level world, double x, double y, double z) {
        if (shouldSpawnParticle(world)) {
            world.addParticle(POLLEN_PARTICLE, x, y, z, 0, 0, 0);
        }
    }
}
