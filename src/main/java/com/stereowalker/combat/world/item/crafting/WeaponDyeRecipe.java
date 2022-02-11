package com.stereowalker.combat.world.item.crafting;

import com.google.common.collect.Lists;
import com.stereowalker.combat.world.item.DyeableWeaponItem;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class WeaponDyeRecipe extends CustomRecipe {
   public WeaponDyeRecipe(ResourceLocation idIn) {
      super(idIn);
   }

   @Override
   public boolean matches(CraftingContainer inv, Level worldIn) {
      ItemStack itemstack = ItemStack.EMPTY;
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < inv.getContainerSize(); ++i) {
         ItemStack itemstack1 = inv.getItem(i);
         if (!itemstack1.isEmpty()) {
            if (itemstack1.getItem() instanceof DyeableWeaponItem && ((DyeableWeaponItem)itemstack1.getItem()).usesDyeingRecipe()) {
               if (!itemstack.isEmpty()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!(itemstack1.getItem() instanceof DyeItem)) {
                  return false;
               }

               list.add(itemstack1);
            }
         }
      }

      return !itemstack.isEmpty() && !list.isEmpty();
   }

   @Override
   public ItemStack assemble(CraftingContainer inv) {
      List<DyeItem> list = Lists.newArrayList();
      ItemStack itemstack = ItemStack.EMPTY;

      for(int i = 0; i < inv.getContainerSize(); ++i) {
         ItemStack itemstack1 = inv.getItem(i);
         if (!itemstack1.isEmpty()) {
            Item item = itemstack1.getItem();
            if (item instanceof DyeableWeaponItem) {
               if (!itemstack.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               itemstack = itemstack1.copy();
            } else {
               if (!(item instanceof DyeItem)) {
                  return ItemStack.EMPTY;
               }

               list.add((DyeItem)item);
            }
         }
      }

      return !itemstack.isEmpty() && !list.isEmpty() ? DyeableWeaponItem.dyeItem(itemstack, list) : ItemStack.EMPTY;
   }

   @Override
   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return CRecipeSerializer.CRAFTING_SPECIAL_WEAPONDYE;
   }
}