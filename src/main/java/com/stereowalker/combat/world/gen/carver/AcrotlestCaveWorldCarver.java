package com.stereowalker.combat.world.gen.carver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.fluid.CFluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class AcrotlestCaveWorldCarver extends CaveWorldCarver {
	public AcrotlestCaveWorldCarver(Codec<ProbabilityConfig> p_i231917_1_) {
		super(p_i231917_1_, 128);
		this.carvableBlocks = ImmutableSet.of(CBlocks.PURIFIED_DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, CBlocks.PURIFIED_GRASS_BLOCK, CBlocks.MEZEPINE);
		this.carvableFluids = ImmutableSet.of(CFluids.BIABLE);
	}

	@Override
	protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomePos, BitSet p_225556_3_, Random p_225556_4_, BlockPos.Mutable p_225556_5_, BlockPos.Mutable p_225556_6_, BlockPos.Mutable p_225556_7_, int p_225556_8_, int p_225556_9_, int p_225556_10_, int p_225556_11_, int p_225556_12_, int p_225556_13_, int p_225556_14_, int p_225556_15_, MutableBoolean p_225556_16_) {
		int i = p_225556_13_ | p_225556_15_ << 4 | p_225556_14_ << 8;
		if (p_225556_3_.get(i)) {
			return false;
		} else {
			p_225556_3_.set(i);
			p_225556_5_.setPos(p_225556_11_, p_225556_14_, p_225556_12_);
			BlockState blockstate = chunk.getBlockState(p_225556_5_);
			BlockState blockstate1 = chunk.getBlockState(p_225556_6_.setPos(p_225556_5_).move(Direction.UP));
			if (blockstate.getBlock() == CBlocks.PURIFIED_GRASS_BLOCK || blockstate.getBlock() == Blocks.MYCELIUM) {
				p_225556_16_.setTrue();
			}

			if (!this.canCarveBlock(blockstate, blockstate1)) {
				return false;
			} else {
				if (p_225556_14_ > 0 && p_225556_14_ <= 2) {
					chunk.setBlockState(p_225556_5_, CBlocks.BLACK_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 2 && p_225556_14_ <= 4) {
					chunk.setBlockState(p_225556_5_, CBlocks.BLUE_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 4 && p_225556_14_ <= 6) {
					chunk.setBlockState(p_225556_5_, CBlocks.MAGENTA_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 6 && p_225556_14_ <= 8) {
					chunk.setBlockState(p_225556_5_, CBlocks.CYAN_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 8 && p_225556_14_ <= 10) {
					chunk.setBlockState(p_225556_5_, CBlocks.GRAY_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 10 && p_225556_14_ <= 12) {
					chunk.setBlockState(p_225556_5_, CBlocks.GREEN_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 12 && p_225556_14_ <= 14) {
					chunk.setBlockState(p_225556_5_, CBlocks.LIGHT_BLUE_TSUNE.getDefaultState(), false);
				} else if (p_225556_14_ > 14 && p_225556_14_ <= 16) {
					chunk.setBlockState(p_225556_5_, CBlocks.LIGHT_GRAY_TSUNE.getDefaultState(), false);
				} else {
					chunk.setBlockState(p_225556_5_, CAVE_AIR, false);
					if (p_225556_16_.isTrue()) {
						p_225556_7_.setPos(p_225556_5_).move(Direction.DOWN);
						if (chunk.getBlockState(p_225556_7_).getBlock() == CBlocks.PURIFIED_DIRT) {
							chunk.setBlockState(p_225556_7_, biomePos.apply(p_225556_5_).getGenerationSettings().getSurfaceBuilderConfig().getTop(), false);
						}
					}
				}

				return true;
			}
		}
	}
}