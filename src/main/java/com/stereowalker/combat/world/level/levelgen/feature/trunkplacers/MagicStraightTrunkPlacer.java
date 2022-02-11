package com.stereowalker.combat.world.level.levelgen.feature.trunkplacers;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class MagicStraightTrunkPlacer extends TrunkPlacer {
	public static final Codec<MagicStraightTrunkPlacer> CODEC = RecordCodecBuilder.create((builderInstance) -> {
		return trunkPlacerParts(builderInstance).apply(builderInstance, MagicStraightTrunkPlacer::new);
	});

	public MagicStraightTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}

	protected TrunkPlacerType<?> type() {
		return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
	}

	private static boolean isDirt(LevelSimulatedReader pLevel, BlockPos pPos) {
		return pLevel.isStateAtPosition(pPos, (p_70304_) -> {
			return p_70304_.is(CBlocks.CALTAS) && !p_70304_.is(CBlocks.ELYCEN_BLOCK);
		});
	}

	protected static void setDirtAt(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, Random pRandom, BlockPos pPos, TreeConfiguration pConfig) {
		if (pConfig.forceDirt || !isDirt(pLevel, pPos)) {
			pBlockSetter.accept(pPos, pConfig.dirtProvider.getState(pRandom, pPos));
		}

	}

	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader p_161859_, BiConsumer<BlockPos, BlockState> p_161860_, Random p_161861_, int p_161862_, BlockPos p_161863_, TreeConfiguration p_161864_) {
		setDirtAt(p_161859_, p_161860_, p_161861_, p_161863_.below(), p_161864_);

		for(int i = 0; i < p_161862_; ++i) {
			placeLog(p_161859_, p_161860_, p_161861_, p_161863_.above(i), p_161864_);
		}

		return ImmutableList.of(new FoliagePlacer.FoliageAttachment(p_161863_.above(p_161862_), 0, false));
	}
}