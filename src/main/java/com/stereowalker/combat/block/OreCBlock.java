package com.stereowalker.combat.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class OreCBlock extends Block {
	public OreCBlock(Properties properties) {
		super(properties);
	}

	protected int getExperience(Random random) {
		if (this == CBlocks.RUBY_ORE) {
			return MathHelper.nextInt(random, 3, 7);
		} else if (this == CBlocks.MAGIC_STONE) {
			return MathHelper.nextInt(random, 11, 23);
		} else if (this == CBlocks.YELLOW_MAGIC_CLUSTER) {
			return MathHelper.nextInt(random, 5, 9);
			//	      } else if (this == Blocks.LAPIS_ORE) {
			//	         return MathHelper.nextInt(p_220281_1_, 2, 5);
		} else {
			return this == CBlocks.TRIDOX_ORE ? MathHelper.nextInt(random, 5, 10) : 0;
		}
	}

	/**
	 * Spawn additional block drops such as experience or other entities
	 */
	@SuppressWarnings("deprecation")
	public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, worldIn, pos, stack);
	}

	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.getExperience(RANDOM) : 0;
	}
}