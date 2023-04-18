package com.stereowalker.combat.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TsuneSpikeFeature extends Feature<NoneFeatureConfiguration> {
	public TsuneSpikeFeature(Codec<NoneFeatureConfiguration> p_i51493_1_) {
		super(p_i51493_1_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
		BlockPos pos = p_159882_.origin();
		RandomSource rand = p_159882_.random();

		WorldGenLevel worldIn;
		for(worldIn = p_159882_.level(); worldIn.isEmptyBlock(pos) && pos.getY() > worldIn.getMinBuildHeight() + 2; pos = pos.below()) {
		}

		Block genBlock;
		int rBlock = rand.nextInt(3);
		if (rBlock == 0) {
			genBlock = CBlocks.PINK_TSUNE;
		} else if (rBlock == 1) {
			genBlock = CBlocks.ORANGE_TSUNE;
		} else {
			genBlock = CBlocks.LIME_TSUNE;
		}

		if (worldIn.getBlockState(pos).getBlock() != CBlocks.PURIFIED_GRASS_BLOCK) {
			return false;
		} else {
			pos = pos.above(rand.nextInt(3));
			int i = rand.nextInt(4) + 7;
			int j = i / 4 + rand.nextInt(2);
			if (j > 1 && rand.nextInt(60) == 0) {
				pos = pos.above(10 + rand.nextInt(30));
			} else if (j > 1 && rand.nextInt(60) == 0) {
				pos = pos.above(5 + rand.nextInt(15));
			}

			for(int k = 0; k < i; ++k) {
				float f = (1.0F - (float)k / (float)i) * (float)j;
				int l = Mth.ceil(f);

				for(int i1 = -l; i1 <= l; ++i1) {
					float f1 = (float)Mth.abs(i1) - 0.25F;

					for(int j1 = -l; j1 <= l; ++j1) {
						float f2 = (float)Mth.abs(j1) - 0.25F;
						if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(rand.nextFloat() > 0.75F))) {
							BlockState blockstate = worldIn.getBlockState(pos.offset(i1, k, j1));
							if (blockstate.isAir() || isDirt(blockstate) || blockstate.is(CBlocks.PURIFIED_GRASS_BLOCK) || blockstate.is(Blocks.ICE)) {
								this.setBlock(worldIn, pos.offset(i1, k, j1), genBlock.defaultBlockState());
							}

							if (k != 0 && l > 1) {
								blockstate = worldIn.getBlockState(pos.offset(i1, -k, j1));
								if (blockstate.isAir() || isDirt(blockstate) || blockstate.is(CBlocks.PURIFIED_GRASS_BLOCK) || blockstate.is(Blocks.ICE)) {
									this.setBlock(worldIn, pos.offset(i1, -k, j1), genBlock.defaultBlockState());
								}
							}
						}
					}
				}
			}

			int k1 = j - 1;
			if (k1 < 0) {
				k1 = 0;
			} else if (k1 > 1) {
				k1 = 1;
			}

			for(int l1 = -k1; l1 <= k1; ++l1) {
				for(int i2 = -k1; i2 <= k1; ++i2) {
					BlockPos blockpos = pos.offset(l1, -1, i2);
					int j2 = 50;
					if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
						j2 = rand.nextInt(5);
					}

					while(blockpos.getY() > 50) {
						BlockState blockstate1 = worldIn.getBlockState(blockpos);
						if (!blockstate1.isAir() && !isDirt(blockstate1) && !blockstate1.is(CBlocks.PURIFIED_GRASS_BLOCK) && !blockstate1.is(Blocks.ICE) && !blockstate1.is(genBlock)) {
							break;
						}

						this.setBlock(worldIn, blockpos, genBlock.defaultBlockState());
						blockpos = blockpos.below();
						--j2;
						if (j2 <= 0) {
							blockpos = blockpos.below(rand.nextInt(5) + 1);
							j2 = rand.nextInt(5);
						}
					}
				}
			}

			return true;
		}
	}
}