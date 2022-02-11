package com.stereowalker.combat.world.item.crafting;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

public interface FletchingRecipe extends Recipe<CraftingContainer> {
   default RecipeType<?> getType() {
      return CRecipeType.FLETCHING;
   }
   
   @Override
	default ItemStack getToastSymbol() {
	   return new ItemStack(Blocks.FLETCHING_TABLE);
	}
}