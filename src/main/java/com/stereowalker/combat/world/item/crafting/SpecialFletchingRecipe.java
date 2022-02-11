package com.stereowalker.combat.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class SpecialFletchingRecipe implements FletchingRecipe {
   private final ResourceLocation id;

   public SpecialFletchingRecipe(ResourceLocation idIn) {
      this.id = idIn;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public boolean isSpecial() {
      return true;
   }

   @Override
   public ItemStack getResultItem() {
      return ItemStack.EMPTY;
   }
}