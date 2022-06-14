package com.stereowalker.combat.world.level.levelgen.carver;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CarvingContext;

public class AcrotlestCanyonWorldCarver extends CanyonWorldCarver {
	public AcrotlestCanyonWorldCarver(Codec<CanyonCarverConfiguration> p_64711_) {
		super(p_64711_);
		this.replaceableBlocks = ImmutableSet.of(CBlocks.PURIFIED_DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, CBlocks.PURIFIED_GRASS_BLOCK, CBlocks.MEZEPINE, CBlocks.SLYAPHY, Blocks.GREEN_TERRACOTTA);
		this.liquids = ImmutableSet.of(CFluids.BIABLE);
	}

	@Nullable
	public BlockState getCarveState(CarvingContext pContext, CanyonCarverConfiguration pConfig, BlockPos pPos, Aquifer pAquifer) {
		if (pPos.getY() > 0 && pPos.getY() <= 2) {
			return CBlocks.BLACK_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 2 && pPos.getY() <= 4) {
			return CBlocks.BLUE_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 4 && pPos.getY() <= 6) {
			return CBlocks.MAGENTA_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 6 && pPos.getY() <= 8) {
			return CBlocks.CYAN_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 8 && pPos.getY() <= 10) {
			return CBlocks.GRAY_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 10 && pPos.getY() <= 12) {
			return CBlocks.GREEN_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 12 && pPos.getY() <= 14) {
			return CBlocks.LIGHT_BLUE_TSUNE.defaultBlockState();
		} else if (pPos.getY() > 14 && pPos.getY() <= 16) {
			return CBlocks.LIGHT_GRAY_TSUNE.defaultBlockState();
		} else {
			return super.getCarveState(pContext, pConfig, pPos, pAquifer);
		}
	}
}