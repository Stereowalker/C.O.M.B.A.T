package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.world.item.InventoryItem;
import com.stereowalker.combat.world.level.block.CardboardBoxBlock;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class ItemInventorySlot extends Slot {
	ItemStack container;

	public ItemInventorySlot(ItemStack containerIn, Container inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.container = containerIn;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		boolean flag1 = (stack.getItem() instanceof InventoryItem);
		boolean flag2 = false;
		boolean flag3 = ((InventoryItem<?>)container.getItem()).isItemValidForPackage(stack, index);
		if (stack.getItem() instanceof BlockItem) {
			BlockItem block = (BlockItem) stack.getItem();
			flag2 = block.getBlock() instanceof ShulkerBoxBlock || block.getBlock() instanceof CardboardBoxBlock; 
		}
		return !flag1 && !flag2 && flag3;
	}

}
