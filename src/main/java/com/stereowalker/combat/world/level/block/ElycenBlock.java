package com.stereowalker.combat.world.level.block;

import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ElycenBlock extends GrassBlock {

	public ElycenBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = Blocks.GRASS.defaultBlockState();

		label48:
			for(int i = 0; i < 128; ++i) {
				BlockPos blockpos1 = blockpos;

				for(int j = 0; j < i / 16; ++j) {
					blockpos1 = blockpos1.offset(pRand.nextInt(3) - 1, (pRand.nextInt(3) - 1) * pRand.nextInt(3) / 2, pRand.nextInt(3) - 1);
					if (!pLevel.getBlockState(blockpos1.below()).is(this) || pLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(pLevel, blockpos1)) {
						continue label48;
					}
				}

				BlockState blockstate2 = pLevel.getBlockState(blockpos1);
				if (blockstate2.is(blockstate.getBlock()) && pRand.nextInt(10) == 0) {
					((BonemealableBlock)blockstate.getBlock()).performBonemeal(pLevel, pRand, blockpos1, blockstate2);
				}

				if (blockstate2.isAir()) {
					Holder<PlacedFeature> holder;
		            if (pRand.nextInt(8) == 0) {
		               List<ConfiguredFeature<?, ?>> list = pLevel.getBiome(blockpos1).value().getGenerationSettings().getFlowerFeatures();
		               if (list.isEmpty()) {
		                  continue;
		               }

		               holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
		            } else {
		               holder = VegetationPlacements.GRASS_BONEMEAL;
		            }

		            holder.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRand, blockpos1);
				}
			}

	}

}
