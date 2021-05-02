package com.stereowalker.combat.inventory.container;

import javax.annotation.Nullable;

import com.stereowalker.combat.inventory.ItemInventory;
import com.stereowalker.combat.item.InventoryItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public abstract class BackItemContainer<Item extends InventoryItem<ItemInventory<?>>, Inv extends ItemInventory<?>> extends Container {
	private final Inv inventory;
	private final ItemStack backpack;

//		public BackItemContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory) {
//			this(id, new ItemInv, ItemStack.EMPTY, playerInventory);
//		}

	@SuppressWarnings("unchecked")
	public BackItemContainer(@Nullable ContainerType<?> type, int id, ItemStack backpack, PlayerInventory playerInventory) {
		this(type, id, (Inv) ((Item)backpack.getItem()).loadInventory(backpack), backpack, playerInventory);
	}

	public BackItemContainer(@Nullable ContainerType<?> type, int id, Inv backpackInventory, ItemStack stack, PlayerInventory playerInventory) {
		super(type, id);
		this.inventory = backpackInventory;
		this.backpack = stack;
		backpackInventory.openInventory(playerInventory.player);
		for(int j1 = 0; j1 < 9; ++j1) {
			this.addSlot(new ItemInventorySlot(stack, backpackInventory, j1, 8 + j1 * 18, 17));
		}

		for(int i1 = 0; i1 < 3; ++i1) {
			for(int k1 = 0; k1 < 9; ++k1) {
				this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 48 + i1 * 18));
			}
		}

		for(int j1 = 0; j1 < 9; ++j1) {
			this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 106));
		}

	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@SuppressWarnings("unchecked")
	public boolean canInteractWith(PlayerEntity playerIn) {
		((Item)backpack.getItem()).saveInventory(backpack, inventory);
		return this.inventory.isUsableByPlayer(playerIn);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		((Item)backpack.getItem()).saveInventory(backpack, inventory);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < this.inventory.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}