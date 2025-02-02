/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.client.fx;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import java.util.Locale;

public class SparkleParticleData implements ParticleOptions {
	public static final Codec<SparkleParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
			Codec.FLOAT.fieldOf("r").forGetter(d -> d.r),
			Codec.FLOAT.fieldOf("g").forGetter(d -> d.g),
			Codec.FLOAT.fieldOf("b").forGetter(d -> d.b),
			Codec.INT.fieldOf("m").forGetter(d -> d.m),
			Codec.DOUBLE.fieldOf("mx").forGetter(d -> d.mx),
			Codec.DOUBLE.fieldOf("my").forGetter(d -> d.my),
			Codec.DOUBLE.fieldOf("mz").forGetter(d -> d.mz)
	).apply(instance, SparkleParticleData::new));
	public final float size;
	public final float r, g, b;
	public final int m;
	public final double mx, my, mz;

	public static SparkleParticleData sparkle(float size, float r, float g, float b, int m, double mx, double my, double mz) {
		return new SparkleParticleData(size, r, g, b, m, mx, my, mz);
	}

	public SparkleParticleData(float size, float r, float g, float b, int m, double mx, double my, double mz) {
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.m = m;
		this.mx = mx;
		this.my = my;
		this.mz = mz;
	}

	@Nonnull
	@Override
	public ParticleType<SparkleParticleData> getType() {
		return ModParticles.SPARKLE;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buf) {
		buf.writeFloat(size);
		buf.writeFloat(r);
		buf.writeFloat(g);
		buf.writeFloat(b);
		buf.writeInt(m);
		buf.writeDouble(mx);
		buf.writeDouble(my);
		buf.writeDouble(mz);
	}

	@Nonnull
	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d %.2f %.2f %.2f",
				ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.size, this.r, this.g, this.b, this.m, this.mx, this.my, this.mz);
	}

	public static final Deserializer<SparkleParticleData> DESERIALIZER = new Deserializer<SparkleParticleData>() {
		@Nonnull
		@Override
		public SparkleParticleData fromCommand(@Nonnull ParticleType<SparkleParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
			reader.expect(' ');
			float size = reader.readFloat();
			reader.expect(' ');
			float r = reader.readFloat();
			reader.expect(' ');
			float g = reader.readFloat();
			reader.expect(' ');
			float b = reader.readFloat();
			reader.expect(' ');
			int m = reader.readInt();
			reader.expect(' ');
			double mx = reader.readDouble();
			reader.expect(' ');
			double my = reader.readDouble();
			reader.expect(' ');
			double mz = reader.readDouble();
			return new SparkleParticleData(size, r, g, b, m, mx, my, mz);
		}

		@Override
		public SparkleParticleData fromNetwork(@Nonnull ParticleType<SparkleParticleData> type, FriendlyByteBuf buf) {
			return new SparkleParticleData(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
		}
	};
}
