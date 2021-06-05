package com.stereowalker.combat.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

public class ElycenBlock extends GrassBlock {

	public ElycenBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.up();
		BlockState blockstate = Blocks.GRASS.getDefaultState();

		label48:
			for(int i = 0; i < 128; ++i) {
				BlockPos blockpos1 = blockpos;

				for(int j = 0; j < i / 16; ++j) {
					blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
					if (!worldIn.getBlockState(blockpos1.down()).matchesBlock(this) || worldIn.getBlockState(blockpos1).hasOpaqueCollisionShape(worldIn, blockpos1)) {
						continue label48;
					}
				}

				BlockState blockstate2 = worldIn.getBlockState(blockpos1);
				if (blockstate2.matchesBlock(blockstate.getBlock()) && rand.nextInt(10) == 0) {
					((IGrowable)blockstate.getBlock()).grow(worldIn, rand, blockpos1, blockstate2);
				}

				if (blockstate2.isAir()) {
					BlockState blockstate1;
					if (rand.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> list = worldIn.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
						if (list.isEmpty()) {
							continue;
						}

						ConfiguredFeature<?, ?> configuredfeature = list.get(0);
						FlowersFeature flowersfeature = (FlowersFeature)configuredfeature.feature;
						blockstate1 = flowersfeature.getFlowerToPlace(rand, blockpos1, configuredfeature.getConfig());
					} else {
						blockstate1 = blockstate;
					}

					if (blockstate1.isValidPosition(worldIn, blockpos1)) {
						worldIn.setBlockState(blockpos1, blockstate1, 3);
					}
				}
			}

	}

}
