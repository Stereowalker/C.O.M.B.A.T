package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.world.item.QuiverItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class QuiverContainer extends ItemContainer<QuiverItem> {
	public QuiverContainer() {
		super(6);
	}

	@Override
	public boolean stillValid(Player player) {
		return player.getMainHandItem().getItem() instanceof QuiverItem || player.getOffhandItem().getItem() instanceof QuiverItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof QuiverItem;
	}

	public ItemStack getAttachedBow() {
		return getItem(0);
	}
}
