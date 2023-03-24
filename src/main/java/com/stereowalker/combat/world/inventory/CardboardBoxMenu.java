package com.stereowalker.combat.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CardboardBoxMenu extends AbstractContainerMenu {
   private final Container inventory;

   public CardboardBoxMenu(int id, Inventory playerInventory) {
      this(id, new SimpleContainer(10), playerInventory);
   }

   public CardboardBoxMenu(int id, Container crateInventory, Inventory playerInventory) {
      super(CMenuType.CARDBOARD_BOX, id);
      this.inventory = crateInventory;
      crateInventory.startOpen(playerInventory.player);
      for(int k = 0; k < 2; ++k) {
         for(int l = 0; l < 5; ++l) {
            this.addSlot(new Slot(crateInventory, l + k * 5, 8 + (l+2) * 18, 18 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }

   }

   @Override
   public boolean stillValid(Player playerIn) {
      return this.inventory.stillValid(playerIn);
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (index < this.inventory.getContainerSize()) {
            if (!this.moveItemStackTo(itemstack1, this.inventory.getContainerSize(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getContainerSize(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }
}