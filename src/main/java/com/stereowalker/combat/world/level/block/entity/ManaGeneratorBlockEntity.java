package com.stereowalker.combat.world.level.block.entity;


import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.inventory.ManaGeneratorMenu;
import com.stereowalker.combat.world.item.ManaOrbItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ManaGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity implements MenuProvider {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;

	public ManaGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.MANA_GENERATOR, pWorldPosition, pBlockState, 1000);
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	protected final ContainerData data = new ContainerData() {
		public int get(int index) {
			switch(index) {
			case 0:
				return ManaGeneratorBlockEntity.this.energy;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				ManaGeneratorBlockEntity.this.energy = value;
			}

		}

		public int getCount() {
			return 1;
		}
	};

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		ContainerHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		ContainerHelper.saveAllItems(compound, this.inventory);
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
		return new ManaGeneratorMenu(id, this, playerInventory, data);
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("container.mana_generator");
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent(this.hasCustomName() ? this.customName : "container.mana_generator");
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ManaGeneratorBlockEntity pBlockEntity) {
		if (!pLevel.isClientSide) {
			if (!pBlockEntity.isFull()) {
				if (pBlockEntity.inventory.get(0).getItem() instanceof ManaOrbItem) {
					if (EnergyUtils.getEnergy(pBlockEntity.inventory.get(0), EnergyUtils.EnergyType.MAGIC_ENERGY) > 0) {
						EnergyUtils.addEnergyToItem(pBlockEntity.inventory.get(0), -1, EnergyUtils.EnergyType.MAGIC_ENERGY);
						pBlockEntity.energy++;
						pBlockEntity.setChanged();
					}
				} else {
					pBlockEntity.setEnergy(pBlockEntity.getEnergyStored());
				}
			}
		}
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getItem(int index) {
		return this.inventory.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.inventory, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.inventory, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		this.inventory.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with AbstractContainerMenu
	 */
	@Override
	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.getBlockPos()) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 0.5D, (double)this.getBlockPos().getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clearContent() {
		this.inventory.clear();
	}
}