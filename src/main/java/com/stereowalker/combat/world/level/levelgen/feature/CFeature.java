package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.features.MiscAcrotlestFeatures;
import com.stereowalker.combat.data.worldgen.placement.MiscAcrotlestPlacements;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public abstract class CFeature {
	public static final Map<ResourceLocation,Feature<?>> FEATURES = new HashMap<ResourceLocation,Feature<?>>();
	
	public static final Feature<TreeConfiguration> ACROTLEST_TREE = register("acrotlest_tree", new AcrotlestTreeFeature(TreeConfiguration.CODEC));
	public static final Feature<TreeConfiguration> MAGIC_TREE = register("magic_tree", new MagicTreeFeature(TreeConfiguration.CODEC));
	public static final Feature<NoneFeatureConfiguration> TSUNE_SPIKE = register("tsune_spike", new TsuneSpikeFeature(NoneFeatureConfiguration.CODEC));
	public static final Feature<NoneFeatureConfiguration> MAGENTA_TSUNE_COLUMN = register("magenta_tsune_column", new TsuneColumnFeature(NoneFeatureConfiguration.CODEC));
	public static final Feature<ReplaceBlockConfiguration> RUBY_ORE = register("ruby_ore", new ReplaceBlockFeature(ReplaceBlockConfiguration.CODEC));
	
	public static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature){
		FEATURES.put(Combat.getInstance().location(name), feature);
		return feature;
	}

	public static void registerAll(RegisterHelper<Feature<?>> registry) {
		for(Entry<ResourceLocation, Feature<?>> feature: FEATURES.entrySet()) {
			registry.register(feature.getKey(), feature.getValue());
			Combat.debug("Feature: \""+feature.getKey().toString()+"\" registered");
		}
		new MiscAcrotlestFeatures();
		new MiscAcrotlestPlacements();
		Combat.debug("All Features Registered");
	}
}
