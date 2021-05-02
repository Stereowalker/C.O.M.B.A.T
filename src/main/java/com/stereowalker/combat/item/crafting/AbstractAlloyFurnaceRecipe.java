package com.stereowalker.combat.item.crafting;

import com.stereowalker.combat.block.CBlocks;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractAlloyFurnaceRecipe implements IRecipe<IInventory> {
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
	public boolean matches(IInventory inv, World worldIn) {
		boolean flag =  
				(this.ingredient1.test(inv.getStackInSlot(0)) && this.ingredient2.test(inv.getStackInSlot(1)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(2)))) ||
				(this.ingredient1.test(inv.getStackInSlot(0)) && this.ingredient2.test(inv.getStackInSlot(2)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(1)))) ||

				(this.ingredient1.test(inv.getStackInSlot(1)) && this.ingredient2.test(inv.getStackInSlot(0)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(2)))) ||
				(this.ingredient1.test(inv.getStackInSlot(1)) && this.ingredient2.test(inv.getStackInSlot(2)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(0)))) ||

				(this.ingredient1.test(inv.getStackInSlot(2)) && this.ingredient2.test(inv.getStackInSlot(0)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(1)))) ||
				(this.ingredient1.test(inv.getStackInSlot(2)) && this.ingredient2.test(inv.getStackInSlot(1)) && (has2Ingredients() || this.ingredient3.test(inv.getStackInSlot(0))));
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
	public ItemStack getCraftingResult(IInventory inv) {
		return this.result1.copy();
	}

	/**
	 * Returns an Item that is the second result of this recipe
	 */
	public ItemStack getCraftingResult2(IInventory inv) {
		return this.has1Result() ? ItemStack.EMPTY : this.result2.copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	public boolean canFit(int width, int height) {
		return true;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getRecipeOutput() {
		return this.result1;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	public ItemStack getRecipeOutput2() {
		return this.has1Result() ? ItemStack.EMPTY : this.result2;
	}

	//   public boolean isValidAdditionItem(ItemStack addition) {
	//      return this.addition.test(addition);
	//   }

	public ItemStack getIcon() {
		return new ItemStack(CBlocks.ALLOY_FURNACE);
	}

	public ResourceLocation getId() {
		return this.recipeId;
	}

	public IRecipeType<?> getType() {
		return CRecipeType.ALLOY_SMELTING;
	}
}