package com.stereowalker.combat.inventory.container;

import com.stereowalker.combat.inventory.SheathInventory;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.SheathItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SheathContainer extends Container {
	private final SheathInventory inventory;
	private final ItemStack sheath;

	public SheathContainer(int id, PlayerInventory playerInventory) {
		this(id, new SheathInventory(), new ItemStack(CItems.SHEATH), playerInventory);
	}

	public SheathContainer(int id, ItemStack sheath, PlayerInventory playerInventory) {
		this(id, ((SheathItem)sheath.getItem()).loadInventory(sheath), sheath, playerInventory);
	}

	public SheathContainer(int id, SheathInventory sheathInventory, ItemStack stack, PlayerInventory playerInventory) {
		super(CContainerType.SHEATH, id);
		this.inventory = sheathInventory;
		this.sheath = stack;
		sheathInventory.openInventory(playerInventory.player);
		for(int j1 = 0; j1 < 1; ++j1) {
			this.addSlot(new ItemInventorySlot(sheath, sheathInventory, j1, 8 + 4 * 18, 17));
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
		 ((SheathItem)sheath.getItem()).saveInventory(sheath, inventory);
		 return this.inventory.isUsableByPlayer(playerIn);
	 }
	 

	 @Override
	 public void onContainerClosed(PlayerEntity playerIn) {
		 super.onContainerClosed(playerIn);
		 if (sheath.getItem() instanceof SheathItem) {
			 ((SheathItem)sheath.getItem()).saveInventory(sheath, inventory);
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