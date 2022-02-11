package com.stereowalker.combat.world.level.block.grower;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.data.worldgen.CFeatures;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class MonorisTreeGrower extends AbstractTreeGrower {
	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
		return CFeatures.MONORIS;
	}
}
