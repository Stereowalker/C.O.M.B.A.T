package com.stereowalker.combat.world.item.crafting;

import com.stereowalker.combat.Combat;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class CRecipeType {
	public static final RecipeType<WoodcuttingRecipe> WOODCUTTING = register("woodcutting");
	public static final RecipeType<FletchingRecipe> FLETCHING = register("fletching");
	public static final RecipeType<AbstractAlloyFurnaceRecipe> ALLOY_SMELTING = register("alloy_smelting");
	public static final RecipeType<AbstractArcaneWorkbenchRecipe> ARCANE_CONVERSION = register("arcane_conversion");
	
	public static <T extends Recipe<?>> RecipeType<T> register(String name){
		return RecipeType.register(Combat.getInstance().locationString(name));
	}
}
