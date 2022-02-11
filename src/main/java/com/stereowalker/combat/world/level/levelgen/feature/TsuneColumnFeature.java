package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TsuneColumnFeature extends Feature<NoneFeatureConfiguration> {
	public TsuneColumnFeature(Codec<NoneFeatureConfiguration> p_i51493_1_) {
		super(p_i51493_1_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
		BlockPos pos = p_159882_.origin();
		Random rand = p_159882_.random();

		WorldGenLevel worldIn;
		for(worldIn = p_159882_.level(); worldIn.isEmptyBlock(pos) && pos.getY() > worldIn.getMinBuildHeight() + 2; pos = pos.below()) {
		}
		
		Block genBlock = CBlocks.BROWN_TSUNE;
		int randomHeight = 7 + rand.nextInt(24);

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
							BlockState blockstate = worldIn.getBlockState(pos.above(y).north(z).west(x));
							if (blockstate.isAir() || isDirt(blockstate) || blockstate.is(CBlocks.HOMSE) || blockstate.is(Blocks.ICE)) {
								this.setBlock(worldIn, pos.above(y).north(z).west(x), genBlock.defaultBlockState());
							}
						}
					}
				}
			}
			return true;
		}
	}
}