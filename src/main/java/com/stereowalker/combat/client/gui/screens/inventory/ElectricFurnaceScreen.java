package com.stereowalker.combat.client.gui.screens.inventory;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.gui.screens.recipebook.ElectricFurnaceRecipeGui;
import com.stereowalker.combat.world.inventory.ElectricFurnaceMenu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricFurnaceScreen extends AbstractElectricFurnaceScreen<ElectricFurnaceMenu> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = Combat.getInstance().location("textures/gui/container/electric_furnace.png");

   public ElectricFurnaceScreen(ElectricFurnaceMenu p_i51089_1_, Inventory p_i51089_2_, Component p_i51089_3_) {
      super(p_i51089_1_, new ElectricFurnaceRecipeGui(), p_i51089_2_, p_i51089_3_, FURNACE_GUI_TEXTURES);
   }
}