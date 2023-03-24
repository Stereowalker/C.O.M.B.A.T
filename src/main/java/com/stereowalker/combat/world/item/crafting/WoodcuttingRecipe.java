package com.stereowalker.combat.world.item.crafting;

import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class WoodcuttingRecipe extends SingleItemRecipe {
   public WoodcuttingRecipe(ResourceLocation p_i50021_1_, String p_i50021_2_, Ingredient p_i50021_3_, ItemStack p_i50021_4_) {
      super(CRecipeType.WOODCUTTING, CRecipeSerializer.WOODCUTTING, p_i50021_1_, p_i50021_2_, p_i50021_3_, p_i50021_4_);
   }

   @Override
   public boolean matches(Container inv, Level worldIn) {
      return this.ingredient.test(inv.getItem(0));
   }

   @Override
   public ItemStack getToastSymbol() {
      return new ItemStack(CBlocks.WOODCUTTER);
   }
}