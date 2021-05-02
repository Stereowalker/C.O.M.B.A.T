package com.stereowalker.combat.item;

import com.stereowalker.unionlib.UnionLib;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class AccessoryItemCheck {
	/**
	 * Confirms if this accessory is currently equipped by {@link player}
	 */
	@Deprecated
	public static boolean isEquipped(PlayerEntity player, Item accessory) {
		if (ModHelper.isCuriosLoaded()) {
			return CuriosApi.getCuriosHelper().findEquippedCurio(accessory, player).map(ringIn -> {
				return ringIn.getRight().getItem() == accessory;
				}).orElse(false);
		} else {
			if (UnionLib.getAccessoryInventory(player).getFirstRing().getItem() == accessory) return true;
			if (UnionLib.getAccessoryInventory(player).getSecondRing().getItem() == accessory) return true;
			else {
				for (int i = 0; i < 9; i++) {
					if (getOtherInventories(player, i).getItem() == accessory) return true;
				}
				return false;
			}
		}
	}
	
	public static ItemStack getOtherInventories(PlayerEntity player, int slot) {
		if (slot != 3 && slot != 6 && slot >= 0 && slot < 9) {
			return UnionLib.getAccessoryInventory(player).getStackInSlot(slot);
		}
		else return ItemStack.EMPTY;
	}

	/**
	 * Gets an ItemStack instance of the equipped accessory. Returns null when no the accessory is not equipped {@link player}
	 */
//	@Deprecated
//	public ItemStack getEquippedRing(PlayerEntity player) {
//		if (isEquipped(player)) {
//			if (Combat.isCuriosLoaded) {
//				return CuriosApi.getCuriosHelper().findEquippedCurio(accessoryType.getValidStack(), player).map(ringIn -> {return ringIn.getRight();}).orElse(ItemStack.EMPTY);
//			} else {
//				return player.inventory.getStackInSlot(9);
//			}
//		}
//		return ItemStack.EMPTY;
//	}
}
