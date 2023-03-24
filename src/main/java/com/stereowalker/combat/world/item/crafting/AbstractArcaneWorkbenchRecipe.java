package com.stereowalker.combat.world.item.crafting;

import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class AbstractArcaneWorkbenchRecipe implements Recipe<Container> {
	public final Ingredient base;
	public final Ingredient addition1;
	public final int additionCost1;

	protected final Ingredient addition2;
	public final int additionCost2;

	protected final Ingredient addition3;
	public final int additionCost3;

	public final int tridoxCost;
	protected final ItemStack result;
	private final ResourceLocation recipeId;

	public AbstractArcaneWorkbenchRecipe(ResourceLocation recipeIdIn, Ingredient baseIn, 
			Ingredient addition1In, int additionCost1In, 
			Ingredient addition2In, int additionCost2In, 
			Ingredient addition3In, int additionCost3In,
			int tridoxCostIn, ItemStack resultIn) {
		this.recipeId = recipeIdIn;
		this.base = baseIn;
		this.addition1 = addition1In;
		this.addition2 = addition2In;
		this.addition3 = addition3In;
		this.additionCost1 = additionCost1In;
		this.additionCost2 = additionCost2In;
		this.additionCost3 = additionCost3In;
		this.tridoxCost = tridoxCostIn;
		this.result = resultIn;
	}

	public abstract boolean hasAddition2();

	public abstract boolean hasAddition3();

	public int inputs() {
		int i = 0;
		if (hasAddition2() && hasAddition3()) i = 3;
		else if (!hasAddition2() && hasAddition3()) i = 2;
		else if (hasAddition2() && !hasAddition3()) i = 2;
		else i = 1;
		return i;
	}
	
	public Ingredient addition2() {
		return inputs() > 1 ? this.addition2 : Ingredient.of(Items.PAPER);
	}
	
	public Ingredient addition3() {
		return inputs() > 2 ? this.addition3 : Ingredient.of(Items.PAPER);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(Container inv, Level worldIn) {
		ItemStack tridox = new ItemStack(CItems.TRIDOX);
		ItemStack tridoxSlot = inv.getItem(1);
		ItemStack slot1 = inv.getItem(2);
		ItemStack slot2 = inv.getItem(3);
		ItemStack slot3 = inv.getItem(4);
		if (this.base.test(inv.getItem(0)) && (tridoxSlot.sameItem(tridox) && tridoxSlot.getCount() >= tridoxCost)) {
			if (this.addition1.test(slot1) && slot1.getCount() >= additionCost1) {
				if ((!this.hasAddition2() || (this.addition2().test(slot2) && slot2.getCount() >= additionCost2)) && (!this.hasAddition3() || (this.addition3().test(slot3) && slot3.getCount() >= additionCost3))) {
					return true;
				}
				if ((!this.hasAddition3() || (this.addition3().test(slot2) && slot2.getCount() >= additionCost3)) && (!this.hasAddition2() || (this.addition2().test(slot3) && slot3.getCount() >= additionCost2))) {
					return true;
				}
			}
			if (this.addition1.test(slot2) && slot2.getCount() >= additionCost1) {
				if ((!this.hasAddition3() || (this.addition3().test(slot1) && slot1.getCount() >= additionCost3)) && (!this.hasAddition2() || (this.addition2().test(slot3) && slot3.getCount() >= additionCost2))) {
					return true;
				}
				if ((!this.hasAddition2() || (this.addition2().test(slot1) && slot1.getCount() >= additionCost2)) && (!this.hasAddition3() || (this.addition3().test(slot3) && slot3.getCount() >= additionCost3))) {
					return true;
				}
			}
			if (this.addition1.test(slot3) && slot3.getCount() >= additionCost1) {
				if ((!this.hasAddition3() || (this.addition3().test(slot2) && slot2.getCount() >= additionCost3)) && (!this.hasAddition2() || (this.addition2().test(slot1) && slot1.getCount() >= additionCost2))) {
					return true;
				}
				if ((!this.hasAddition2() || (this.addition2().test(slot2) && slot2.getCount() >= additionCost2)) && (!this.hasAddition3() || (this.addition3().test(slot1) && slot1.getCount() >= additionCost3))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(Container inv) {
		return this.result.copy();
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
		return this.result;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(CBlocks.ARCANE_WORKBENCH);
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public RecipeType<?> getType() {
		return CRecipeType.ARCANE_CONVERSION;
	}

}