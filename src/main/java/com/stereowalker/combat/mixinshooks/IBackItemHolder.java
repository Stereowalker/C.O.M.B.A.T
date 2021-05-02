package com.stereowalker.combat.mixinshooks;

import com.stereowalker.combat.inventory.ItemInventory;

public interface IBackItemHolder {
	public ItemInventory<?> getItemInventory();
	public void setItemInventory(ItemInventory<?> input);
}
