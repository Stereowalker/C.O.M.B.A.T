package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CFeatures;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CFeature {
	public static final List<Feature<?>> FEATURES = new ArrayList<Feature<?>>();
	
	public static final Feature<TreeConfiguration> ACROTLEST_TREE = register("acrotlest_tree", new AcrotlestTreeFeature(TreeConfiguration.CODEC));
	public static final Feature<TreeConfiguration> MAGIC_TREE = register("magic_tree", new MagicTreeFeature(TreeConfiguration.CODEC));
	public static final Feature<NoneFeatureConfiguration> TSUNE_SPIKE = register("tsune_spike", new TsuneSpikeFeature(NoneFeatureConfiguration.CODEC));
	public static final Feature<NoneFeatureConfiguration> MAGENTA_TSUNE_COLUMN = register("magenta_tsune_spike", new TsuneColumnFeature(NoneFeatureConfiguration.CODEC));
	public static final Feature<ReplaceBlockConfiguration> RUBY_ORE = register("ruby_ore", new ReplaceBlockFeature(ReplaceBlockConfiguration.CODEC));
	
	public static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature){
		feature.setRegistryName(Combat.getInstance().location(name));
		FEATURES.add(feature);
		return feature;
	}

	public static void registerAll(IForgeRegistry<Feature<?>> registry) {
		for(Feature<?> feature: FEATURES) {
			registry.register(feature);
			Combat.debug("Feature: \""+feature.getRegistryName().toString()+"\" registered");
		}
		for (Pair<String,ConfiguredFeature<?, ?>> pair : CFeatures.CONFIGURED_FEATURES) {
			Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Combat.getInstance().location(pair.getLeft()), pair.getRight());
		}
		Combat.debug("All Features Registered");
	}
}
