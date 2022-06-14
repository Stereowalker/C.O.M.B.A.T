package com.stereowalker.combat.data.worldgen.features;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.CombatOreConfigurationPredicates;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class COreFeatures {
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_PELGAN_TARGET_LIST = ImmutableList.of(OreConfiguration.target(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.PELGAN_ORE.defaultBlockState()), OreConfiguration.target(CombatOreConfigurationPredicates.SLYAPHY, CBlocks.SLYAPHY_PELGAN_ORE.defaultBlockState()));
	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_LOZYNE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.LOZYNE_ORE.defaultBlockState()), OreConfiguration.target(CombatOreConfigurationPredicates.SLYAPHY, CBlocks.SLYAPHY_LOZYNE_ORE.defaultBlockState()));

	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PELGAN = register("ore_plegan", Feature.ORE, new OreConfiguration(ORE_PELGAN_TARGET_LIST, 9));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LOZYNE = register("ore_lozyne", Feature.ORE, new OreConfiguration(ORE_LOZYNE_TARGET_LIST, 8));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SERABLE = register("ore_serable", Feature.ORE, new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.SERABLE_ORE.defaultBlockState(), 7));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PYRANITE = register("ore_pyranite", Feature.ORE, new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.PYRANITE_ORE.defaultBlockState(), 16));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LIMESTONE = register("ore_limestone", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.LIMESTONE.defaultBlockState(), 14));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_CASSITERITE = register("ore_cassiterite", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.CASSITERITE.defaultBlockState(), 15));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_YELLOW_CLUSTER = register("ore_yellow_cluster", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 3));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_RUBY = register("ore_ruby", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, CBlocks.RUBY_ORE.defaultBlockState())/*, OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState())*/), 3));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_TRIDOX = register("ore_tridox", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.TRIDOX_ORE.defaultBlockState(), 5));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PASQUEM = register("ore_pasquem", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.PASQUEM_ORE.defaultBlockState(), 3));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_YELLOW_CLUSTER_MAGIC = register("ore_yellow_cluster_magic", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 19));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_TRIDOX_MAGIC = register("ore_tridox_magic", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.TRIDOX_ORE.defaultBlockState(), 12));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_COPPER = register("dead_ore_copper", Feature.ORE, new OreConfiguration(OreFeatures.ORE_COPPER_TARGET_LIST, 5));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_COAL = register("dead_ore_coal", Feature.ORE, new OreConfiguration(OreFeatures.ORE_COAL_TARGET_LIST, 8));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_IRON = register("dead_ore_iron", Feature.ORE, new OreConfiguration(OreFeatures.ORE_IRON_TARGET_LIST, 4));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_GOLD = register("dead_ore_gold", Feature.ORE, new OreConfiguration(OreFeatures.ORE_GOLD_TARGET_LIST, 4));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_REDSTONE = register("dead_ore_redstone", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.REDSTONE_ORE.defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState())), 4));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_DIAMOND = register("dead_ore_diamond", Feature.ORE, new OreConfiguration(OreFeatures.ORE_DIAMOND_TARGET_LIST, 4));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_LAPIS = register("dead_ore_lapis", Feature.ORE, new OreConfiguration(OreFeatures.ORE_LAPIS_TARGET_LIST, 3));
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_PASQUEM = register("dead_ore_pasquem", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.PASQUEM_ORE.defaultBlockState(), 17));

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String p_206489_, F p_206490_, FC p_206491_) {
		return FeatureUtils.register(Combat.MODID+":"+p_206489_, p_206490_, p_206491_);
	}
}
