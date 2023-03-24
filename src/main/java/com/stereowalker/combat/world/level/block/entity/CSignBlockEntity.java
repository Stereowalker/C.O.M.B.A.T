package com.stereowalker.combat.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CSignBlockEntity extends SignBlockEntity {

	public CSignBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(pWorldPosition, pBlockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return CBlockEntityType.SIGN;
	}
}
