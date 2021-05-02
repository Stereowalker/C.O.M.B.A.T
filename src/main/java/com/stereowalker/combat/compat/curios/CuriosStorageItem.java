package com.stereowalker.combat.compat.curios;

import java.util.Random;

import com.stereowalker.combat.item.InventoryItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
public class CuriosStorageItem {
	static Random random = new Random();

	public static void putOrRetrieveItemInStorage(PlayerEntity playerentity, Hand hand) {
		if (CuriosCompat.getSlotsForType(playerentity, "back", 0).getItem() instanceof InventoryItem) {
			ItemStack inventoryStack = CuriosCompat.getSlotsForType(playerentity, "back", 0);
			InventoryItem<?> item = (InventoryItem<?>)inventoryStack.getItem();
			item.accessInventory(inventoryStack, (inventory) -> {
				ItemStack itemStack = playerentity.getHeldItem(hand);
				boolean putStackSuccess = false;

				if (itemStack.isEmpty() && item.canRetrieveItemsWithKeybind()) {
					if (!inventory.getStackInSlot(inventory.retrievalSlot()).isEmpty()) {
						playerentity.setHeldItem(hand, inventory.removeStackFromSlot(inventory.retrievalSlot()));
					}
				}
				else if (!itemStack.isEmpty()) {
					ItemStack newStack = itemStack;
					for (int i = 0; i < inventory.getSizeInventory(); i++) {
						if (!newStack.isEmpty() && item.isItemValid(newStack, i)){
							newStack = inventory.addItem(newStack, i);
							putStackSuccess = true;
						}
					}

					if (putStackSuccess) {
						playerentity.setHeldItem(hand, newStack);
						if (!playerentity.getHeldItem(hand).equals(newStack, true))
							playerentity.world.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
					}
					else if (!item.isItemValid(itemStack, inventory.retrievalSlot()) && item.canRetrieveItemsWithKeybind()) {
						if (!inventory.getStackInSlot(inventory.retrievalSlot()).isEmpty()) {
							if (playerentity.inventory.getFirstEmptyStack() >= 0) {
								ItemStack newStack1 = itemStack.copy();
								playerentity.setHeldItem(hand, inventory.removeStackFromSlot(inventory.retrievalSlot()));
								playerentity.addItemStackToInventory(newStack1);
								if (!newStack1.isEmpty()) playerentity.dropItem(newStack1, false);
							}
						}
					}
				}
			});
		}
	}
}
