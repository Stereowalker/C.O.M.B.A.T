package com.stereowalker.combat.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class PyraniteOreBlock extends Block {

	public PyraniteOreBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		activate(state, worldIn, pos);
		super.onBlockClicked(state, worldIn, pos, player);
	}
	
	/**
	 * Called when the given entity walks on this Block
	 */
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		activate(worldIn.getBlockState(pos), worldIn, pos);
		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		activate(state, worldIn, pos);
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	private static void activate(BlockState blockState, World world, BlockPos blockPos) {
		for(Direction direction : Direction.values()) {
			BlockPos blockPos1 = blockPos.offset(direction);
			BlockState blockstate = world.getBlockState(blockPos1);
			if(blockstate == Blocks.AIR.getDefaultState() && blockstate.isValidPosition(world, blockPos1)) {
				world.setBlockState(blockPos1, ((PyraniteFireBlock)CBlocks.PYRANITE_FIRE).getStateForPlacement(world, blockPos1), 11);
			}
		}
	}

	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? 10 + RANDOM.nextInt(5) : 0;
	}

}
