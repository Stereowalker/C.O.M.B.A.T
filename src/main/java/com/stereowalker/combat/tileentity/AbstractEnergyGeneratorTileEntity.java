package com.stereowalker.combat.tileentity;


import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractEnergyGeneratorTileEntity extends AbstractEnergyContainerTileEntity {
	protected AbstractEnergyGeneratorTileEntity(TileEntityType<?> typeIn, int maxEnergyIn) {
		super(typeIn, maxEnergyIn);
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