package com.stereowalker.combat.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class ElectricFurnaceContainer extends AbstractElectricFurnaceContainer {
	public ElectricFurnaceContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
		super(CContainerType.ELECTRIC_FURNACE, IRecipeType.SMELTING, p_i50082_1_, p_i50082_2_);
	}

	public ElectricFurnaceContainer(int p_i50083_1_, PlayerInventory p_i50083_2_, IInventory p_i50083_3_, IIntArray p_i50083_4_, boolean isPowered) {
		super(CContainerType.ELECTRIC_FURNACE, IRecipeType.SMELTING, p_i50083_1_, p_i50083_2_, p_i50083_3_, p_i50083_4_);
	}

	@Override
	public RecipeBookCategory func_241850_m() {
		return null;
	}
}