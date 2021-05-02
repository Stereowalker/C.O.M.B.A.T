package com.stereowalker.combat.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TsuneSpikeFeature extends Feature<NoFeatureConfig> {
	public TsuneSpikeFeature(Codec<NoFeatureConfig> p_i51493_1_) {
		super(p_i51493_1_);
	}

	@Override
	public boolean generate(ISeedReader worldIn, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, NoFeatureConfig p_230362_6_) {
		Block genBlock;
		int rBlock = rand.nextInt(3);
		if (rBlock == 0) {
			genBlock = CBlocks.PINK_TSUNE;
		} else if (rBlock == 1) {
			genBlock = CBlocks.ORANGE_TSUNE;
		} else {
			genBlock = CBlocks.LIME_TSUNE;
		}
		while(worldIn.isAirBlock(pos) && pos.getY() > 2) {
			pos = pos.down();
		}

		if (worldIn.getBlockState(pos).getBlock() != CBlocks.PURIFIED_GRASS_BLOCK) {
			return false;
		} else {
			pos = pos.up(rand.nextInt(3));
			int i = rand.nextInt(4) + 7;
			int j = i / 4 + rand.nextInt(2);
			if (j > 1 && rand.nextInt(60) == 0) {
				pos = pos.up(10 + rand.nextInt(30));
			} else if (j > 1 && rand.nextInt(60) == 0) {
				pos = pos.up(5 + rand.nextInt(15));
			}

			for(int k = 0; k < i; ++k) {
				float f = (1.0F - (float)k / (float)i) * (float)j;
				int l = MathHelper.ceil(f);

				for(int i1 = -l; i1 <= l; ++i1) {
					float f1 = (float)MathHelper.abs(i1) - 0.25F;

					for(int j1 = -l; j1 <= l; ++j1) {
						float f2 = (float)MathHelper.abs(j1) - 0.25F;
						if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(rand.nextFloat() > 0.75F))) {
							BlockState blockstate = worldIn.getBlockState(pos.add(i1, k, j1));
							Block block = blockstate.getBlock();
							if (blockstate.isAir(worldIn, pos.add(i1, k, j1)) || isDirt(block) || block == CBlocks.PURIFIED_GRASS_BLOCK || block == Blocks.ICE) {
								this.setBlockState(worldIn, pos.add(i1, k, j1), genBlock.getDefaultState());
							}

							if (k != 0 && l > 1) {
								blockstate = worldIn.getBlockState(pos.add(i1, -k, j1));
								block = blockstate.getBlock();
								if (blockstate.isAir(worldIn, pos.add(i1, -k, j1)) || isDirt(block) || block == CBlocks.PURIFIED_GRASS_BLOCK || block == Blocks.ICE) {
									this.setBlockState(worldIn, pos.add(i1, -k, j1), genBlock.getDefaultState());
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
					BlockPos blockpos = pos.add(l1, -1, i2);
					int j2 = 50;
					if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
						j2 = rand.nextInt(5);
					}

					while(blockpos.getY() > 50) {
						BlockState blockstate1 = worldIn.getBlockState(blockpos);
						Block block1 = blockstate1.getBlock();
						if (!blockstate1.isAir(worldIn, blockpos) && !isDirt(block1) && block1 != CBlocks.PURIFIED_GRASS_BLOCK && block1 != Blocks.ICE && block1 != genBlock) {
							break;
						}

						this.setBlockState(worldIn, blockpos, genBlock.getDefaultState());
						blockpos = blockpos.down();
						--j2;
						if (j2 <= 0) {
							blockpos = blockpos.down(rand.nextInt(5) + 1);
							j2 = rand.nextInt(5);
						}
					}
				}
			}

			return true;
		}
	}
}