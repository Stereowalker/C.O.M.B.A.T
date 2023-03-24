package com.stereowalker.combat.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AlloyFurnaceMenu extends AbstractContainerMenu {
   private final Container inventory;
   private final ContainerData furnaceData;

   public AlloyFurnaceMenu(int id, Inventory playerInventory) {
      this(id, new SimpleContainer(6), playerInventory, new SimpleContainerData(4));
   }

   public AlloyFurnaceMenu(int id, Container crateInventory, Inventory playerInventory, ContainerData p_i50104_6_) {
      super(CMenuType.ALLOY_FURNACE, id);
      checkContainerSize(crateInventory, 6);
      checkContainerDataCount(p_i50104_6_, 4);
      this.inventory = crateInventory;
      this.furnaceData = p_i50104_6_;
      crateInventory.startOpen(playerInventory.player);
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
      this.addDataSlots(p_i50104_6_);
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

   protected boolean isFuel(ItemStack stack) {
      return AbstractFurnaceBlockEntity.isFuel(stack);
   }

   @OnlyIn(Dist.CLIENT)
   public int getBurnProgress() {
      int i = this.furnaceData.get(2);
      int j = this.furnaceData.get(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public int getLitProgress() {
      int i = this.furnaceData.get(1);
      if (i == 0) {
         i = 200;
      }

      return this.furnaceData.get(0) * 13 / i;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isLit() {
      return this.furnaceData.get(0) > 0;
   }
}