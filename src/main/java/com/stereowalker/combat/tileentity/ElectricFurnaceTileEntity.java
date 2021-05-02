package com.stereowalker.combat.tileentity;

import com.stereowalker.combat.inventory.container.ElectricFurnaceContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ElectricFurnaceTileEntity extends AbstractElectricFurnaceTileEntity {
   public ElectricFurnaceTileEntity() {
      super(CTileEntityType.ELECTRIC_FURNACE, IRecipeType.SMELTING);
   }

   protected ITextComponent getDefaultName() {
      return new TranslationTextComponent("container.electric_furnace");
   }

   protected Container createMenu(int id, PlayerInventory player) {
      return new ElectricFurnaceContainer(id, player, this, this.furnaceData, this.isBurning());
   }
}