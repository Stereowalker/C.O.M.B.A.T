package com.stereowalker.combat.world.level.block.entity;

import com.stereowalker.combat.world.inventory.ElectricFurnaceMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricFurnaceBlockEntity extends AbstractElectricFurnaceBlockEntity {
   public ElectricFurnaceBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
      super(CBlockEntityType.ELECTRIC_FURNACE, pWorldPosition, pBlockState, RecipeType.SMELTING);
   }

   protected Component getDefaultName() {
      return Component.translatable("container.electric_furnace");
   }

   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return new ElectricFurnaceMenu(id, player, this, this.furnaceData, this.isBurning());
   }
}