package com.stereowalker.combat.block.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.gen.feature.CFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class MonorisTree extends Tree {
	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
		return CFeatures.MONORIS;
	}
}
