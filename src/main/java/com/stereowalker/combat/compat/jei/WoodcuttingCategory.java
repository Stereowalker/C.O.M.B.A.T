package com.stereowalker.combat.compat.jei;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.crafting.WoodcuttingRecipe;
import com.stereowalker.combat.world.level.block.CBlocks;

import mezz.jei.api.constants.ModIds;
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

public class WoodcuttingCategory implements IRecipeCategory<WoodcuttingRecipe> {
	public static final ResourceLocation UID = Combat.getInstance().location("woodcutting");
	
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localizedName;
	
	public WoodcuttingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");
		background = guiHelper.createDrawable(location, 0, 220, 82, 34);
		icon = guiHelper.createDrawableIngredient(new ItemStack(CBlocks.WOODCUTTER));
		localizedName = new TranslatableComponent("combat.recipes.woodcutting");
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<WoodcuttingRecipe> getRecipeClass() {
		return WoodcuttingRecipe.class;
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
	public void setIngredients(WoodcuttingRecipe recipe, IIngredients ingredients) {
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
		ingredients.setInputIngredients(recipe.getIngredients());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, WoodcuttingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 0, 8);
		stacks.init(1, false, 60, 8);
		stacks.set(ingredients);
	}

}
