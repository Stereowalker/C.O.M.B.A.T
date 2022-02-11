package com.stereowalker.combat.world.item;

import java.util.function.Consumer;

import com.stereowalker.combat.world.inventory.ItemContainer;
import com.stereowalker.combat.world.level.block.CardboardBoxBlock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public abstract class InventoryItem<T extends ItemContainer<?>> extends Item {

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
		inventory.fromTag(stack.getOrCreateTag().getList("Items", 10));
		return inventory;
	}

	/**
	 * This only loads the inventory. This does not save it to the item
	 * @param stack
	 * @return
	 */
	public T loadInventory(CompoundTag compound) {
		T inventory = getInventoryInstance();
		if (compound.contains("tag", 10)) {
			inventory.fromTag(compound.getCompound("tag").getList("Items", 10));
		}
		return inventory;
	}

	public void saveInventory(ItemStack stack, ItemContainer<?> inventory) {
		if (inventory != null) {
			stack
			.getOrCreateTag().put("Items", 
					inventory.createTag());
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
