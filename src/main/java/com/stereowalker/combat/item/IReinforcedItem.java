package com.stereowalker.combat.item;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public interface IReinforcedItem {
	Item getBaseItem();
	
	Ingredient getUpgradeItem();
}
