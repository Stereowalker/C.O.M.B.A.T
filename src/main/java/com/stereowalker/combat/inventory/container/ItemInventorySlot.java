package com.stereowalker.combat.inventory.container;

import com.stereowalker.combat.block.CardboardBoxBlock;
import com.stereowalker.combat.item.InventoryItem;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class ItemInventorySlot extends Slot {
	ItemStack container;

	public ItemInventorySlot(ItemStack containerIn, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.container = containerIn;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		boolean flag1 = (stack.getItem() instanceof InventoryItem);
		boolean flag2 = false;
		boolean flag3 = ((InventoryItem<?>)container.getItem()).isItemValidForPackage(stack, slotNumber);
		if (stack.getItem() instanceof BlockItem) {
			BlockItem block = (BlockItem) stack.getItem();
			flag2 = block.getBlock() instanceof ShulkerBoxBlock || block.getBlock() instanceof CardboardBoxBlock; 
		}
		return !flag1 && !flag2 && flag3;
	}

}
