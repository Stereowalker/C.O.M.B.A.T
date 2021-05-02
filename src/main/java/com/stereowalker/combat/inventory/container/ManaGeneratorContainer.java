package com.stereowalker.combat.inventory.container;

import com.stereowalker.combat.tileentity.ManaGeneratorTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ManaGeneratorContainer extends Container {
   private final IInventory inventory;
   private final IIntArray field_217064_e;

   public ManaGeneratorContainer(int id, PlayerInventory playerInventory) {
      this(id, new ManaGeneratorTileEntity(), playerInventory, new IntArray(4));
   }

   public ManaGeneratorContainer(int id, IInventory crateInventory, PlayerInventory playerInventory, IIntArray p_i50104_6_) {
      super(CContainerType.MANA_GENERATOR, id);
      assertInventorySize(crateInventory, 1);
      assertIntArraySize(p_i50104_6_, 1);
      this.inventory = crateInventory;
      this.field_217064_e = p_i50104_6_;
      crateInventory.openInventory(playerInventory.player);
      this.addSlot(new Slot(inventory, 0, 80, 35));

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }
      this.trackIntArray(p_i50104_6_);
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return this.inventory.isUsableByPlayer(playerIn);
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index < this.inventory.getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }

   @OnlyIn(Dist.CLIENT)
   public int getEnergyScaled() {
      int i = 1000;
      if (i == 0) {
         i = 200;
      }

      return Math.min(this.field_217064_e.get(0), i) * 70 / i;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEnergy() {
      return this.field_217064_e.get(0) > 0;
   }
}