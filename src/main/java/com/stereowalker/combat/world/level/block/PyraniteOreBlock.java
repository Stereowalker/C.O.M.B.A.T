package com.stereowalker.combat.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PyraniteOreBlock extends OreBlock {

	public PyraniteOreBlock(Properties properties) {
		super(properties, UniformInt.of(10, 15));
	}
	
	@Override
	public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		activate(pState, pLevel, pPos);
	}
	
	@Override
	public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
		activate(pState, pLevel, pPos);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		activate(pState, pLevel, pPos);
		return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
	}

	private static void activate(BlockState blockState, Level world, BlockPos blockPos) {
		for(Direction direction : Direction.values()) {
			BlockPos blockPos1 = blockPos.relative(direction);
			BlockState blockstate = world.getBlockState(blockPos1);
			if(blockstate == Blocks.AIR.defaultBlockState() && blockstate.canSurvive(world, blockPos1)) {
				BlockState fire = ((PyraniteFireBlock)CBlocks.PYRANITE_FIRE).getStateForPlacement(world, blockPos1);
				world.setBlock(blockPos1, fire, 11);
			}
		}
	}

}
