package com.stereowalker.combat.event;

import com.stereowalker.combat.item.ILegendaryGear;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;

public class LegendaryWeaponEvents {

	public static void legendaryCollection(PlayerEntity player) {
		if(!player.world.isRemote) {
			ItemStack itemstack = findLegendaryItem(player);
			ItemStack itemstack2 = findLegendaryItemInvert(player);
			int itemstackLoc = findLegendaryItemLoc(player);
			int itemstackLoc2 = findLegendaryItemInvertLoc(player);
			if((!itemstack.isEmpty() && !itemstack2.isEmpty()) && (itemstackLoc != itemstackLoc2) && !player.isPotionActive(Effects.WITHER)) {
				player.addPotionEffect(new EffectInstance(Effects.WITHER, 40, 1));
			}
		}
	}

	private static ItemStack findLegendaryItem(PlayerEntity player) {
		if (isLegendaryItem(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		} else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if (isLegendaryItem(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	private static ItemStack findLegendaryItemInvert(PlayerEntity player) {
		if (isLegendaryItem(player.getHeldItem(Hand.MAIN_HAND))) {
			return player.getHeldItem(Hand.MAIN_HAND);
		} else {
			for(int i = player.inventory.getSizeInventory(); i >= 0; --i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if (isLegendaryItem(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	private static int findLegendaryItemLoc(PlayerEntity player) {
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = player.inventory.getStackInSlot(i);
			if (isLegendaryItem(itemstack)) {
				return i;
			}
		}

		return 1000;
	}

	private static int findLegendaryItemInvertLoc(PlayerEntity player) {
		for(int i = player.inventory.getSizeInventory(); i >= 0; --i) {
			ItemStack itemstack = player.inventory.getStackInSlot(i);
			if (isLegendaryItem(itemstack)) {
				return i;
			}
		}

		return 1000;
	}

	protected static boolean isLegendaryItem(ItemStack stack) {
		return stack.getItem() instanceof ILegendaryGear;
	}
}
