package com.stereowalker.combat.world.item.crafting;

import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class AbstractAlloyFurnaceRecipe implements Recipe<Container> {
	public final Ingredient ingredient1;
	public final Ingredient ingredient2;
	public final Ingredient ingredient3;
	public final ItemStack result1;
	public final ItemStack result2;
	protected final float experience;
	protected final int cookTime;
	private final ResourceLocation recipeId;

	public AbstractAlloyFurnaceRecipe(ResourceLocation recipeIdIn, Ingredient ingredient1In, Ingredient ingredient2In, Ingredient ingredient3In, ItemStack result1In, ItemStack result2In, float experienceIn, int cookTimeIn) {
		this.recipeId = recipeIdIn;
		this.ingredient1 = ingredient1In;
		this.ingredient2 = ingredient2In;
		this.ingredient3 = ingredient3In;
		this.result1 = result1In;
		this.result2 = result2In;
		this.experience = experienceIn;
		this.cookTime = cookTimeIn;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(Container inv, Level worldIn) {
		boolean flag =  
				(this.ingredient1.test(inv.getItem(0)) && this.ingredient2.test(inv.getItem(1)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(2)))) ||
				(this.ingredient1.test(inv.getItem(0)) && this.ingredient2.test(inv.getItem(2)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(1)))) ||

				(this.ingredient1.test(inv.getItem(1)) && this.ingredient2.test(inv.getItem(0)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(2)))) ||
				(this.ingredient1.test(inv.getItem(1)) && this.ingredient2.test(inv.getItem(2)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(0)))) ||

				(this.ingredient1.test(inv.getItem(2)) && this.ingredient2.test(inv.getItem(0)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(1)))) ||
				(this.ingredient1.test(inv.getItem(2)) && this.ingredient2.test(inv.getItem(1)) && (has2Ingredients() || this.ingredient3.test(inv.getItem(0))));
		return flag;
	}
	
	public abstract boolean has2Ingredients();
	
	public abstract boolean has1Result();

	public int getCookTime() {
		return this.cookTime;
	}

	public float getExperience() {
		return this.experience;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(Container inv) {
		return this.result1.copy();
	}

	/**
	 * Returns an Item that is the second result of this recipe
	 */
	public ItemStack assemble2(Container inv) {
		return this.has1Result() ? ItemStack.EMPTY : this.result2.copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	@Override
	public ItemStack getResultItem() {
		return this.result1;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getResultItem2() {
		return this.has1Result() ? ItemStack.EMPTY : this.result2;
	}

	//   public boolean isValidAdditionItem(ItemStack addition) {
	//      return this.addition.test(addition);
	//   }

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(CBlocks.ALLOY_FURNACE);
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public RecipeType<?> getType() {
		return CRecipeType.ALLOY_SMELTING;
	}
}