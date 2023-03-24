package com.stereowalker.combat.mixinshooks;

import com.stereowalker.combat.world.inventory.ItemContainer;

public interface IBackItemHolder {
	public ItemContainer<?> getItemInventory();
	public void setItemInventory(ItemContainer<?> input);
}
