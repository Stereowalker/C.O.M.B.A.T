package com.stereowalker.combat.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class MagicSaplingBlock extends SaplingBlock {

	public MagicSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
		super(pTreeGrower, pProperties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == CBlocks.ELYCEN_BLOCK || block == CBlocks.CALTAS;
	}
}