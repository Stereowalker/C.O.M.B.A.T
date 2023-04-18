package com.stereowalker.combat.compat.jei;

import java.util.List;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;

public class RecipeManagers implements IRecipeManagerPlugin {
	
	public RecipeManagers(IJeiHelpers helper) {
	}

	@Override
	public <T, V> List<T> getRecipes(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
		return null;
	}

	@Override
	public <T> List<T> getRecipes(IRecipeCategory<T> recipeCategory) {
		return null;
	}

	@Override
	public <V> List<RecipeType<?>> getRecipeTypes(IFocus<V> focus) {
		return null;
	}

}
