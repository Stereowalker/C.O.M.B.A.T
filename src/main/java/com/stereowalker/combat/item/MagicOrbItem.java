package com.stereowalker.combat.item;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.client.gui.screen.MageSelectionScreen;
import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicOrbItem extends Item {
	public MagicOrbItem(Item.Properties builder) {
		super(builder);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (worldIn.isRemote && CombatEntityStats.getElementalAffinity(playerIn) == SpellCategory.NONE) {
			openMageScreen();
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void openMageScreen() {
		Minecraft.getInstance().displayGuiScreen(new MageSelectionScreen(Minecraft.getInstance(), null));
	}
}