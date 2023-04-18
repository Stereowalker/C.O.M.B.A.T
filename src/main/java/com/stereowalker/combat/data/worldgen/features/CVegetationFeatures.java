package com.stereowalker.combat.data.worldgen.features;

import java.util.List;

import com.stereowalker.combat.data.worldgen.placement.CTreePlacements;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CVegetationFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MAGICAL = FeatureUtils.createKey("combat:trees_magical");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AUSLDINE = FeatureUtils.createKey("combat:trees_ausldine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_DEAD_OAK = FeatureUtils.createKey("combat:trees_dead_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_REZAL = FeatureUtils.createKey("combat:trees_rezal");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MONORIS = FeatureUtils.createKey("combat:trees_monoris");
	
	public static void init() {}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstrap) {
		HolderGetter<Block> holdergetter = bootstrap.lookup(Registries.BLOCK);
		HolderGetter<PlacedFeature> holdergetter1 = bootstrap.lookup(Registries.PLACED_FEATURE);
		Holder<PlacedFeature> holder = holdergetter1.getOrThrow(CTreePlacements.AUSLDINE_CHECKED);
		Holder<PlacedFeature> holder1 = holdergetter1.getOrThrow(CTreePlacements.DEAD_OAK_CHECKED);
		Holder<PlacedFeature> holder2 = holdergetter1.getOrThrow(CTreePlacements.REZAL_CHECKED);
		Holder<PlacedFeature> holder3 = holdergetter1.getOrThrow(CTreePlacements.MONORIS_CHECKED);
		FeatureUtils.register(bootstrap, TREES_MAGICAL, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(holder, 0.5F), new WeightedPlacedFeature(holder2, 0.5F)), holder));
		FeatureUtils.register(bootstrap, TREES_AUSLDINE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(holder, 0.5F)), holder));
		FeatureUtils.register(bootstrap, TREES_DEAD_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(holder1, 0.5F)), holder1));
		FeatureUtils.register(bootstrap, TREES_REZAL, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(holder2, 0.5F)), holder2));
		FeatureUtils.register(bootstrap, TREES_MONORIS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(holder3, 0.5F)), holder3));
	}
}
