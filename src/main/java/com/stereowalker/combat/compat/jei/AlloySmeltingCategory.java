package com.stereowalker.combat.compat.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.item.crafting.AbstractAlloyFurnaceRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;

public class AlloySmeltingCategory implements IRecipeCategory<AbstractAlloyFurnaceRecipe> {
	public static final ResourceLocation UID = Combat.getInstance().location("alloy_smelting");

	private final IDrawable background;

	private final IDrawable icon;

	private final String localizedName;

	private final IDrawableAnimated flame;

	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

	public AlloySmeltingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Combat.getInstance().location("textures/gui/container/alloy_furnace.png");
		this.background = guiHelper.createDrawable(location, 14, 14, 123, 57);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(CBlocks.ALLOY_FURNACE));
		this.localizedName = I18n.format("combat.recipes.alloy_smelting");
		flame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(location, 176, 0, 14, 14), 200, StartDirection.TOP, true);

		this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<Integer, IDrawableAnimated>() {
			public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(location, 176, 14, 24, 17)
						.buildAnimated(cookTime.intValue(), IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	protected IDrawableAnimated getArrow(AbstractAlloyFurnaceRecipe recipe) {
		int cookTime = recipe.getCookTime();
		if (cookTime <= 0)
			cookTime = 200; 
		return (IDrawableAnimated)this.cachedArrows.getUnchecked(Integer.valueOf(cookTime));
	}

	public ResourceLocation getUid() {
		return UID;
	}

	public Class<AbstractAlloyFurnaceRecipe> getRecipeClass() {
		return AbstractAlloyFurnaceRecipe.class;
	}

	public String getTitle() {
		return this.localizedName;
	}

	@Override
	public void draw(AbstractAlloyFurnaceRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, 65, 20);
		flame.draw(matrixStack, 24, 22);
		drawExperience(recipe, matrixStack, -28, 38);
		drawCookTime(recipe, matrixStack, -28, 48);
	}

	protected void drawExperience(AbstractAlloyFurnaceRecipe recipe, MatrixStack matrixStack, int x, int y) {
		float experience = recipe.getExperience();
		if (experience > 0.0F) {
			TranslationTextComponent experienceString = new TranslationTextComponent("gui.jei.category.smelting.experience", new Object[] { Float.valueOf(experience) });
			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			int stringWidth = fontRenderer.getStringPropertyWidth((ITextProperties)experienceString);
			fontRenderer.drawText(matrixStack, experienceString, (this.background.getWidth() - stringWidth) + x, y, -8355712);
		} 
	}

	protected void drawCookTime(AbstractAlloyFurnaceRecipe recipe, MatrixStack matrixStack, int x, int y) {
		int cookTime = recipe.getCookTime();
		if (cookTime > 0) {
			int cookTimeSeconds = cookTime / 20;
			TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.smelting.time.seconds", new Object[] { Integer.valueOf(cookTimeSeconds) });
			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
			int stringWidth = fontRenderer.getStringPropertyWidth((ITextProperties)timeString);
			fontRenderer.drawText(matrixStack, timeString, (this.background.getWidth() - stringWidth) + x, y, -8355712);
		} 
	}

	public IDrawable getBackground() {
		return this.background;
	}

	public IDrawable getIcon() {
		return this.icon;
	}

	public void setIngredients(AbstractAlloyFurnaceRecipe recipe, IIngredients ingredients) {
		if (recipe.has1Result()) {
			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getRecipeOutput()));
		} else {
			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getRecipeOutput(), recipe.getRecipeOutput2()));
		}
		
		if (recipe.has2Ingredients()) {
			ingredients.setInputIngredients(Lists.newArrayList(recipe.ingredient1, recipe.ingredient2));
		} else {
			ingredients.setInputIngredients(Lists.newArrayList(recipe.ingredient1, recipe.ingredient2, recipe.ingredient3));
		}
	}

	public void setRecipe(IRecipeLayout recipeLayout, AbstractAlloyFurnaceRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, false, 101, 4);
		if (!recipe.has1Result()) stacks.init(1, false, 101, 35);
		stacks.init(2, true, 1, 2);
		stacks.init(3, true, 23, 2);
		if (!recipe.has2Ingredients()) stacks.init(4, true, 45, 2);
		stacks.set(ingredients);
	}
}
