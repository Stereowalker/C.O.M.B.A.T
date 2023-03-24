package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.SheathItem;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SheathMenu extends AbstractContainerMenu {
	private final SheathContainer inventory;
	private final ItemStack sheath;

	public SheathMenu(int id, Inventory playerInventory) {
		this(id, new SheathContainer(), new ItemStack(CItems.SHEATH), playerInventory);
	}

	public SheathMenu(int id, ItemStack sheath, Inventory playerInventory) {
		this(id, ((SheathItem)sheath.getItem()).loadInventory(sheath), sheath, playerInventory);
	}

	public SheathMenu(int id, SheathContainer sheathInventory, ItemStack stack, Inventory playerInventory) {
		super(CMenuType.SHEATH, id);
		this.inventory = sheathInventory;
		this.sheath = stack;
		sheathInventory.startOpen(playerInventory.player);
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

	@Override
	 public boolean stillValid(Player playerIn) {
		 ((SheathItem)sheath.getItem()).saveInventory(sheath, inventory);
		 return this.inventory.stillValid(playerIn);
	 }
	 

	 @Override
	 public void removed(Player playerIn) {
		 super.removed(playerIn);
		 if (sheath.getItem() instanceof SheathItem) {
			 ((SheathItem)sheath.getItem()).saveInventory(sheath, inventory);
		 }
	 }

	 @Override
	 public ItemStack quickMoveStack(Player playerIn, int index) {
		 ItemStack itemstack = ItemStack.EMPTY;
		 Slot slot = this.slots.get(index);
		 if (slot != null && slot.hasItem()) {
			 ItemStack itemstack1 = slot.getItem();
			 itemstack = itemstack1.copy();
			 if (index < this.inventory.getContainerSize()) {
				 if (!this.moveItemStackTo(itemstack1, this.inventory.getContainerSize(), this.slots.size(), true)) {
					 return ItemStack.EMPTY;
				 }
			 } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getContainerSize(), false)) {
				 return ItemStack.EMPTY;
			 }

			 if (itemstack1.isEmpty()) {
				 slot.set(ItemStack.EMPTY);
			 } else {
				 slot.setChanged();
			 }
		 }

		 return itemstack;
	 }
}