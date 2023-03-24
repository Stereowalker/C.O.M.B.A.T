package com.stereowalker.combat.world.level.block.grower;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AusldineTreeGrower extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random randomIn, boolean largeHive) {
		return CTreeFeatures.AUSLDINE;
	}
}
