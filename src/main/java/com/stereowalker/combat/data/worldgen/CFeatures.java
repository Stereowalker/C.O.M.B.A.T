package com.stereowalker.combat.data.worldgen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.CombatOreConfigurationPredicates;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.AcrotlestStraightTrunkPlacer;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.MagicStraightTrunkPlacer;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;

public abstract class CFeatures {
	public static final List<Pair<String,ConfiguredFeature<?, ?>>> CONFIGURED_FEATURES = new ArrayList<Pair<String,ConfiguredFeature<?, ?>>>();

	public static final ConfiguredFeature<TreeConfiguration, ?> AUSLDINE = register("ausldine", CFeature.MAGIC_TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(CBlocks.AUSLDINE_LOG.defaultBlockState()), new MagicStraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(CBlocks.AUSLDINE_LEAVES.defaultBlockState()), new SimpleStateProvider(CBlocks.AUSLDINE_SAPLING.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirt(new SimpleStateProvider(CBlocks.CALTAS.defaultBlockState())).build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> DEAD_OAK = register("dead_oak", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(CBlocks.DEAD_OAK_LOG.defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(Blocks.AIR.defaultBlockState()), new SimpleStateProvider(Blocks.AIR.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> REZAL = register("rezal", CFeature.MAGIC_TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(CBlocks.REZAL_LOG.defaultBlockState()), new MagicStraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(CBlocks.REZAL_LEAVES.defaultBlockState()), new SimpleStateProvider(CBlocks.REZAL_LEAVES.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirt(new SimpleStateProvider(CBlocks.CALTAS.defaultBlockState())).build()));
	public static final ConfiguredFeature<TreeConfiguration, ?> MONORIS = register("monoris", CFeature.ACROTLEST_TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(CBlocks.MONORIS_LOG.defaultBlockState()), new AcrotlestStraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(CBlocks.MONORIS_LEAVES.defaultBlockState()), new SimpleStateProvider(CBlocks.MONORIS_SAPLING.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().dirt(new SimpleStateProvider(CBlocks.PURIFIED_DIRT.defaultBlockState())).build()));

	public static final ConfiguredFeature<?, ?> TSUNE_SPIKE = register("tsune_spike", CFeature.TSUNE_SPIKE.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
	public static final ConfiguredFeature<?, ?> MAGENTA_TSUNE_COLUMN = register("magenta_tsune_column", CFeature.MAGENTA_TSUNE_COLUMN.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(1));


	public static final ConfiguredFeature<?, ?> TREES_MAGICAL = register("trees_magical", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(AUSLDINE.weighted(0.5F), REZAL.weighted(0.5F)), AUSLDINE)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_AUSLDINE = register("trees_ausldine", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(AUSLDINE.weighted(0.5F)), AUSLDINE)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_DEAD_OAK = register("trees_dead_oak", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(DEAD_OAK.weighted(0.5F)), DEAD_OAK)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_REZAL = register("trees_rezal", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(REZAL.weighted(0.5F)), REZAL)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_MONORIS = register("trees_monoris", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(MONORIS.weighted(0.5F)), MONORIS)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));

	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_RUBY_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, CFeatures.States.RUBY_ORE), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, CFeatures.States.RUBY_ORE));
	public static final ConfiguredFeature<?, ?> ORE_PELGAN = register("ore_plegan", Feature.ORE.configured(new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CFeatures.States.PELGAN_ORE, 9)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)).squared().count(20));
	public static final ConfiguredFeature<?, ?> ORE_LOZYNE = register("ore_lozyne", Feature.ORE.configured(new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.LOZYNE_ORE.defaultBlockState(), 8)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(17)).squared().count(1));
	public static final ConfiguredFeature<?, ?> ORE_SERABLE = register("ore_serable", Feature.ORE.configured(new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.SERABLE_ORE.defaultBlockState(), 7)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32)).squared().count(5));
	public static final ConfiguredFeature<?, ?> ORE_PYRANITE = register("ore_pyranite", Feature.ORE.configured(new OreConfiguration(CombatOreConfigurationPredicates.MEZEPINE, CBlocks.PYRANITE_ORE.defaultBlockState(), 16)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100)).squared().count(16));
	public static final ConfiguredFeature<?, ?> ORE_LIMESTONE = register("ore_limestone", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.LIMESTONE.defaultBlockState(), 14)).rangeTriangle(VerticalAnchor.absolute(31), VerticalAnchor.absolute(62)).squared().count(40));
	public static final ConfiguredFeature<?, ?> ORE_CASSITERITE = register("ore_cassiterite", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.CASSITERITE.defaultBlockState(), 15)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(63)).squared().count(10));
	public static final ConfiguredFeature<?, ?> ORE_YELLOW_CLUSTER = register("ore_yellow_cluster", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 3)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256)).squared().count(30));
	public static final ConfiguredFeature<?, ?> ORE_RUBY = register("ore_ruby", Feature.REPLACE_SINGLE_BLOCK.configured(new ReplaceBlockConfiguration(ORE_RUBY_TARGET_LIST)).rangeUniform(VerticalAnchor.absolute(4), VerticalAnchor.absolute(31)).squared().count(UniformInt.of(3, 8)));
	public static final ConfiguredFeature<?, ?> ORE_TRIDOX = register("ore_tridox", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.TRIDOX_ORE.defaultBlockState(), 5)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27)).squared().count(Config.SERVER.tridoxChance.get()));
	public static final ConfiguredFeature<?, ?> ORE_PASQUEM = register("ore_pasquem", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.PASQUEM_ORE.defaultBlockState(), 3)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)).squared().count(30));
	public static final ConfiguredFeature<?, ?> ORE_YELLOW_CLUSTER_MAGIC = register("ore_yellow_cluster_magic", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.YELLOW_MAGIC_CLUSTER.defaultBlockState(), 19)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256)).squared().count(190));
	public static final ConfiguredFeature<?, ?> ORE_TRIDOX_MAGIC = register("ore_tridox_magic", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.TRIDOX_ORE.defaultBlockState(), 12)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27)).squared().count(Config.SERVER.tridoxChance.get()*2));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_COPPER = register("dead_ore_copper", Feature.ORE.configured(new OreConfiguration(Features.ORE_COPPER_TARGET_LIST, 5)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(96)).squared().count(6));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_COAL = register("dead_ore_coal", Feature.ORE.configured(new OreConfiguration(Features.ORE_COAL_TARGET_LIST, 8)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127)).squared().count(20));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_IRON = register("dead_ore_iron", Feature.ORE.configured(new OreConfiguration(Features.ORE_IRON_TARGET_LIST, 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_GOLD = register("dead_ore_gold", Feature.ORE.configured(new OreConfiguration(Features.ORE_GOLD_TARGET_LIST, 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31)).squared().count(2));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_REDSTONE = register("dead_ore_redstone", Feature.ORE.configured(new OreConfiguration(Features.ORE_REDSTONE_TARGET_LIST, 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15)).squared().count(8));
	public static final ConfiguredFeature<?, ?> DEAD_ORE_DIAMOND = register("dead_ore_diamond", Feature.ORE.configured(new OreConfiguration(Features.ORE_DIAMOND_TARGET_LIST, 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15)).squared());
	public static final ConfiguredFeature<?, ?> DEAD_ORE_LAPIS = register("dead_ore_lapis", Feature.ORE.configured(new OreConfiguration(Features.ORE_LAPIS_TARGET_LIST, 3)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(30)).squared());
	public static final ConfiguredFeature<?, ?> DEAD_ORE_PASQUEM = register("dead_ore_pasquem", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, CBlocks.PASQUEM_ORE.defaultBlockState(), 17)).rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)).squared().count(30));
	
	public static final ConfiguredFeature<?, ?> LAKE_BIABLE = register("lake_biable", Feature.LAKE.configured(new BlockStateConfiguration(CBlocks.BIABLE.defaultBlockState())).range(Features.Decorators.FULL_RANGE).squared().rarity(4));
	public static final ConfiguredFeature<?, ?> LAKE_WHITE_TSUNE = register("lake_white_tsune", Feature.LAKE.configured(new BlockStateConfiguration(CBlocks.WHITE_TSUNE.defaultBlockState())).range(Features.Decorators.FULL_RANGE).squared().rarity(4));
	public static final ConfiguredFeature<?, ?> LAKE_YELLOW_TSUNE = register("lake_yellow_tsune", Feature.LAKE.configured(new BlockStateConfiguration(CBlocks.YELLOW_TSUNE.defaultBlockState())).range(Features.Decorators.FULL_RANGE).squared().rarity(4));
	public static final ConfiguredFeature<?, ?> LAKE_RED_TSUNE = register("lake_red_tsune", Feature.LAKE.configured(new BlockStateConfiguration(CBlocks.RED_TSUNE.defaultBlockState())).range(Features.Decorators.FULL_RANGE).squared().rarity(4));
	public static final ConfiguredFeature<?, ?> LAKE_PURPLE_TSUNE = register("lake_purple_tsune", Feature.LAKE.configured(new BlockStateConfiguration(CBlocks.PURPLE_TSUNE.defaultBlockState())).range(Features.Decorators.FULL_RANGE).squared().rarity(4));


	public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature){
		CONFIGURED_FEATURES.add(Pair.of(name,configuredFeature));
		return configuredFeature;
	}

	public static final class States {
		protected static final BlockState RUBY_ORE = CBlocks.RUBY_ORE.defaultBlockState();
		protected static final BlockState PELGAN_ORE = CBlocks.PELGAN_ORE.defaultBlockState();
	}
}
