package com.stereowalker.combat.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.crafting.AbstractArcaneWorkbenchRecipe;
import com.stereowalker.combat.world.level.block.CBlocks;

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

public class ArcaneConversionCategory implements IRecipeCategory<AbstractArcaneWorkbenchRecipe> {
	public static final ResourceLocation UID = Combat.getInstance().location("arcane_conversion");

	private final IDrawable background;

	private final IDrawable icon;

	private final Component localizedName;


	public ArcaneConversionCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Combat.getInstance().location("textures/gui/container/arcane_workbench.png");
		this.background = guiHelper.createDrawable(location, 32, 9, 116, 68);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(CBlocks.ARCANE_WORKBENCH));
		this.localizedName = new TranslatableComponent("combat.recipes.arcane_conversion");
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<AbstractArcaneWorkbenchRecipe> getRecipeClass() {
		return AbstractArcaneWorkbenchRecipe.class;
	}

	@Override
	public Component getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setIngredients(AbstractArcaneWorkbenchRecipe recipe, IIngredients ingredients) {
		ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getResultItem()));
		List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();

		ItemStack[] base = recipe.base.getItems();
		inputs.add(Lists.newArrayList(base));
		
		ItemStack[] tridox = new ItemStack[]{new ItemStack(CItems.TRIDOX)};
		for(ItemStack stack : tridox) {
			stack.setCount(recipe.tridoxCost);
		}
		inputs.add(Lists.newArrayList(tridox));
		
		ItemStack[] addition1 = recipe.addition1.getItems();
		for(ItemStack stack : addition1) {
			stack.setCount(recipe.additionCost1);
		}
		inputs.add(Lists.newArrayList(addition1));

		if (recipe.hasAddition2()) {
			ItemStack[] addition2 = recipe.addition2().getItems();
			for(ItemStack stack : addition2) {
				stack.setCount(recipe.additionCost2);
			}
			inputs.add(Lists.newArrayList(addition2));
		}

		if (recipe.hasAddition3()) {
			ItemStack[] addition3 = recipe.addition3().getItems();
			for(ItemStack stack : addition3) {
				stack.setCount(recipe.additionCost3);
			}
			inputs.add(Lists.newArrayList(addition3));
		}

		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
	}

	@Override
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
