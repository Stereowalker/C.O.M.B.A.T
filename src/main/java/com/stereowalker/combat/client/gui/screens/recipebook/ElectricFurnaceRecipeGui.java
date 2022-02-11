package com.stereowalker.combat.client.gui.screens.recipebook;

import java.util.Set;

import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricFurnaceRecipeGui extends AbstractFurnaceRecipeBookComponent {
   private static final Component field_243409_i = new TranslatableComponent("gui.recipebook.toggleRecipes.blastable");

   @Override
   protected Component getRecipeFilterName() {
      return field_243409_i;
   }

   @Override
   protected Set<Item> getFuelItems() {
		return /* AbstractFurnaceTileEntity.getBurnTimes().keySet() */null;
   }
}