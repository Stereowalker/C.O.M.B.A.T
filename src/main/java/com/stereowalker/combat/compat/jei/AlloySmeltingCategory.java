package com.stereowalker.combat.compat.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.crafting.AbstractAlloyFurnaceRecipe;
import com.stereowalker.combat.world.level.block.CBlocks;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AlloySmeltingCategory implements IRecipeCategory<AbstractAlloyFurnaceRecipe> {
	public static final RecipeType<AbstractAlloyFurnaceRecipe> UID = RecipeType.create("combat", "alloy_smelting", AbstractAlloyFurnaceRecipe.class);

	private final IDrawable background;

	private final IDrawable icon;

	private final Component localizedName;

	private final IDrawableAnimated flame;

	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

	public AlloySmeltingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Combat.getInstance().location("textures/gui/container/alloy_furnace.png");
		this.background = guiHelper.createDrawable(location, 14, 14, 123, 57);
		this.icon = guiHelper.createDrawableItemStack(new ItemStack(CBlocks.ALLOY_FURNACE));
		this.localizedName = Component.translatable("combat.recipes.alloy_smelting");
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

	public Component getTitle() {
		return this.localizedName;
	}

	@Override
	public void draw(AbstractAlloyFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(stack, 65, 20);
		flame.draw(stack, 24, 22);
		drawExperience(recipe, stack, -28, 38);
		drawCookTime(recipe, stack, -28, 48);
	}

	@SuppressWarnings("resource")
	protected void drawExperience(AbstractAlloyFurnaceRecipe recipe, PoseStack matrixStack, int x, int y) {
		float experience = recipe.getExperience();
		if (experience > 0.0F) {
			Component experienceString = Component.translatable("gui.jei.category.smelting.experience", new Object[] { Float.valueOf(experience) });
			Font font = Minecraft.getInstance().font;
			int stringWidth = font.width((FormattedText)experienceString);
			font.draw(matrixStack, experienceString, (this.background.getWidth() - stringWidth) + x, y, -8355712);
		} 
	}

	@SuppressWarnings("resource")
	protected void drawCookTime(AbstractAlloyFurnaceRecipe recipe, PoseStack matrixStack, int x, int y) {
		int cookTime = recipe.getCookTime();
		if (cookTime > 0) {
			int cookTimeSeconds = cookTime / 20;
			Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", new Object[] { Integer.valueOf(cookTimeSeconds) });
			Font font = Minecraft.getInstance().font;
			int stringWidth = font.width((FormattedText)timeString);
			font.draw(matrixStack, timeString, (this.background.getWidth() - stringWidth) + x, y, -8355712);
		} 
	}

	public IDrawable getBackground() {
		return this.background;
	}

	public IDrawable getIcon() {
		return this.icon;
	}

//	public void setIngredients(AbstractAlloyFurnaceRecipe recipe, IIngredients ingredients) {
//		if (recipe.has1Result()) {
//			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getResultItem()));
//		} else {
//			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getResultItem(), recipe.getResultItem2()));
//		}
//		
//		if (recipe.has2Ingredients()) {
//			ingredients.setInputIngredients(Lists.newArrayList(recipe.ingredient1, recipe.ingredient2));
//		} else {
//			ingredients.setInputIngredients(Lists.newArrayList(recipe.ingredient1, recipe.ingredient2, recipe.ingredient3));
//		}
//	}

//	public void setRecipe(IRecipeLayout recipeLayout, AbstractAlloyFurnaceRecipe recipe, IIngredients ingredients) {
//		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
//		stacks.init(0, false, 101, 4);
//		if (!recipe.has1Result()) stacks.init(1, false, 101, 35);
//		stacks.init(2, true, 1, 2);
//		stacks.init(3, true, 23, 2);
//		if (!recipe.has2Ingredients()) stacks.init(4, true, 45, 2);
//		stacks.set(ingredients);
//	}

	@Override
	public RecipeType<AbstractAlloyFurnaceRecipe> getRecipeType() {
		return UID;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AbstractAlloyFurnaceRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 4).addItemStack(recipe.getResultItem());
		if (!recipe.has1Result()) builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 35).addItemStack(recipe.getResultItem2());
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 2).addIngredients(recipe.ingredient1);
		builder.addSlot(RecipeIngredientRole.INPUT, 23, 2).addIngredients(recipe.ingredient2);
		if (!recipe.has2Ingredients()) builder.addSlot(RecipeIngredientRole.INPUT, 45, 2).addIngredients(recipe.ingredient3);
	}
}
