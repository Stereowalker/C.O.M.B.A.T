package com.stereowalker.combat.compat.jei;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.gui.screens.inventory.FletchingScreen;
import com.stereowalker.combat.world.item.crafting.ShapedFletchingRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class FletchingCategory implements IRecipeCategory<ShapedFletchingRecipe> {
	public static final ResourceLocation UID = Combat.getInstance().location("fletching");
	
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localizedName;
	
	public FletchingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = FletchingScreen.FLETCHING_TABLE_GUI_TEXTURES;
		background = guiHelper.createDrawable(location, 48, 16, 97, 54);
		icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.FLETCHING_TABLE));
		localizedName = new TranslatableComponent("combat.recipes.fletching");
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<ShapedFletchingRecipe> getRecipeClass() {
		return ShapedFletchingRecipe.class;
	}

	@Override
	public Component getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(ShapedFletchingRecipe recipe, IIngredients ingredients) {
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
		ingredients.setInputIngredients(recipe.getIngredients());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ShapedFletchingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, false, 74, 18);
		for (int i = 1; i <= 3; i++) {
			stacks.init(i, true, 0, (i-1) * 18);
		}
		stacks.set(ingredients);
	}

}
