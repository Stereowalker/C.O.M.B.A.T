package com.stereowalker.combat.world.biome.provider;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.world.biome.CBiomes;
import com.stereowalker.combat.world.gen.layer.AcrotlestLayerUtil;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AcrotlestBiomeProvider extends BiomeProvider {
	public static final Codec<AcrotlestBiomeProvider> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((acrotlestProvider) -> {
			return acrotlestProvider.seed;
		}), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", Boolean.valueOf(false), Lifecycle.stable()).forGetter((acrotlestProvider) -> {
			return acrotlestProvider.legacyBiomes;
		}), Codec.BOOL.fieldOf("large_biomes").orElse(false).stable().forGetter((acrotlestProvider) -> {
			return acrotlestProvider.largeBiomes;
		}), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((acrotlestProvider) -> {
			return acrotlestProvider.lookupRegistry;
		})).apply(builder, builder.stable(AcrotlestBiomeProvider::new));
	});
	private final Layer genBiomes;
	private static final List<RegistryKey<Biome>> biomes = ImmutableList.of(Biomes.FROZEN_RIVER, CBiomes.ACROTLEST_FOREST, CBiomes.HISOV_SANDS, CBiomes.ACROTLEST_MOUNTAINS, CBiomes.ACROTLEST_RIVER, Biomes.DEEP_FROZEN_OCEAN);
	private final long seed;
	private final boolean legacyBiomes;
	private final boolean largeBiomes;
	private final Registry<Biome> lookupRegistry;

	public AcrotlestBiomeProvider(long seed, boolean legacyBiomes, boolean largeBiomes, Registry<Biome> lookupRegistry) {
		super(biomes.stream().map((key) -> {
			return () -> {
				return lookupRegistry.getOrThrow(key);
			};
		}));
		this.seed = seed;
		this.legacyBiomes = legacyBiomes;
		this.largeBiomes = largeBiomes;
		this.lookupRegistry = lookupRegistry;
		this.genBiomes = AcrotlestLayerUtil.func_237215_a_(seed, legacyBiomes, largeBiomes ? 6 : 4, 4);
	}

	protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
		return CODEC;
	}

	@OnlyIn(Dist.CLIENT)
	public BiomeProvider getBiomeProvider(long seed) {
		return new AcrotlestBiomeProvider(seed, this.legacyBiomes, this.largeBiomes, this.lookupRegistry);
	}

	public Biome getNoiseBiome(int x, int y, int z) {
		return this.genBiomes.func_242936_a(this.lookupRegistry, x, z);
	}
}