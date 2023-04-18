package com.stereowalker.combat.data.worldgen.features;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.AcrotlestStraightTrunkPlacer;
import com.stereowalker.combat.world.level.levelgen.feature.trunkplacers.MagicStraightTrunkPlacer;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class CTreeFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> AUSLDINE = FeatureUtils.createKey("combat:ausldine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_OAK = FeatureUtils.createKey("combat:dead_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> REZAL = FeatureUtils.createKey("combat:rezal");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MONORIS = FeatureUtils.createKey("combat:monoris");

	private static TreeConfiguration.TreeConfigurationBuilder createMagicStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new MagicStraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(CBlocks.CALTAS));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createAcrotlestStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new AcrotlestStraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(CBlocks.PURIFIED_DIRT));
	}
	
	public static void init() {}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> p_256317_) {
		HolderGetter<Block> holdergetter = p_256317_.lookup(Registries.BLOCK);
		FeatureUtils.register(p_256317_, AUSLDINE, CFeature.MAGIC_TREE, createMagicStraightBlobTree(CBlocks.AUSLDINE_LOG, CBlocks.AUSLDINE_LEAVES, 4, 2, 0, 2).ignoreVines().build());
		FeatureUtils.register(p_256317_, DEAD_OAK, Feature.TREE, TreeFeatures.createStraightBlobTree(CBlocks.DEAD_OAK_LOG, Blocks.AIR, 4, 2, 0, 2).ignoreVines().build());
		FeatureUtils.register(p_256317_, REZAL, CFeature.MAGIC_TREE, createMagicStraightBlobTree(CBlocks.REZAL_LOG, CBlocks.REZAL_LEAVES, 3, 5, 3, 2).ignoreVines().build());
		FeatureUtils.register(p_256317_, MONORIS, CFeature.ACROTLEST_TREE, createAcrotlestStraightBlobTree(CBlocks.MONORIS_LOG, CBlocks.MONORIS_LEAVES, 4, 2, 0, 2).ignoreVines().build());
	}
}
