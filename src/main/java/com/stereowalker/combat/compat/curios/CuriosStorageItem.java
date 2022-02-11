package com.stereowalker.combat.compat.curios;

import java.util.Random;

import com.stereowalker.combat.world.item.InventoryItem;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
public class CuriosStorageItem {
	static Random random = new Random();

	public static void putOrRetrieveItemInStorage(Player playerentity, InteractionHand hand) {
		if (CuriosCompat.getSlotsForType(playerentity, "back", 0).getItem() instanceof InventoryItem) {
			ItemStack inventoryStack = CuriosCompat.getSlotsForType(playerentity, "back", 0);
			InventoryItem<?> item = (InventoryItem<?>)inventoryStack.getItem();
			item.accessInventory(inventoryStack, (inventory) -> {
				ItemStack itemStack = playerentity.getItemInHand(hand);
				boolean putStackSuccess = false;

				if (itemStack.isEmpty() && item.canRetrieveItemsWithKeybind()) {
					if (!inventory.getItem(inventory.retrievalSlot()).isEmpty()) {
						playerentity.setItemInHand(hand, inventory.removeItemNoUpdate(inventory.retrievalSlot()));
					}
				}
				else if (!itemStack.isEmpty()) {
					ItemStack newStack = itemStack;
					for (int i = 0; i < inventory.getContainerSize(); i++) {
						if (!newStack.isEmpty() && item.isItemValid(newStack, i)){
							newStack = inventory.addItem(newStack, i);
							putStackSuccess = true;
						}
					}

					if (putStackSuccess) {
						playerentity.setItemInHand(hand, newStack);
						if (!playerentity.getItemInHand(hand).equals(newStack, true))
							playerentity.level.playSound((Player)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
					}
					else if (!item.isItemValid(itemStack, inventory.retrievalSlot()) && item.canRetrieveItemsWithKeybind()) {
						if (!inventory.getItem(inventory.retrievalSlot()).isEmpty()) {
							if (playerentity.getInventory().getFreeSlot() >= 0) {
								ItemStack newStack1 = itemStack.copy();
								playerentity.setItemInHand(hand, inventory.removeItemNoUpdate(inventory.retrievalSlot()));
								playerentity.addItem(newStack1);
								if (!newStack1.isEmpty()) playerentity.drop(newStack1, false);
							}
						}
					}
				}
			});
		}
	}
}
