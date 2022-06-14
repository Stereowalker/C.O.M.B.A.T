package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.tags.BiomeCTags;
import com.stereowalker.combat.world.level.levelgen.feature.AcrotlestMineshaftFeature;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.CombatStructures;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class CStructureFeatures {
	public static final Holder<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER = StructureFeatures.register(CombatStructures.ETHERION_TOWER, CStructureFeature.ETHERION_TOWER.configured(new ProbabilityStructureConfiguration(Config.SERVER.etherionTowerProbability.get().floatValue(), ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD), BiomeCTags.HAS_ETHERION_TOWER));
	public static final Holder<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER_ACROTLEST = StructureFeatures.register(CombatStructures.ETHERION_TOWER_ACROTLEST, CStructureFeature.ETHERION_TOWER.configured(new ProbabilityStructureConfiguration(Config.SERVER.etherionTowerProbability.get().floatValue(), ProbabilityStructureConfiguration.DimensionVariant.ACROLEST), BiomeCTags.HAS_ETHERION_TOWER_ACROTLEST));
	public static final Holder<ConfiguredStructureFeature<?, ?>> ACROTLEST_MINESHAFT_NORMAL = StructureFeatures.register(CombatStructures.ACROTLEST_MINESHAFT_NORMAL, CStructureFeature.ACROTLEST_MINESHAFT.configured(new AcrotlestMineshaftConfiguration(0.004F, AcrotlestMineshaftFeature.Type.NORMAL), BiomeCTags.ACROTLEST_MINESHAFT_NORMAL));
	public static final Holder<ConfiguredStructureFeature<?, ?>> ACROTLEST_PORTAL = StructureFeatures.register(CombatStructures.ACROTLEST_PORTAL, CStructureFeature.ACROTLEST_PORTAL.configured(new ProbabilityStructureConfiguration(Config.SERVER.acrotlestPortalProbability.get(), ProbabilityStructureConfiguration.DimensionVariant.ACROLEST), BiomeCTags.ACROTLEST_PORTAL));
	public static final Holder<ConfiguredStructureFeature<?, ?>> OVERWORLD_PORTAL = StructureFeatures.register(CombatStructures.OVERWORLD_PORTAL, CStructureFeature.ACROTLEST_PORTAL.configured(new ProbabilityStructureConfiguration(Config.SERVER.acrotlestPortalProbability.get(), ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD), BiomeCTags.OVERWORLD_PORTAL));
	public static final Holder<ConfiguredStructureFeature<?, ?>> MAGIC_STONE_DEPOSIT = StructureFeatures.register(CombatStructures.MAGIC_STONE_DEPOSIT, CStructureFeature.MAGIC_STONE_DEPOSIT.configured(new ProbabilityStructureConfiguration(Config.SERVER.magicStoneDepositProbability.get()), BiomeCTags.MAGIC_STONE_DEPOSIT));
}
