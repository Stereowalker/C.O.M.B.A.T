package com.stereowalker.combat.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AlloyFurnaceFuelSlot extends Slot {
   private final AlloyFurnaceMenu furnace;

   public AlloyFurnaceFuelSlot(AlloyFurnaceMenu containerIn, Container inventoryIn, int index, int xPosition, int yPosition) {
      super(inventoryIn, index, xPosition, yPosition);
      this.furnace = containerIn;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return this.furnace.isFuel(stack) || isBucket(stack);
   }

   @Override
   public int getMaxStackSize(ItemStack stack) {
      return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
   }

   public static boolean isBucket(ItemStack stack) {
      return stack.getItem() == Items.BUCKET;
   }
}