package com.emily.apicraft.client.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class BeeParticleType extends ParticleType<BeeParticleOptions> {
    public BeeParticleType(){
        super(false, BeeParticleOptions.DESERIALIZER);
    }
    @Override
    public @NotNull Codec<BeeParticleOptions> codec() {
        return BeeParticleOptions.codec(this);
    }
}
