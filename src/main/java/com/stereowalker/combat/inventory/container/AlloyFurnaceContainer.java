package com.stereowalker.combat.inventory.container;

import com.stereowalker.combat.tileentity.AlloyFurnaceTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AlloyFurnaceContainer extends Container {
   private final IInventory inventory;
   private final IIntArray furnaceData;

   public AlloyFurnaceContainer(int id, PlayerInventory playerInventory) {
      this(id, new AlloyFurnaceTileEntity(), playerInventory, new IntArray(4));
   }

   public AlloyFurnaceContainer(int id, IInventory crateInventory, PlayerInventory playerInventory, IIntArray p_i50104_6_) {
      super(CContainerType.ALLOY_FURNACE, id);
      assertInventorySize(crateInventory, 3);
      assertIntArraySize(p_i50104_6_, 4);
      this.inventory = crateInventory;
      this.furnaceData = p_i50104_6_;
      crateInventory.openInventory(playerInventory.player);
      this.addSlot(new Slot(inventory, 0, 16, 17));
      this.addSlot(new Slot(inventory, 1, 38, 17));
      this.addSlot(new Slot(inventory, 2, 60, 17));
      this.addSlot(new AlloyFurnaceFuelSlot(this, inventory, 3, 38, 53));
      this.addSlot(new AlloyFurnaceResultSlot(playerInventory.player, crateInventory, 4, 116, 19, true));
      this.addSlot(new AlloyFurnaceResultSlot(playerInventory.player, crateInventory, 5, 116, 50, false));

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

   protected boolean isFuel(ItemStack stack) {
      return AbstractFurnaceTileEntity.isFuel(stack);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217060_j() {
      int i = this.furnaceData.get(2);
      int j = this.furnaceData.get(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217059_k() {
      int i = this.furnaceData.get(1);
      if (i == 0) {
         i = 200;
      }

      return this.furnaceData.get(0) * 13 / i;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_217061_l() {
      return this.furnaceData.get(0) > 0;
   }
}