package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.world.item.SheathItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SheathContainer extends ItemContainer<SheathItem> {
	public SheathContainer() {
		super(1);
	}

	@Override
	public boolean stillValid(Player player) {
		return player.getMainHandItem().getItem() instanceof SheathItem || player.getOffhandItem().getItem() instanceof SheathItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof SheathItem;
	}

	public ItemStack getSheathedSword() {
		return getItem(0);
	}
}
