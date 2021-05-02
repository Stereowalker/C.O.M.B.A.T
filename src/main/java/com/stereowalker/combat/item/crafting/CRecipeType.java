package com.stereowalker.combat.item.crafting;

import com.stereowalker.combat.Combat;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class CRecipeType {
	public static final IRecipeType<WoodcuttingRecipe> WOODCUTTING = register("woodcutting");
	public static final IRecipeType<IFletchingRecipe> FLETCHING = register("fletching");
	public static final IRecipeType<AbstractAlloyFurnaceRecipe> ALLOY_SMELTING = register("alloy_smelting");
	public static final IRecipeType<AbstractArcaneWorkbenchRecipe> ARCANE_CONVERSION = register("arcane_conversion");
	
	public static <T extends IRecipe<?>> IRecipeType<T> register(String name){
		return IRecipeType.register(Combat.getInstance().locationString(name));
	}
}
