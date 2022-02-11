package com.stereowalker.combat.world.inventory;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.item.InventoryItem;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class BackItemContainer<Item extends InventoryItem<ItemContainer<?>>, Inv extends ItemContainer<?>> extends AbstractContainerMenu {
	private final Inv inventory;
	private final ItemStack backpack;

//		public BackItemContainer(@Nullable MenuType<?> type, int id, Inventory playerInventory) {
//			this(id, new ItemInv, ItemStack.EMPTY, playerInventory);
//		}

	@SuppressWarnings("unchecked")
	public BackItemContainer(@Nullable MenuType<?> type, int id, ItemStack backpack, Inventory playerInventory) {
		this(type, id, (Inv) ((Item)backpack.getItem()).loadInventory(backpack), backpack, playerInventory);
	}

	public BackItemContainer(@Nullable MenuType<?> type, int id, Inv backpackInventory, ItemStack stack, Inventory playerInventory) {
		super(type, id);
		this.inventory = backpackInventory;
		this.backpack = stack;
		backpackInventory.startOpen(playerInventory.player);
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

	@Override
	@SuppressWarnings("unchecked")
	public boolean stillValid(Player playerIn) {
		((Item)backpack.getItem()).saveInventory(backpack, inventory);
		return this.inventory.stillValid(playerIn);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		((Item)backpack.getItem()).saveInventory(backpack, inventory);
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