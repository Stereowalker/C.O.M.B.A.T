package com.stereowalker.combat.world.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class InventoryMenu extends AbstractContainerMenu {

	protected InventoryMenu(MenuType<?> type, int id) {
		super(type, id);
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return false;
	}

}
