//package com.stereowalker.combat.world.level.biome;
//
//import java.util.List;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.Lifecycle;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import com.stereowalker.combat.world.level.newbiome.layer.AcrotlestLayers;
//
//import net.minecraft.core.Registry;
//import net.minecraft.resources.RegistryLookupCodec;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.BiomeSource;
//import net.minecraft.world.level.biome.Biomes;
//import net.minecraft.world.level.newbiome.layer.Layer;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//public class AcrotlestBiomeSource extends BiomeSource {
//	public static final Codec<AcrotlestBiomeSource> CODEC = RecordCodecBuilder.create((builder) -> {
//		return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((acrotlestProvider) -> {
//			return acrotlestProvider.seed;
//		}), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", Boolean.valueOf(false), Lifecycle.stable()).forGetter((acrotlestProvider) -> {
//			return acrotlestProvider.legacyBiomes;
//		}), Codec.BOOL.fieldOf("large_biomes").orElse(false).stable().forGetter((acrotlestProvider) -> {
//			return acrotlestProvider.largeBiomes;
//		}), RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((acrotlestProvider) -> {
//			return acrotlestProvider.lookupRegistry;
//		})).apply(builder, builder.stable(AcrotlestBiomeSource::new));
//	});
//	private final Layer genBiomes;
//	private static final List<ResourceKey<Biome>> biomes = ImmutableList.of(Biomes.FROZEN_RIVER, CBiomes.ACROTLEST_FOREST, CBiomes.HISOV_SANDS, CBiomes.ACROTLEST_MOUNTAINS, CBiomes.ACROTLEST_RIVER, Biomes.DEEP_FROZEN_OCEAN);
//	private final long seed;
//	private final boolean legacyBiomes;
//	private final boolean largeBiomes;
//	private final Registry<Biome> lookupRegistry;
//
//	public AcrotlestBiomeSource(long seed, boolean legacyBiomes, boolean largeBiomes, Registry<Biome> lookupRegistry) {
//		super(biomes.stream().map((key) -> {
//			return () -> {
//				return lookupRegistry.getOrThrow(key);
//			};
//		}));
//		this.seed = seed;
//		this.legacyBiomes = legacyBiomes;
//		this.largeBiomes = largeBiomes;
//		this.lookupRegistry = lookupRegistry;
//		this.genBiomes = AcrotlestLayers.getDefaultLayer(seed, legacyBiomes, largeBiomes ? 6 : 4, 4);
//	}
//
//	@Override
//	protected Codec<? extends BiomeSource> codec() {
//		return CODEC;
//	}
//
//	@OnlyIn(Dist.CLIENT)
//	public BiomeSource withSeed(long seed) {
//		return new AcrotlestBiomeSource(seed, this.legacyBiomes, this.largeBiomes, this.lookupRegistry);
//	}
//
//	public Biome getNoiseBiome(int x, int y, int z) {
//		return this.genBiomes.get(this.lookupRegistry, x, z);
//	}
//}