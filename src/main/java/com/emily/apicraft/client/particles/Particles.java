package com.emily.apicraft.client.particles;

import com.emily.apicraft.registry.Registries;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.mojang.logging.LogUtils.getLogger;

public class Particles {
    private Particles(){}

    public static void register(){
        Logger logger = getLogger();
        logger.debug("Registering particles");
    }
    public static final RegistryObject<ParticleType<BeeParticleOptions>> BEE_EXPLORE_PARTICLE = Registries.PARTICLE_TYPES.register("bee_explore_particle", BeeParticleType::new);
    public static final RegistryObject<ParticleType<BeeParticleOptions>> BEE_ROUND_TRIP_PARTICLE = Registries.PARTICLE_TYPES.register("bee_round_trip_particle", BeeParticleType::new);

}
