package com.stereowalker.combat.world.level.block.entity;


import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class AbstractEnergyContainerBlockEntity extends BaseContainerBlockEntity implements IEnergyStorage {
	protected int capacity;
	protected int energy;
    protected int maxReceive;
    protected int maxExtract;
	protected CustomEnergyStorage energyStorage = new CustomEnergyStorage(this.capacity, this.canReceive()?1:0, this.canExtract()?1:0, this.energy, this);
	protected AbstractEnergyContainerBlockEntity(BlockEntityType<?> typeIn, BlockPos pWorldPosition, BlockState pBlockState, int maxEnergyIn) {
		super(typeIn, pWorldPosition, pBlockState);
		this.capacity = maxEnergyIn;
		this.maxExtract = 1;
		this.maxReceive = 1;
	}


	//Forge Energy Methods
	@Override
	public int getEnergyStored() {
		return this.energy;
	}
	
	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

	private LazyOptional<?> energyHandler = LazyOptional.of(() -> createEnergyHandler());
	protected IEnergyStorage createEnergyHandler() {
		return energyStorage;
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityEnergy.ENERGY)
			return energyHandler.cast();
		return super.getCapability(cap, side);
	}
    //
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public boolean isDrained() {
		return this.getEnergyStored() <= 0;
	}
	
	public boolean isFull() {
		return this.getEnergyStored() >= getMaxEnergyStored();
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (compound.contains("Energy")) {
			this.energy = compound.getInt("Energy");
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putInt("Energy", this.energy);
	}
}