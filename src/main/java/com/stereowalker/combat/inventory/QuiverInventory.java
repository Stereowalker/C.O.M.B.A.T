package com.stereowalker.combat.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.item.QuiverItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class QuiverInventory extends ItemInventory<QuiverItem> {
	public QuiverInventory() {
		super(6);
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean isUsableByPlayer(PlayerEntity player) {
		return player.getHeldItemMainhand().getItem() instanceof QuiverItem || player.getHeldItemOffhand().getItem() instanceof QuiverItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof QuiverItem;
	}
	
	public ItemStack getAttachedBow() {
		return getStackInSlot(0);
	}
}
