package com.stereowalker.combat.client.gui.screen.inventory;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.gui.recipebook.ElectricFurnaceRecipeGui;
import com.stereowalker.combat.inventory.container.ElectricFurnaceContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricFurnaceScreen extends AbstractElectricFurnaceScreen<ElectricFurnaceContainer> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = Combat.getInstance().location("textures/gui/container/electric_furnace.png");

   public ElectricFurnaceScreen(ElectricFurnaceContainer p_i51089_1_, PlayerInventory p_i51089_2_, ITextComponent p_i51089_3_) {
      super(p_i51089_1_, new ElectricFurnaceRecipeGui(), p_i51089_2_, p_i51089_3_, FURNACE_GUI_TEXTURES);
   }
}