package com.stereowalker.combat.world.gen.feature;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.world.gen.placement.CPlacement;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public abstract class CFeatures {
	public static final List<Pair<String,ConfiguredFeature<?, ?>>> CONFIGURED_FEATURES = new ArrayList<Pair<String,ConfiguredFeature<?, ?>>>();

	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> AUSLDINE = register("ausldine", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CBlocks.AUSLDINE_LOG.getDefaultState()), new SimpleBlockStateProvider(CBlocks.AUSLDINE_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DEAD_OAK = register("dead_oak", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CBlocks.DEAD_OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> REZAL = register("rezal", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CBlocks.REZAL_LOG.getDefaultState()), new SimpleBlockStateProvider(CBlocks.REZAL_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MONORIS = register("monoris", CFeature.ACROTLEST_TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CBlocks.MONORIS_LOG.getDefaultState()), new SimpleBlockStateProvider(CBlocks.MONORIS_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));

	public static final ConfiguredFeature<?, ?> TSUNE_SPIKE = register("tsune_spike", CFeature.TSUNE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(3));
	public static final ConfiguredFeature<?, ?> MAGENTA_TSUNE_COLUMN = register("magenta_tsune_column", CFeature.MAGENTA_TSUNE_COLUMN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(1));
	
	
	public static final ConfiguredFeature<?, ?> TREES_MAGICAL = register("trees_magical", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(AUSLDINE.withChance(0.5F), REZAL.withChance(0.5F)), AUSLDINE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_AUSLDINE = register("trees_ausldine", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(AUSLDINE.withChance(0.5F)), AUSLDINE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_DEAD_OAK = register("trees_dead_oak", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(DEAD_OAK.withChance(0.5F)), DEAD_OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_REZAL = register("trees_rezal", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(REZAL.withChance(0.5F)), REZAL)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
	public static final ConfiguredFeature<?, ?> TREES_MONORIS = register("trees_monoris", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(MONORIS.withChance(0.5F)), MONORIS)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

	public static final ConfiguredFeature<?, ?> ORE_PELGAN = register("ore_plegan", Feature.ORE.withConfiguration(new OreFeatureConfig(CombatFillerBlockType.MEZEPINE, CBlocks.PELGAN_ORE.getDefaultState(), 9)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 64))).square().count(20));
	public static final ConfiguredFeature<?, ?> ORE_LOZYNE = register("ore_lozyne", Feature.ORE.withConfiguration(new OreFeatureConfig(CombatFillerBlockType.MEZEPINE, CBlocks.LOZYNE_ORE.getDefaultState(), 8)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 17))).square().count(1));
	public static final ConfiguredFeature<?, ?> ORE_SERABLE = register("ore_serable", Feature.ORE.withConfiguration(new OreFeatureConfig(CombatFillerBlockType.MEZEPINE, CBlocks.SERABLE_ORE.getDefaultState(), 7)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 32))).square().count(5));
	public static final ConfiguredFeature<?, ?> ORE_PYRANITE = register("ore_pyranite", Feature.ORE.withConfiguration(new OreFeatureConfig(CombatFillerBlockType.MEZEPINE, CBlocks.PYRANITE_ORE.getDefaultState(), 16)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 100))).square().count(16));
	public static final ConfiguredFeature<?, ?> ORE_LIMESTONE = register("ore_limestone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.LIMESTONE.getDefaultState(), 14)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(31, 31, 62))).square().count(40));
	public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.COPPER_ORE.getDefaultState(), 6)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 74))).square().count(Config.SERVER.copperChance.get()));
	public static final ConfiguredFeature<?, ?> ORE_CASSITERITE = register("ore_cassiterite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.CASSITERITE.getDefaultState(), 15)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 63))).square().count(10));
	public static final ConfiguredFeature<?, ?> ORE_YELLOW_CLUSTER = register("ore_yellow_cluster", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.YELLOW_MAGIC_CLUSTER.getDefaultState(), 3)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().count(30));
	public static final ConfiguredFeature<?, ?> ORE_RUBY = register("ore_ruby", CFeature.RUBY_ORE.withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), CBlocks.RUBY_ORE.getDefaultState())).withPlacement(CPlacement.RUBY_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	public static final ConfiguredFeature<?, ?> ORE_TRIDOX = register("ore_tridox", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.TRIDOX_ORE.getDefaultState(), 5)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 27))).square().count(Config.SERVER.tridoxChance.get()));
	public static final ConfiguredFeature<?, ?> ORE_PASQUEM = register("ore_pasquem", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.PASQUEM_ORE.getDefaultState(), 3)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128))).square().count(30));
	public static final ConfiguredFeature<?, ?> ORE_YELLOW_CLUSTER_MAGIC = register("ore_yellow_cluster_magic", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.YELLOW_MAGIC_CLUSTER.getDefaultState(), 19)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().count(190));
	public static final ConfiguredFeature<?, ?> ORE_TRIDOX_MAGIC = register("ore_tridox_magic", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.TRIDOX_ORE.getDefaultState(), 12)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 27))).square().count(Config.SERVER.tridoxChance.get()*2));
	public static final ConfiguredFeature<?, ?> ORE_COPPER_DEAD = register("ore_copper_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.COPPER_ORE.getDefaultState(), 3)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 74))).square().count(Config.SERVER.copperChance.get()));
	public static final ConfiguredFeature<?, ?> ORE_COAL_DEAD = register("ore_coal_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.getDefaultState(), 13)).range(128).square().count(20));
	public static final ConfiguredFeature<?, ?> ORE_IRON_DEAD = register("ore_iron_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.getDefaultState(), 5)).range(64).square().count(20));
	public static final ConfiguredFeature<?, ?> ORE_GOLD_DEAD = register("ore_gold_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.GOLD_ORE.getDefaultState(), 5)).range(32).square().count(2));
	public static final ConfiguredFeature<?, ?> ORE_REDSTONE_DEAD = register("ore_redstone_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.REDSTONE_ORE.getDefaultState(), 4)).range(16).square().count(8));
	public static final ConfiguredFeature<?, ?> ORE_DIAMOND_DEAD = register("ore_diamond_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.DIAMOND_ORE.getDefaultState(), 4)).range(16).square());
	public static final ConfiguredFeature<?, ?> ORE_LAPIS_DEAD = register("ore_lapis_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.LAPIS_ORE.getDefaultState(), 3)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());
	public static final ConfiguredFeature<?, ?> ORE_PASQUEM_DEAD = register("ore_pasquem_dead", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, CBlocks.PASQUEM_ORE.getDefaultState(), 17)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128))).square().count(30));

	public static final ConfiguredFeature<?, ?> LAKE_BIABLE = register("lake_biable", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(CBlocks.BIABLE.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> LAKE_WHITE_TSUNE = register("lake_white_tsune", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(CBlocks.WHITE_TSUNE.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> LAKE_YELLOW_TSUNE = register("lake_yellow_tsune", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(CBlocks.YELLOW_TSUNE.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> LAKE_RED_TSUNE = register("lake_red_tsune", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(CBlocks.RED_TSUNE.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	public static final ConfiguredFeature<?, ?> LAKE_PURPLE_TSUNE = register("lake_purple_tsune", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(CBlocks.PURPLE_TSUNE.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	   

	public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature){
		CONFIGURED_FEATURES.add(Pair.of(name,configuredFeature));
		return configuredFeature;
	}
}
