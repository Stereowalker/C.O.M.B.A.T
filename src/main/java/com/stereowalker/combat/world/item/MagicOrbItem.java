package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.client.gui.screens.MageSelectionScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicOrbItem extends Item {
	public MagicOrbItem(Properties builder) {
		super(builder);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (worldIn.isClientSide && SpellCategory.getStrongestElementalAffinity(playerIn) == SpellCategory.NONE) {
			openMageScreen();
		}
		return super.use(worldIn, playerIn, handIn);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void openMageScreen() {
		Minecraft.getInstance().setScreen(new MageSelectionScreen(Minecraft.getInstance(), null));
	}
}