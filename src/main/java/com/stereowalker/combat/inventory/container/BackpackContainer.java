package com.stereowalker.combat.inventory.container;

import com.stereowalker.combat.inventory.BackpackInventory;
import com.stereowalker.combat.item.BackpackItem;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class BackpackContainer extends Container {
	private final BackpackInventory inventory;
	private final ItemStack backpack;

	public BackpackContainer(int id, PlayerInventory playerInventory) {
		this(id, new BackpackInventory(), new ItemStack(CItems.BACKPACK), playerInventory);
	}

	public BackpackContainer(int id, ItemStack backpack, PlayerInventory playerInventory) {
		this(id, ((BackpackItem)backpack.getItem()).loadInventory(backpack), backpack, playerInventory);
	}

	public BackpackContainer(int id, BackpackInventory backpackInventory, ItemStack stack, PlayerInventory playerInventory) {
		super(CContainerType.BACKPACK, id);
		this.inventory = backpackInventory;
		this.backpack = stack;
		backpackInventory.openInventory(playerInventory.player);
		for(int j1 = 0; j1 < 9; ++j1) {
			this.addSlot(new ItemInventorySlot(backpack, backpackInventory, j1, 8 + j1 * 18, 17));
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
	 public boolean canInteractWith(PlayerEntity playerIn) {
		 ((BackpackItem)backpack.getItem()).saveInventory(backpack, inventory);
		 return this.inventory.isUsableByPlayer(playerIn);
	 }
	 

	 @Override
	 public void onContainerClosed(PlayerEntity playerIn) {
		 super.onContainerClosed(playerIn);
		 if (backpack.getItem() instanceof BackpackItem) {
			 ((BackpackItem)backpack.getItem()).saveInventory(backpack, inventory);
		 }
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