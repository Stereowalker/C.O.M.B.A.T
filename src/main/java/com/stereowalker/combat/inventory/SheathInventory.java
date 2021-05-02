package com.stereowalker.combat.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.item.QuiverItem;
import com.stereowalker.combat.item.SheathItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class SheathInventory extends ItemInventory<QuiverItem> {
	public SheathInventory() {
		super(1);
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean isUsableByPlayer(PlayerEntity player) {
		return player.getHeldItemMainhand().getItem() instanceof SheathItem || player.getHeldItemOffhand().getItem() instanceof SheathItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof SheathItem;
	}
	
	public ItemStack getSheathedSword() {
		return getStackInSlot(0);
	}
}
