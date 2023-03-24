package com.stereowalker.combat.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyGeneratorBlockEntity extends AbstractEnergyContainerBlockEntity {
	protected AbstractEnergyGeneratorBlockEntity(BlockEntityType<?> typeIn, BlockPos pWorldPosition, BlockState pBlockState, int maxEnergyIn) {
		super(typeIn, pWorldPosition, pBlockState, maxEnergyIn);
	}

	//Forge Energy Methods
	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
	//
}