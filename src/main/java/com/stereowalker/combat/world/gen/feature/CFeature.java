package com.stereowalker.combat.world.gen.feature;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.ReplaceBlockFeature;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CFeature {
	public static final List<Feature<?>> FEATURES = new ArrayList<Feature<?>>();
	//	public static final Structure<AcrotlestMineshaftConfig> ACROTLEST_MINESHAFT = register("acrotlest_mineshaft", new AcrotlestMineshaftStructure(AcrotlestMineshaftConfig::deserialize));
	//	public static final Structure<NoFeatureConfig> BLUE_ICE_TOWER = register("blue_ice_tower", new BlueIceTowerStructure(NoFeatureConfig::deserialize));

	public static final Feature<BaseTreeFeatureConfig> ACROTLEST_TREE = register("acrotlest_tree", new AcrotlestTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final Feature<NoFeatureConfig> TSUNE_SPIKE = register("tsune_spike", new TsuneSpikeFeature(NoFeatureConfig.CODEC));
	public static final Feature<NoFeatureConfig> MAGENTA_TSUNE_COLUMN = register("magenta_tsune_spike", new TsuneColumnFeature(NoFeatureConfig.CODEC));
	public static final Feature<ReplaceBlockConfig> RUBY_ORE = register("ruby_ore", new ReplaceBlockFeature(ReplaceBlockConfig.CODEC));
	
	public static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature){
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
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Combat.getInstance().location(pair.getLeft()), pair.getRight());
		}
		Combat.debug("All Features Registered");
	}
}
