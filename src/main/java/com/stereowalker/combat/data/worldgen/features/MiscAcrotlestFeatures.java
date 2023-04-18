package com.stereowalker.combat.data.worldgen.features;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MiscAcrotlestFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_BIABLE = FeatureUtils.createKey("combat:spring_biable");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_WHITE_TSUNE = FeatureUtils.createKey("combat:lake_white_tsune");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_YELLOW_TSUNE = FeatureUtils.createKey("combat:lake_yellow_tsune");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_RED_TSUNE = FeatureUtils.createKey("combat:lake_red_tsune");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_PURPLE_TSUNE = FeatureUtils.createKey("combat:lake_purple_tsune");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TSUNE_SPIKE = FeatureUtils.createKey("combat:tsune_spike");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MAGENTA_TSUNE_COLUMN = FeatureUtils.createKey("combat:magenta_tsune_column");
	
	public static void init() {}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> p_256346_) {
		FeatureUtils.register(p_256346_, LAKE_WHITE_TSUNE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.WHITE_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
		FeatureUtils.register(p_256346_, LAKE_YELLOW_TSUNE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.YELLOW_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
		FeatureUtils.register(p_256346_, LAKE_RED_TSUNE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.RED_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
		FeatureUtils.register(p_256346_, LAKE_PURPLE_TSUNE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.PURPLE_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
		FeatureUtils.register(p_256346_, SPRING_BIABLE, Feature.SPRING, new SpringConfiguration(CFluids.BIABLE.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, CBlocks.PURIFIED_DIRT, CBlocks.MEZEPINE, CBlocks.SLYAPHY)));
		FeatureUtils.register(p_256346_, TSUNE_SPIKE, CFeature.TSUNE_SPIKE);
		FeatureUtils.register(p_256346_, MAGENTA_TSUNE_COLUMN, CFeature.MAGENTA_TSUNE_COLUMN);
	}
}
