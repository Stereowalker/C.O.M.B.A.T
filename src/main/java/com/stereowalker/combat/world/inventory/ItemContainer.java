package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.world.item.InventoryItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class ItemContainer<T extends InventoryItem<?>> extends SimpleContainer {
	public ItemContainer(int size) {
		super(size);
	}

	@Override
	public void fromTag(ListTag p_70486_1_) {
		for(int i = 0; i < this.getContainerSize(); ++i) {
			this.setItem(i, ItemStack.EMPTY);
		}

		for(int k = 0; k < p_70486_1_.size(); ++k) {
			CompoundTag compoundnbt = p_70486_1_.getCompound(k);
			int j = compoundnbt.getByte("Slot") & 255;
			if (j >= 0 && j < this.getContainerSize()) {
				this.setItem(j, ItemStack.of(compoundnbt));
			}
		}

	}

	@Override
	public ListTag createTag() {
		ListTag listnbt = new ListTag();

		for(int i = 0; i < this.getContainerSize(); ++i) {
			ItemStack itemstack = this.getItem(i);
			if (!itemstack.isEmpty()) {
				CompoundTag compoundnbt = new CompoundTag();
				compoundnbt.putByte("Slot", (byte)i);
				itemstack.save(compoundnbt);
				listnbt.add(compoundnbt);
			}
		}

		return listnbt;
	}

	@Override
	public boolean stillValid(Player player) {
		return player.getMainHandItem().getItem() instanceof InventoryItem<?> || player.getOffhandItem().getItem() instanceof InventoryItem<?> || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof InventoryItem<?>;
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
		ItemStack itemstack = this.getItem(slot);
		if (this.isSameItem(itemstack, p_223372_1_)) {
			this.moveItemsBetweenStacks(p_223372_1_, itemstack);
			if (p_223372_1_.isEmpty()) {
				return;
			}
		}
	}

	private boolean isSameItem(ItemStack p_233540_1_, ItemStack p_233540_2_) {
		return p_233540_1_.getItem() == p_233540_2_.getItem() && ItemStack.tagMatches(p_233540_1_, p_233540_2_);
	}

	private void moveItemsBetweenStacks(ItemStack p_223373_1_, ItemStack p_223373_2_) {
		int i = Math.min(this.getMaxStackSize(), p_223373_2_.getMaxStackSize());
		int j = Math.min(p_223373_1_.getCount(), i - p_223373_2_.getCount());
		if (j > 0) {
			p_223373_2_.grow(j);
			p_223373_1_.shrink(j);
			this.setChanged();
		}

	}

	private void putInInventory(ItemStack p_223375_1_, int slot) {
		ItemStack itemstack = this.getItem(slot);
		if (itemstack.isEmpty()) {
			this.setItem(slot, p_223375_1_.copy());
			p_223375_1_.setCount(0);
			return;
		}
	}
}
