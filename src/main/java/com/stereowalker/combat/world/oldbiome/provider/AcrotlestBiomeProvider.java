package com.stereowalker.combat.world.oldbiome.provider;
//package com.stereowalker.combat.world.biome.provider;
//
//import java.util.Set;
//
//import com.google.common.collect.ImmutableSet;
//import com.stereowalker.combat.world.biome.CBiomes;
//
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.Biomes;
//import net.minecraft.world.biome.provider.BiomeProvider;
//import net.minecraft.world.gen.layer.Layer;
//import net.minecraft.world.gen.layer.LayerUtil;
//
//public class AcrotlestBiomeProvider extends BiomeProvider {
//	private final Layer genBiomes;
//	private final static Set<Biome> biomes = ImmutableSet.of(Biomes.FROZEN_RIVER, CBiomes.ACROTLEST_FOREST, CBiomes.HISOV_SANDS, CBiomes.ACROTLEST_MOUNTAINS, CBiomes.ACROTLEST_RIVER, Biomes.DEEP_FROZEN_OCEAN);
//
//	public AcrotlestBiomeProvider(AcrotlestBiomeProviderSettings settingsProvider) {
//		super(biomes);
//		this.genBiomes = LayerUtil.func_227474_a_(settingsProvider.getSeed(), settingsProvider.getWorldType(), settingsProvider.getGeneratorSettings());
//	}
//
//	@Override
//	public Biome getNoiseBiome(int p_225526_1_, int p_225526_2_, int p_225526_3_) {
//		return this.genBiomes.func_215738_a(p_225526_1_, p_225526_3_);
//	}
//}