package com.stereowalker.combat.data.worldgen.features;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MiscAcrotlestFeatures {
	static String n(String n) {return Combat.MODID+":"+n;}
	public static final Holder<ConfiguredFeature<SpringConfiguration, ?>> SPRING_BIABLE = FeatureUtils.register(n("spring_biable"), Feature.SPRING, new SpringConfiguration(CFluids.BIABLE.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, CBlocks.PURIFIED_DIRT, CBlocks.MEZEPINE, CBlocks.SLYAPHY)));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_WHITE_TSUNE = FeatureUtils.register(n("lake_white_tsune"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.WHITE_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_YELLOW_TSUNE = FeatureUtils.register(n("lake_yellow_tsune"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.YELLOW_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_RED_TSUNE = FeatureUtils.register(n("lake_red_tsune"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.RED_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_PURPLE_TSUNE = FeatureUtils.register(n("lake_purple_tsune"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(CBlocks.PURPLE_TSUNE.defaultBlockState()), BlockStateProvider.simple(CBlocks.MEZEPINE.defaultBlockState())));
	   
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> TSUNE_SPIKE = FeatureUtils.register(n("tsune_spike"), CFeature.TSUNE_SPIKE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> MAGENTA_TSUNE_COLUMN = FeatureUtils.register(n("magenta_tsune_column"), CFeature.MAGENTA_TSUNE_COLUMN);
}
