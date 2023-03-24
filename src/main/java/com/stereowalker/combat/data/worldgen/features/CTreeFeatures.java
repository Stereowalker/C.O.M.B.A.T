package com.stereowalker.combat.data.worldgen.features;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.AcrotlestStraightTrunkPlacer;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.MagicStraightTrunkPlacer;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class CTreeFeatures {

	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> AUSLDINE = register("ausldine", CFeature.MAGIC_TREE, createMagicStraightBlobTree(CBlocks.AUSLDINE_LOG, CBlocks.AUSLDINE_LEAVES, 4, 2, 0, 2).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> DEAD_OAK = register("dead_oak", Feature.TREE, TreeFeatures.createStraightBlobTree(CBlocks.DEAD_OAK_LOG, Blocks.AIR, 4, 2, 0, 2).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> REZAL = register("rezal", CFeature.MAGIC_TREE, createMagicStraightBlobTree(CBlocks.AUSLDINE_LOG, CBlocks.AUSLDINE_LEAVES, 3, 5, 3, 2).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MONORIS = register("monoris", CFeature.ACROTLEST_TREE, createAcrotlestStraightBlobTree(CBlocks.MONORIS_LOG, CBlocks.MONORIS_LEAVES, 4, 2, 0, 2).ignoreVines().build());

	private static TreeConfiguration.TreeConfigurationBuilder createMagicStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new MagicStraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(CBlocks.CALTAS));
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createAcrotlestStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new AcrotlestStraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(CBlocks.PURIFIED_DIRT));
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String p_206489_, F p_206490_, FC p_206491_) {
		return FeatureUtils.register("combat:"+p_206489_, p_206490_, p_206491_);
	}
}
