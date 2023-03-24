package com.stereowalker.combat.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyConsumerBlockEntity extends AbstractEnergyContainerBlockEntity {
	protected AbstractEnergyConsumerBlockEntity(BlockEntityType<?> typeIn, BlockPos pWorldPosition, BlockState pBlockState, int maxEnergyIn) {
		super(typeIn, pWorldPosition, pBlockState, maxEnergyIn);
	}

	//Forge Energy Methods
	@Override
	public boolean canExtract() {
		return false;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
    //
}