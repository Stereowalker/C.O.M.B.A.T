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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BatteryChargerMenu extends AbstractContainerMenu {
   private final Container inventory;
   private final ContainerData data;

   public BatteryChargerMenu(int id, Inventory playerInventory) {
      this(id, new SimpleContainer(1), playerInventory, new SimpleContainerData(4));
   }

   public BatteryChargerMenu(int id, Container crateInventory, Inventory playerInventory, ContainerData dataIn) {
      super(CMenuType.BATTERY_CHARGER, id);
      checkContainerSize(crateInventory, 1);
      checkContainerDataCount(dataIn, 1);
      this.inventory = crateInventory;
      this.data = dataIn;
      crateInventory.startOpen(playerInventory.player);
      this.addSlot(new Slot(inventory, 0, 80, 35));

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }
      this.addDataSlots(dataIn);
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

   @OnlyIn(Dist.CLIENT)
   public int getEnergyScaled() {
      int i = 1000;
      if (i == 0) {
         i = 200;
      }

      return Math.min(this.data.get(0), i) * 70 / i;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEnergy() {
      return this.data.get(0) > 0;
   }
}