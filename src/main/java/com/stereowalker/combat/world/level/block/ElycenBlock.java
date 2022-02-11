package com.stereowalker.combat.world.level.block;

import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ElycenBlock extends GrassBlock {

	public ElycenBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = Blocks.GRASS.defaultBlockState();

		label48:
			for(int i = 0; i < 128; ++i) {
				BlockPos blockpos1 = blockpos;

				for(int j = 0; j < i / 16; ++j) {
					blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
					if (!worldIn.getBlockState(blockpos1.below()).is(this) || worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) {
						continue label48;
					}
				}

				BlockState blockstate2 = worldIn.getBlockState(blockpos1);
				if (blockstate2.is(blockstate.getBlock()) && rand.nextInt(10) == 0) {
					((BonemealableBlock)blockstate.getBlock()).performBonemeal(worldIn, rand, blockpos1, blockstate2);
				}

				if (blockstate2.isAir()) {
					BlockState blockstate1;
					if (rand.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> list = worldIn.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
						if (list.isEmpty()) {
							continue;
						}

						ConfiguredFeature<?, ?> configuredfeature = list.get(0);
						AbstractFlowerFeature flowersfeature = (AbstractFlowerFeature)configuredfeature.feature;
						blockstate1 = flowersfeature.getRandomFlower(rand, blockpos1, configuredfeature.config());
					} else {
						blockstate1 = blockstate;
					}

					if (blockstate1.canSurvive(worldIn, blockpos1)) {
						worldIn.setBlock(blockpos1, blockstate1, 3);
					}
				}
			}

	}

}
