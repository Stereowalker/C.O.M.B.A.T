package com.stereowalker.combat.world.level.block.grower;

import javax.annotation.Nullable;

import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MonorisTreeGrower extends AbstractTreeGrower {
	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
		return CTreeFeatures.MONORIS;
	}
}
