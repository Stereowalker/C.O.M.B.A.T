package com.stereowalker.combat.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class AlloyFurnace2Output2Inputs extends AbstractAlloyFurnaceRecipe{

	public AlloyFurnace2Output2Inputs(ResourceLocation recipeIdIn, Ingredient ingredient1In,
			Ingredient ingredient2In, Ingredient ingredient3In, ItemStack result1In,
			ItemStack result2In, float experienceIn, int cookTimeIn) {
		super(recipeIdIn, ingredient1In, ingredient2In, ingredient3In, result1In, result2In,
				experienceIn, cookTimeIn);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CRecipeSerializer.ALLOY_SMELTING_2OU_2IN;
	}

	@Override
	public boolean has2Ingredients() {
		return true;
	}

	@Override
	public boolean has1Result() {
		return false;
	}

}
