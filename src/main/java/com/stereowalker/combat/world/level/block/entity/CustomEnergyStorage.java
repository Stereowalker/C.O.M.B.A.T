package com.stereowalker.combat.world.level.block.entity;

import com.stereowalker.combat.Combat;

import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
	AbstractEnergyContainerBlockEntity te;

	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy, AbstractEnergyContainerBlockEntity te) {
		super(capacity, maxReceive, maxExtract, energy);
		this.te = te;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int rec = te.receiveEnergy(maxReceive, simulate);
		this.capacity = te.capacity;
		this.energy = te.energy;
		this.maxExtract = te.maxExtract;
		this.maxReceive = te.maxReceive;
		Combat.debug("Recieved "+maxReceive+", Energy Left = "+energy+" "+te.energy);
		return rec;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int ext = te.extractEnergy(maxExtract, simulate);
		this.capacity = te.capacity;
		this.energy = te.energy;
		this.maxExtract = te.maxExtract;
		this.maxReceive = te.maxReceive;
		Combat.debug("Extracted "+maxExtract+", Energy Left = "+energy+" "+te.energy);
		return ext;
	}

}
