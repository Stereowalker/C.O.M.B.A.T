package com.stereowalker.combat.data.worldgen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.world.level.levelgen.feature.AcrotlestMineshaftFeature;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class CStructureFeatures {

	public static final List<Pair<String,ConfiguredStructureFeature<?, ?>>> CONFIGURED_STRUCTURE_FEATURES = new ArrayList<Pair<String,ConfiguredStructureFeature<?, ?>>>();

	public static final ConfiguredStructureFeature<ProbabilityStructureConfiguration, ? extends StructureFeature<ProbabilityStructureConfiguration>> ETHERION_TOWER = register("etherion_tower", CStructureFeature.ETHERION_TOWER.configured(new ProbabilityStructureConfiguration(Config.SERVER.etherionTowerProbability.get().floatValue(), ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD)));
	public static final ConfiguredStructureFeature<ProbabilityStructureConfiguration, ? extends StructureFeature<ProbabilityStructureConfiguration>> ETHERION_TOWER_ACROTLEST = register("etherion_tower_acrotlest", CStructureFeature.ETHERION_TOWER.configured(new ProbabilityStructureConfiguration(Config.SERVER.etherionTowerProbability.get().floatValue(), ProbabilityStructureConfiguration.DimensionVariant.ACROLEST)));
	public static final ConfiguredStructureFeature<AcrotlestMineshaftConfiguration, ? extends StructureFeature<AcrotlestMineshaftConfiguration>> ACROTLEST_MINESHAFT_NORMAL = register("acrotlest_mineshaft", CStructureFeature.ACROTLEST_MINESHAFT.configured(new AcrotlestMineshaftConfiguration(0.004F, AcrotlestMineshaftFeature.Type.NORMAL)));
	public static final ConfiguredStructureFeature<ProbabilityStructureConfiguration, ? extends StructureFeature<ProbabilityStructureConfiguration>> ACROTLEST_PORTAL = register("acrotlest_portal", CStructureFeature.ACROTLEST_PORTAL.configured(new ProbabilityStructureConfiguration(Config.SERVER.acrotlestPortalProbability.get(), ProbabilityStructureConfiguration.DimensionVariant.ACROLEST)));
	public static final ConfiguredStructureFeature<ProbabilityStructureConfiguration, ? extends StructureFeature<ProbabilityStructureConfiguration>> OVERWORLD_PORTAL = register("overworld_portal", CStructureFeature.ACROTLEST_PORTAL.configured(new ProbabilityStructureConfiguration(Config.SERVER.acrotlestPortalProbability.get(), ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD)));
	public static final ConfiguredStructureFeature<ProbabilityStructureConfiguration, ? extends StructureFeature<ProbabilityStructureConfiguration>> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", CStructureFeature.MAGIC_STONE_DEPOSIT.configured(new ProbabilityStructureConfiguration(Config.SERVER.magicStoneDepositProbability.get())));

	public static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String name, ConfiguredStructureFeature<FC, F> structureFeature){
		CONFIGURED_STRUCTURE_FEATURES.add(Pair.of(name,structureFeature));
		return structureFeature;
	}
}
