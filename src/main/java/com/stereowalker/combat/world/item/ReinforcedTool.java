package com.stereowalker.combat.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public interface ReinforcedTool {
	Item getBaseItem();
	
	Ingredient getUpgradeItem();
}
