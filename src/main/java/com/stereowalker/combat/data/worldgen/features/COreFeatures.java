package com.stereowalker.combat.data.worldgen.features;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.stereowalker.combat.tags.BlockCTags;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.CombatOreConfigurationPredicates;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class COreFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PELGAN = FeatureUtils.createKey("combat:ore_plegan");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LOZYNE = FeatureUtils.createKey("combat:ore_lozyne");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SERABLE = FeatureUtils.createKey("combat:ore_serable");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PYRANITE = FeatureUtils.createKey("combat:ore_pyranite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE = FeatureUtils.createKey("combat:ore_limestone");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CASSITERITE = FeatureUtils.createKey("combat:ore_cassiterite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_YELLOW_CLUSTER = FeatureUtils.createKey("combat:ore_yellow_cluster");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY = FeatureUtils.createKey("combat:ore_ruby");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TRIDOX = FeatureUtils.createKey("combat:ore_tridox");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PASQUEM = FeatureUtils.createKey("combat:ore_pasquem");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_YELLOW_CLUSTER_MAGIC = FeatureUtils.createKey("combat:ore_yellow_cluster_magic");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TRIDOX_MAGIC = FeatureUtils.createKey("combat:ore_tridox_magic");
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_COPPER = register("dead_ore_copper", Feature.ORE, new OreConfiguration(OreFeatures.ORE_COPPER_TARGET_LIST, 5));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_COAL = register("dead_ore_coal", Feature.ORE, new OreConfiguration(OreFeatures.ORE_COAL_TARGET_LIST, 8));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_IRON = register("dead_ore_iron", Feature.ORE, new OreConfiguration(OreFeatures.ORE_IRON_TARGET_LIST, 4));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_GOLD = register("dead_ore_gold", Feature.ORE, new OreConfiguration(OreFeatures.ORE_GOLD_TARGET_LIST, 4));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_REDSTONE = register("dead_ore_redstone", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Blocks.REDSTONE_ORE.defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState())), 4));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_DIAMOND = register("dead_ore_diamond", Feature.ORE, new OreConfiguration(OreFeatures.ORE_DIAMOND_TARGET_LIST, 4));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_LAPIS = register("dead_ore_lapis", Feature.ORE, new OreConfiguration(OreFeatures.ORE_LAPIS_TARGET_LIST, 3));
	//	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEAD_ORE_PASQUEM = register("dead_ore_pasquem", Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, CBlocks.PASQUEM_ORE.defaultBlockState(), 17));

	//	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String p_206489_, F p_206490_, FC p_206491_) {
	//		return FeatureUtils.register(Combat.MODID+":"+p_206489_, p_206490_, p_206491_);
	//	}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> p_256317_) {
		RuleTest ruletest = new TagMatchTest(BlockCTags.MEZEPINE_ORE_REPLACEABLES);
		RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		FeatureUtils.register(p_256317_, ORE_PELGAN, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(ruletest, CBlocks.PELGAN_ORE.defaultBlockState()), OreConfiguration.target(CombatOreConfigurationPredicates.SLYAPHY, CBlocks.SLYAPHY_PELGAN_ORE.defaultBlockState())), 9));
		FeatureUtils.register(p_256317_, ORE_LOZYNE, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(ruletest, CBlocks.LOZYNE_ORE.defaultBlockState()), OreConfiguration.target(CombatOreConfigurationPredicates.SLYAPHY, CBlocks.SLYAPHY_LOZYNE_ORE.defaultBlockState())), 8));
		FeatureUtils.register(p_256317_, ORE_SERABLE, Feature.ORE, new OreConfiguration(ruletest, CBlocks.SERABLE_ORE.defaultBlockState(), 7));
		FeatureUtils.register(p_256317_, ORE_PYRANITE, Feature.ORE, new OreConfiguration(ruletest, CBlocks.PYRANITE_ORE.defaultBlockState(), 16));
		FeatureUtils.register(p_256317_, ORE_RUBY, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(ruletest1, CBlocks.RUBY_ORE.defaultBlockState())/*, OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState())*/), 3));
		FeatureUtils.register(p_256317_, ORE_TRIDOX, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.TRIDOX_ORE.defaultBlockState(), 5));
		FeatureUtils.register(p_256317_, ORE_PASQUEM, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.PASQUEM_ORE.defaultBlockState(), 3));
		FeatureUtils.register(p_256317_, ORE_LIMESTONE, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.LIMESTONE.defaultBlockState(), 14));
		FeatureUtils.register(p_256317_, ORE_CASSITERITE, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.CASSITERITE.defaultBlockState(), 15));
		FeatureUtils.register(p_256317_, ORE_TRIDOX_MAGIC, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.TRIDOX_ORE.defaultBlockState(), 12));
		FeatureUtils.register(p_256317_, ORE_YELLOW_CLUSTER, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 3));
		FeatureUtils.register(p_256317_, ORE_YELLOW_CLUSTER_MAGIC, Feature.ORE, new OreConfiguration(ruletest1, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 19));
	}
}
