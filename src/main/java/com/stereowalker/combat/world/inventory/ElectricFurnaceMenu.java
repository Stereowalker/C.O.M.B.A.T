package com.stereowalker.combat.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class ElectricFurnaceMenu extends AbstractElectricFurnaceMenu {
	public ElectricFurnaceMenu(int p_i50082_1_, Inventory p_i50082_2_) {
		super(CMenuType.ELECTRIC_FURNACE, RecipeType.SMELTING, p_i50082_1_, p_i50082_2_);
	}

	public ElectricFurnaceMenu(int p_i50083_1_, Inventory p_i50083_2_, Container p_i50083_3_, ContainerData p_i50083_4_, boolean isPowered) {
		super(CMenuType.ELECTRIC_FURNACE, RecipeType.SMELTING, p_i50083_1_, p_i50083_2_, p_i50083_3_, p_i50083_4_);
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return null;
	}
}