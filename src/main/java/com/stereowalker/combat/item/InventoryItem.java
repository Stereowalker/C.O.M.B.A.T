package com.stereowalker.combat.item;

import java.util.function.Consumer;

import com.stereowalker.combat.block.CardboardBoxBlock;
import com.stereowalker.combat.inventory.ItemInventory;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public abstract class InventoryItem<T extends ItemInventory<?>> extends Item {

	public InventoryItem(Properties properties) {
		super(properties);
	}

	public boolean isItemValid(ItemStack stack, int index) {
		boolean flag1 = (stack.getItem() instanceof InventoryItem);
		boolean flag2 = false;
		if (stack.getItem() instanceof BlockItem) {
			BlockItem block = (BlockItem) stack.getItem();
			flag2 = block.getBlock() instanceof ShulkerBoxBlock || block.getBlock() instanceof CardboardBoxBlock; 
		}
		return !flag1 && !flag2 && isItemValidForPackage(stack, index);
	}

	public boolean isItemValidForPackage(ItemStack stack, int index) {
		return true;
	}

	/**
	 * This only loads the inventory. This does not save it to the item
	 * @param stack
	 * @return
	 */
	public T loadInventory(ItemStack stack) {
		T inventory = getInventoryInstance();
		inventory.read(stack.getOrCreateTag().getList("Items", 10));
		return inventory;
	}

	/**
	 * This only loads the inventory. This does not save it to the item
	 * @param stack
	 * @return
	 */
	public T loadInventory(CompoundNBT compound) {
		T inventory = getInventoryInstance();
		if (compound.contains("tag", 10)) {
			inventory.read(compound.getCompound("tag").getList("Items", 10));
		}
		return inventory;
	}

	public void saveInventory(ItemStack stack, ItemInventory<?> inventory) {
		if (inventory != null) {
			stack
			.getOrCreateTag().put("Items", 
					inventory.write());
		}
	}

	/**
	 * Use to modify items in a inventory item without the need to call the load and save methods
	 * @param stack
	 * @param inventoryConsumer
	 */
	public void accessInventory(ItemStack stack, Consumer<T> inventoryConsumer) {
		T inventory = loadInventory(stack);
		inventoryConsumer.accept(inventory);
		saveInventory(stack, inventory);
	}

	public abstract T getInventoryInstance();

	public boolean canRetrieveItemsWithKeybind() {
		return false;
	}
}
