package com.stereowalker.combat.data.worldgen.features;

import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.placement.CTreePlacements;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

public class CVegetationFeatures {
	static String n(String n) {return Combat.MODID+":"+n;}
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_MAGICAL = FeatureUtils.register(n("trees_magical"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CTreePlacements.AUSLDINE_CHECKED, 0.5F), new WeightedPlacedFeature(CTreePlacements.REZAL_CHECKED, 0.5F)), CTreePlacements.AUSLDINE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_AUSLDINE = FeatureUtils.register(n("trees_ausldine"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CTreePlacements.AUSLDINE_CHECKED, 0.5F)), CTreePlacements.AUSLDINE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_DEAD_OAK = FeatureUtils.register(Combat.MODID+":trees_dead_oak", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CTreePlacements.DEAD_OAK_CHECKED, 0.5F)), CTreePlacements.DEAD_OAK_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_REZAL = FeatureUtils.register(Combat.MODID+":trees_rezal", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CTreePlacements.REZAL_CHECKED, 0.5F)), CTreePlacements.REZAL_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_MONORIS = FeatureUtils.register(Combat.MODID+":trees_monoris", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CTreePlacements.MONORIS_CHECKED, 0.5F)), CTreePlacements.MONORIS_CHECKED));
}
