package com.stereowalker.combat.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ArcaneWorkbench1Recipe extends AbstractArcaneWorkbenchRecipe {

	public ArcaneWorkbench1Recipe(ResourceLocation recipeIdIn, Ingredient baseIn, Ingredient addition1In,
			int additionCost1In, Ingredient addition2In, int additionCost2In, Ingredient addition3In,
			int additionCost3In, int tridoxCostIn, ItemStack resultIn) {
		super(recipeIdIn, baseIn, addition1In, additionCost1In, addition2In, additionCost2In, addition3In, additionCost3In,
				tridoxCostIn, resultIn);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
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
