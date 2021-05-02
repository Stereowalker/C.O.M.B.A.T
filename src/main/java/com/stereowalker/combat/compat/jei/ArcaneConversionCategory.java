package com.stereowalker.combat.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.crafting.AbstractArcaneWorkbenchRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArcaneConversionCategory implements IRecipeCategory<AbstractArcaneWorkbenchRecipe> {
	public static final ResourceLocation UID = Combat.getInstance().location("arcane_conversion");

	private final IDrawable background;

	private final IDrawable icon;

	private final String localizedName;


	public ArcaneConversionCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Combat.getInstance().location("textures/gui/container/arcane_workbench.png");
		this.background = guiHelper.createDrawable(location, 32, 9, 116, 68);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(CBlocks.ARCANE_WORKBENCH));
		this.localizedName = I18n.format("combat.recipes.arcane_conversion");
	}

	public ResourceLocation getUid() {
		return UID;
	}

	public Class<AbstractArcaneWorkbenchRecipe> getRecipeClass() {
		return AbstractArcaneWorkbenchRecipe.class;
	}

	public String getTitle() {
		return this.localizedName;
	}

	public IDrawable getBackground() {
		return this.background;
	}

	public IDrawable getIcon() {
		return this.icon;
	}

	public void setIngredients(AbstractArcaneWorkbenchRecipe recipe, IIngredients ingredients) {
		ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getRecipeOutput()));
		List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();

		ItemStack[] base = recipe.base.getMatchingStacks();
		inputs.add(Lists.newArrayList(base));
		
		ItemStack[] tridox = new ItemStack[]{new ItemStack(CItems.TRIDOX)};
		for(ItemStack stack : tridox) {
			stack.setCount(recipe.tridoxCost);
		}
		inputs.add(Lists.newArrayList(tridox));
		
		ItemStack[] addition1 = recipe.addition1.getMatchingStacks();
		for(ItemStack stack : addition1) {
			stack.setCount(recipe.additionCost1);
		}
		inputs.add(Lists.newArrayList(addition1));

		if (recipe.hasAddition2()) {
			ItemStack[] addition2 = recipe.addition2().getMatchingStacks();
			for(ItemStack stack : addition2) {
				stack.setCount(recipe.additionCost2);
			}
			inputs.add(Lists.newArrayList(addition2));
		}

		if (recipe.hasAddition3()) {
			ItemStack[] addition3 = recipe.addition3().getMatchingStacks();
			for(ItemStack stack : addition3) {
				stack.setCount(recipe.additionCost3);
			}
			inputs.add(Lists.newArrayList(addition3));
		}

		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
	}

	public void setRecipe(IRecipeLayout recipeLayout, AbstractArcaneWorkbenchRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		recipe.inputs();
		stacks.init(0, false, 96, 25);
		stacks.init(1, true, 25, 25);
		stacks.init(2, true, 0, 25);
		stacks.init(3, true, 25, 0);
		if (recipe.inputs() > 1) stacks.init(4, true, 50, 25);
		if (recipe.inputs() > 2) stacks.init(5, true, 25, 50);
		stacks.set(ingredients);
	}
}
