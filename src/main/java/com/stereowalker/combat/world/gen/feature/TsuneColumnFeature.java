package com.stereowalker.combat.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TsuneColumnFeature extends Feature<NoFeatureConfig> {
	public TsuneColumnFeature(Codec<NoFeatureConfig> p_i51493_1_) {
		super(p_i51493_1_);
	}

	@Override
	public boolean generate(ISeedReader worldIn, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, NoFeatureConfig p_230362_6_) {
		Block genBlock = CBlocks.BROWN_TSUNE;
		int randomHeight = 7 + rand.nextInt(24);
		while(worldIn.isAirBlock(pos) && pos.getY() > 2) {
			pos = pos.down();
		}

		if (worldIn.getBlockState(pos).getBlock() != CBlocks.HOMSE) {
			return false;
		} else {
			for (int x = -3; x <= 3; x++) {
				for (int z = -3; z <= 3; z++) {
					for (int y = 0; y <= randomHeight; y++) {
						boolean flag;
						int z1 = Math.abs(z);
						int x1 = Math.abs(x);
						flag = (z1 >= 2 && x1 >= 2) && !(z1 == 2 && x1 == 2);
						if (!flag) {
							BlockState blockstate = worldIn.getBlockState(pos.up(y).north(z).west(x));
							Block block = blockstate.getBlock();
							if (blockstate.isAir(worldIn, pos.up(y).north(z).west(x)) || isDirt(block) || block == CBlocks.HOMSE || block == Blocks.ICE) {
								this.setBlockState(worldIn, pos.up(y).north(z).west(x), genBlock.getDefaultState());
							}
						}
					}
				}
			}
			return true;
		}
	}
}