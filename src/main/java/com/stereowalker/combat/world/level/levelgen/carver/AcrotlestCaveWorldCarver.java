package com.stereowalker.combat.world.level.levelgen.carver;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class AcrotlestCaveWorldCarver extends CaveWorldCarver {
	public AcrotlestCaveWorldCarver(Codec<CaveCarverConfiguration> p_159194_) {
		super(p_159194_);
		this.replaceableBlocks = ImmutableSet.of(CBlocks.PURIFIED_DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, CBlocks.PURIFIED_GRASS_BLOCK, CBlocks.MEZEPINE, Blocks.SANDSTONE, Blocks.GREEN_TERRACOTTA);
		this.liquids = ImmutableSet.of(CFluids.BIABLE);
	}

	@Override
	protected boolean carveBlock(CarvingContext pContext, CaveCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Biome> pBiomeAccessor, BitSet pCarvingMask, Random pRandom, BlockPos.MutableBlockPos pPos, BlockPos.MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
		BlockState blockstate = pChunk.getBlockState(pPos);
		BlockState blockstate1 = pChunk.getBlockState(pCheckPos.setWithOffset(pPos, Direction.UP));
		if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
			pReachedSurface.setTrue();
		}

		if (!this.canReplaceBlock(blockstate, blockstate1) && !isDebugEnabled(pConfig)) {
			return false;
		} else {
			BlockState blockstate2 = this.getCarveState(pContext, pConfig, pPos, pAquifer);
			if (blockstate2 == null) {
				return false;
			} else {
				if (pPos.getY() > 0 && pPos.getY() <= 2) {
					pChunk.setBlockState(pCheckPos, CBlocks.BLACK_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 2 && pPos.getY() <= 4) {
					pChunk.setBlockState(pCheckPos, CBlocks.BLUE_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 4 && pPos.getY() <= 6) {
					pChunk.setBlockState(pCheckPos, CBlocks.MAGENTA_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 6 && pPos.getY() <= 8) {
					pChunk.setBlockState(pCheckPos, CBlocks.CYAN_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 8 && pPos.getY() <= 10) {
					pChunk.setBlockState(pCheckPos, CBlocks.GRAY_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 10 && pPos.getY() <= 12) {
					pChunk.setBlockState(pCheckPos, CBlocks.GREEN_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 12 && pPos.getY() <= 14) {
					pChunk.setBlockState(pCheckPos, CBlocks.LIGHT_BLUE_TSUNE.defaultBlockState(), false);
				} else if (pPos.getY() > 14 && pPos.getY() <= 16) {
					pChunk.setBlockState(pCheckPos, CBlocks.LIGHT_GRAY_TSUNE.defaultBlockState(), false);
				} else {
					pChunk.setBlockState(pPos, blockstate2, false);
					if (pReachedSurface.isTrue()) {
						pCheckPos.setWithOffset(pPos, Direction.DOWN);
						if (pChunk.getBlockState(pCheckPos).is(Blocks.DIRT)) {
							pChunk.setBlockState(pCheckPos, pBiomeAccessor.apply(pPos).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial(), false);
						}
					}
				}

					return true;
				}
			}
		}

		//	@Override
		//	protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomePos, BitSet p_225556_3_, Random p_225556_4_, BlockPos.MutableBlockPos p_225556_5_, BlockPos.MutableBlockPos p_225556_6_, BlockPos.MutableBlockPos p_225556_7_, int p_225556_8_, int p_225556_9_, int p_225556_10_, int p_225556_11_, int p_225556_12_, int p_225556_13_, int p_225556_14_, int p_225556_15_, MutableBoolean p_225556_16_) {
		//		int i = p_225556_13_ | p_225556_15_ << 4 | p_225556_14_ << 8;
		//		if (p_225556_3_.get(i)) {
		//			return false;
		//		} else {
		//			p_225556_3_.set(i);
		//			p_225556_5_.setPos(p_225556_11_, p_225556_14_, p_225556_12_);
		//			BlockState blockstate = chunk.getBlockState(p_225556_5_);
		//			BlockState blockstate1 = chunk.getBlockState(p_225556_6_.setPos(p_225556_5_).move(Direction.UP));
		//			if (blockstate.getBlock() == CBlocks.PURIFIED_GRASS_BLOCK || blockstate.getBlock() == Blocks.MYCELIUM) {
		//				p_225556_16_.setTrue();
		//			}
		//
		//			if (!this.canCarveBlock(blockstate, blockstate1)) {
		//				return false;
		//			} else {
		//				if (p_225556_14_ > 0 && p_225556_14_ <= 2) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.BLACK_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 2 && p_225556_14_ <= 4) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.BLUE_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 4 && p_225556_14_ <= 6) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.MAGENTA_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 6 && p_225556_14_ <= 8) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.CYAN_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 8 && p_225556_14_ <= 10) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.GRAY_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 10 && p_225556_14_ <= 12) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.GREEN_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 12 && p_225556_14_ <= 14) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.LIGHT_BLUE_TSUNE.defaultBlockState(), false);
		//				} else if (p_225556_14_ > 14 && p_225556_14_ <= 16) {
		//					chunk.setBlockState(p_225556_5_, CBlocks.LIGHT_GRAY_TSUNE.defaultBlockState(), false);
		//				} else {
		//					chunk.setBlockState(p_225556_5_, CAVE_AIR, false);
		//					if (p_225556_16_.isTrue()) {
		//						p_225556_7_.setPos(p_225556_5_).move(Direction.DOWN);
		//						if (chunk.getBlockState(p_225556_7_).getBlock() == CBlocks.PURIFIED_DIRT) {
		//							chunk.setBlockState(p_225556_7_, biomePos.apply(p_225556_5_).getGenerationSettings().getSurfaceBuilderConfig().getTop(), false);
		//						}
		//					}
		//				}
		//
		//				return true;
		//			}
		//		}
		//	}
	}