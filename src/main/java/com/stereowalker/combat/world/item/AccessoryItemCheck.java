package com.stereowalker.combat.world.item;

import com.stereowalker.unionlib.entity.AccessorySlot;
import com.stereowalker.unionlib.entity.player.CustomInventoryGetter;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class AccessoryItemCheck {
	/**
	 * Confirms if this accessory is currently equipped by {@link player}
	 */
	@Deprecated
	public static boolean isEquipped(Player player, Item accessory) {
		if (ModHelper.isCuriosLoaded()) {
			return CuriosApi.getCuriosHelper().findEquippedCurio(accessory, player).map(ringIn -> {
				return ringIn.getRight().getItem() == accessory;
				}).orElse(false);
		} else {
			if (((CustomInventoryGetter)player).getUnionInventory().getAccessory(AccessorySlot.FINGER_1).getItem() == accessory) return true;
			else if (((CustomInventoryGetter)player).getUnionInventory().getAccessory(AccessorySlot.FINGER_2).getItem() == accessory) return true;
			else return false;
		}
	}
}
