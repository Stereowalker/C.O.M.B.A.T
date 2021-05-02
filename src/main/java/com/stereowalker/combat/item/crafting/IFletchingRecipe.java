package com.stereowalker.combat.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface IFletchingRecipe extends IRecipe<CraftingInventory> {
   default IRecipeType<?> getType() {
      return CRecipeType.FLETCHING;
   }
   
   @Override
	default ItemStack getIcon() {
	   return new ItemStack(Blocks.FLETCHING_TABLE);
	}
}