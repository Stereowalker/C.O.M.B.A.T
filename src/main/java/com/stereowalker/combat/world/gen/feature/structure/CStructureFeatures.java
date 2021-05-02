package com.stereowalker.combat.world.gen.feature.structure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.world.gen.feature.structure.ProbabilityStructureConfig.DimensionVariant;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public abstract class CStructureFeatures {

	public static final List<Pair<String,StructureFeature<?, ?>>> CONFIGURED_STRUCTURE_FEATURES = new ArrayList<Pair<String,StructureFeature<?, ?>>>();

	public static final StructureFeature<ProbabilityStructureConfig, ? extends Structure<ProbabilityStructureConfig>> ETHERION_TOWER = register("etherion_tower", CStructure.ETHERION_TOWER.withConfiguration(new ProbabilityStructureConfig(Config.SERVER.etherionTowerProbability.get().floatValue(), DimensionVariant.OVERWORLD)));
	public static final StructureFeature<ProbabilityStructureConfig, ? extends Structure<ProbabilityStructureConfig>> ETHERION_TOWER_ACROTLEST = register("etherion_tower_acrotlest", CStructure.ETHERION_TOWER.withConfiguration(new ProbabilityStructureConfig(Config.SERVER.etherionTowerProbability.get().floatValue(), DimensionVariant.ACROLEST)));
	public static final StructureFeature<AcrotlestMineshaftConfig, ? extends Structure<AcrotlestMineshaftConfig>> ACROTLEST_MINESHAFT_NORMAL = register("acrotlest_mineshaft", CStructure.ACROTLEST_MINESHAFT.withConfiguration(new AcrotlestMineshaftConfig((double)0.004F, AcrotlestMineshaftStructure.Type.NORMAL)));
	public static final StructureFeature<ProbabilityStructureConfig, ? extends Structure<ProbabilityStructureConfig>> ACROTLEST_PORTAL = register("acrotlest_portal", CStructure.ACROTLEST_PORTAL.withConfiguration(new ProbabilityStructureConfig(Config.SERVER.acrotlestPortalProbability.get(), DimensionVariant.ACROLEST)));
	public static final StructureFeature<ProbabilityStructureConfig, ? extends Structure<ProbabilityStructureConfig>> OVERWORLD_PORTAL = register("overworld_portal", CStructure.ACROTLEST_PORTAL.withConfiguration(new ProbabilityStructureConfig(Config.SERVER.acrotlestPortalProbability.get(), DimensionVariant.OVERWORLD)));
	public static final StructureFeature<ProbabilityStructureConfig, ? extends Structure<ProbabilityStructureConfig>> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", CStructure.MAGIC_STONE_DEPOSIT.withConfiguration(new ProbabilityStructureConfig(Config.SERVER.magicStoneDepositProbability.get())));

	public static <FC extends IFeatureConfig, F extends Structure<FC>> StructureFeature<FC, F> register(String name, StructureFeature<FC, F> structureFeature){
		CONFIGURED_STRUCTURE_FEATURES.add(Pair.of(name,structureFeature));
		return structureFeature;
	}
}
