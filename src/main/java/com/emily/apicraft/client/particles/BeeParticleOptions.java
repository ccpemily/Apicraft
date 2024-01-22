package com.emily.apicraft.client.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class BeeParticleOptions implements ParticleOptions {
    public final ParticleType<BeeParticleOptions> type;
    public final BlockPos destination;
    public final int color;

    public static final Deserializer<BeeParticleOptions> DESERIALIZER = new Deserializer<>(){
        @Nonnull
        @Override
        public BeeParticleOptions fromCommand(@Nonnull ParticleType<BeeParticleOptions> type, @Nonnull StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            long direction = reader.readLong();
            reader.expect(' ');
            int color = reader.readInt();
            return new BeeParticleOptions(type, direction, color);
        }

        @Override
        public @NotNull BeeParticleOptions fromNetwork(@Nonnull ParticleType<BeeParticleOptions> type, FriendlyByteBuf buf) {
            return new BeeParticleOptions(type, buf.readLong(), buf.readInt());
        }
    };

    public BeeParticleOptions(ParticleType<BeeParticleOptions> type, long destination, int color) {
        this.type = type;
        this.destination = BlockPos.of(destination);
        this.color = color;
    }

    public BeeParticleOptions(RegistryObject<ParticleType<BeeParticleOptions>> type, BlockPos destination, int color) {
        this.type = type.get();
        this.destination = destination;
        this.color = color;
    }

    @Nonnull
    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(@Nonnull FriendlyByteBuf buffer) {
        buffer.writeLong(destination.asLong());
        buffer.writeInt(color);
    }

    @Nonnull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %d %d %d %d", Registry.PARTICLE_TYPE.getKey(type), destination.getX(), destination.getY(), destination.getZ(), color);
    }

    public static @NotNull Codec<BeeParticleOptions> codec(ParticleType<BeeParticleOptions> type) {
        return RecordCodecBuilder.create(val -> val.group(Codec.LONG.fieldOf("direction").forGetter(data -> data.destination.asLong()), Codec.INT.fieldOf("color").forGetter(data -> data.color)).apply(val, (destination1, color1) -> new BeeParticleOptions(type, destination1, color1)));
    }
}
