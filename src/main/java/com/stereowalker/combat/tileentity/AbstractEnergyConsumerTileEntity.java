package com.stereowalker.combat.tileentity;


import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractEnergyConsumerTileEntity extends AbstractEnergyContainerTileEntity {
	protected AbstractEnergyConsumerTileEntity(TileEntityType<?> typeIn, int maxEnergyIn) {
		super(typeIn, maxEnergyIn);
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