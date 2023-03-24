package com.stereowalker.combat.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ArcaneWorkbench1Recipe extends AbstractArcaneWorkbenchRecipe {

	public ArcaneWorkbench1Recipe(ResourceLocation recipeIdIn, Ingredient baseIn, Ingredient addition1In,
			int additionCost1In, Ingredient addition2In, int additionCost2In, Ingredient addition3In,
			int additionCost3In, int tridoxCostIn, ItemStack resultIn) {
		super(recipeIdIn, baseIn, addition1In, additionCost1In, addition2In, additionCost2In, addition3In, additionCost3In,
				tridoxCostIn, resultIn);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CRecipeSerializer.ARCANE_CONVERSION_1_INPUT;
	}

	@Override
	public boolean hasAddition2() {
		return false;
	}

	@Override
	public boolean hasAddition3() {
		return false;
	}
}
