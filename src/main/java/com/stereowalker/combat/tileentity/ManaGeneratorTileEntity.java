package com.stereowalker.combat.tileentity;


import com.stereowalker.combat.inventory.container.ManaGeneratorContainer;
import com.stereowalker.combat.item.ManaOrbItem;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ManaGeneratorTileEntity extends AbstractEnergyGeneratorTileEntity implements INamedContainerProvider, ITickableTileEntity {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;

	public ManaGeneratorTileEntity() {
		super(CTileEntityType.MANA_GENERATOR, 1000);
	}

	public boolean isEmpty() {
		for(ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	protected final IIntArray data = new IIntArray() {
		public int get(int index) {
			switch(index) {
			case 0:
				return ManaGeneratorTileEntity.this.energy;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				ManaGeneratorTileEntity.this.energy = value;
			}

		}

		public int size() {
			return 1;
		}
	};

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, this.inventory);
		return super.write(compound);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		return new ManaGeneratorContainer(id, this, playerInventory, data);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.mana_generator");
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(this.hasCustomName() ? this.customName : "container.mana_generator");
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public void tick() {
		if (!this.world.isRemote) {
			if (!this.isFull()) {
				if (inventory.get(0).getItem() instanceof ManaOrbItem) {
					if (EnergyUtils.getEnergy(inventory.get(0), EnergyType.MAGIC_ENERGY) > 0) {
						EnergyUtils.addEnergyToItem(inventory.get(0), -1, EnergyType.MAGIC_ENERGY);
						energy++;
						this.markDirty();
					}
				} else {
					this.setEnergy(getEnergyStored());
				}
			}
		}
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}
}