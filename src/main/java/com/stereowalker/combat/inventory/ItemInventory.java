package com.stereowalker.combat.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.item.InventoryItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public abstract class ItemInventory<T extends InventoryItem<?>> extends Inventory {
	public ItemInventory(int size) {
		super(size);
	}

	//	   public void openInventory(PlayerEntity player) {
	//	      if (this.associatedChest != null) {
	//	         this.associatedChest.openChest();
	//	      }
	//
	//	      super.openInventory(player);
	//	   }
	//
	//	   public void closeInventory(PlayerEntity player) {
	//	      if (this.associatedChest != null) {
	//	         this.associatedChest.closeChest();
	//	      }
	//
	//	      super.closeInventory(player);
	//	      this.associatedChest = null;
	//	   }

	public void read(ListNBT p_70486_1_) {
		for(int i = 0; i < this.getSizeInventory(); ++i) {
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		}

		for(int k = 0; k < p_70486_1_.size(); ++k) {
			CompoundNBT compoundnbt = p_70486_1_.getCompound(k);
			int j = compoundnbt.getByte("Slot") & 255;
			if (j >= 0 && j < this.getSizeInventory()) {
				this.setInventorySlotContents(j, ItemStack.read(compoundnbt));
			}
		}

	}

	public ListNBT write() {
		ListNBT listnbt = new ListNBT();

		for(int i = 0; i < this.getSizeInventory(); ++i) {
			ItemStack itemstack = this.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				CompoundNBT compoundnbt = new CompoundNBT();
				compoundnbt.putByte("Slot", (byte)i);
				itemstack.write(compoundnbt);
				listnbt.add(compoundnbt);
			}
		}

		return listnbt;
	}

	public int retrievalSlot() {
		return 0;
	}

	public ItemStack addItem(ItemStack stack, int slot) {
		ItemStack itemstack = stack.copy();
		this.mergeStack(itemstack, slot);
		if (itemstack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.putInInventory(itemstack, slot);
			return itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
		}
	}

	private void mergeStack(ItemStack p_223372_1_, int slot) {
		ItemStack itemstack = this.getStackInSlot(slot);
		if (this.func_233540_a_(itemstack, p_223372_1_)) {
			this.func_223373_a(p_223372_1_, itemstack);
			if (p_223372_1_.isEmpty()) {
				return;
			}
		}
	}

	private boolean func_233540_a_(ItemStack p_233540_1_, ItemStack p_233540_2_) {
		return p_233540_1_.getItem() == p_233540_2_.getItem() && ItemStack.areItemStackTagsEqual(p_233540_1_, p_233540_2_);
	}

	private void func_223373_a(ItemStack p_223373_1_, ItemStack p_223373_2_) {
		int i = Math.min(this.getInventoryStackLimit(), p_223373_2_.getMaxStackSize());
		int j = Math.min(p_223373_1_.getCount(), i - p_223373_2_.getCount());
		if (j > 0) {
			p_223373_2_.grow(j);
			p_223373_1_.shrink(j);
			this.markDirty();
		}

	}

	private void putInInventory(ItemStack p_223375_1_, int slot) {
		ItemStack itemstack = this.getStackInSlot(slot);
		if (itemstack.isEmpty()) {
			this.setInventorySlotContents(slot, p_223375_1_.copy());
			p_223375_1_.setCount(0);
			return;
		}
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean isUsableByPlayer(PlayerEntity player) {
		return player.getHeldItemMainhand().getItem() instanceof InventoryItem<?> || player.getHeldItemOffhand().getItem() instanceof InventoryItem<?> || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof InventoryItem<?>;
	}
}
