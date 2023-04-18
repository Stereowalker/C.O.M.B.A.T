package com.stereowalker.combat.compat.jei;

import com.stereowalker.combat.client.gui.screens.inventory.FletchingScreen;
import com.stereowalker.combat.world.item.crafting.FletchingRecipe;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class FletchingCategory implements IRecipeCategory<FletchingRecipe> {
	public static final RecipeType<FletchingRecipe> UID = RecipeType.create("combat", "fletching", FletchingRecipe.class);
	
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localizedName;
	
	public FletchingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = FletchingScreen.FLETCHING_TABLE_GUI_TEXTURES;
		background = guiHelper.createDrawable(location, 48, 16, 97, 54);
		icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.FLETCHING_TABLE));
		localizedName = Component.translatable("combat.recipes.fletching");
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

//	@Override
//	public void setIngredients(FletchingRecipe recipe, IIngredients ingredients) {
//		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
//		ingredients.setInputIngredients(recipe.getIngredients());
//	}

//	@Override
//	public void setRecipe(IRecipeLayout recipeLayout, FletchingRecipe recipe, IIngredients ingredients) {
//		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
//		stacks.init(0, false, 74, 18);
//		for (int i = 1; i <= 3; i++) {
//			stacks.init(i, true, 0, (i-1) * 18);
//		}
//		stacks.set(ingredients);
//	}

	@Override
	public RecipeType<FletchingRecipe> getRecipeType() {
		return UID;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FletchingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 18).addItemStack(recipe.getResultItem());
		for (int i = 1; i <= 3; i++) {
			builder.addSlot(RecipeIngredientRole.INPUT, 0, (i-1) * 18).addIngredients(recipe.getIngredients().get(i-1));
		}
	}

}
