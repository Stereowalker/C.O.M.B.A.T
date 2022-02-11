package com.stereowalker.combat.event;

import com.stereowalker.combat.world.item.LegendaryGear;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LegendaryWeaponEvents {

	public static void legendaryCollection(Player player) {
		if(!player.level.isClientSide) {
			ItemStack itemstack = findLegendaryItem(player);
			ItemStack itemstack2 = findLegendaryItemInvert(player);
			int itemstackLoc = findLegendaryItemLoc(player);
			int itemstackLoc2 = findLegendaryItemInvertLoc(player);
			if((!itemstack.isEmpty() && !itemstack2.isEmpty()) && (itemstackLoc != itemstackLoc2) && !player.hasEffect(MobEffects.WITHER)) {
				player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 1));
			}
		}
	}

	private static ItemStack findLegendaryItem(Player player) {
		if (isLegendaryItem(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return player.getItemInHand(InteractionHand.OFF_HAND);
		} else {
			for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
				ItemStack itemstack = player.getInventory().getItem(i);
				if (isLegendaryItem(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	private static ItemStack findLegendaryItemInvert(Player player) {
		if (isLegendaryItem(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		} else {
			for(int i = player.getInventory().getContainerSize(); i >= 0; --i) {
				ItemStack itemstack = player.getInventory().getItem(i);
				if (isLegendaryItem(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	private static int findLegendaryItemLoc(Player player) {
		for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
			ItemStack itemstack = player.getInventory().getItem(i);
			if (isLegendaryItem(itemstack)) {
				return i;
			}
		}

		return 1000;
	}

	private static int findLegendaryItemInvertLoc(Player player) {
		for(int i = player.getInventory().getContainerSize(); i >= 0; --i) {
			ItemStack itemstack = player.getInventory().getItem(i);
			if (isLegendaryItem(itemstack)) {
				return i;
			}
		}

		return 1000;
	}

	protected static boolean isLegendaryItem(ItemStack stack) {
		return stack.getItem() instanceof LegendaryGear;
	}
}
