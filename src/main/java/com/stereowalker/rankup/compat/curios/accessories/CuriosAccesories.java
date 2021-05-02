package com.stereowalker.rankup.compat.curios.accessories;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.compat.curios.CuriosPredicates;
import com.stereowalker.rankup.AccessoryModifiers;
import com.stereowalker.rankup.AccessoryStats;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.stat.StatProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosAccesories {

	public static void addEffectsToAccessory(PlayerEntity player) {
		if (player != null && CuriosApi.getSlotHelper() != null) {
			for (int x = 0; x < AccessoryStats.accessory_ids.length; x++) {
				for (int i = 0; i < CuriosApi.getSlotHelper().getSlotsForType(player, AccessoryStats.accessory_ids[x]); i++) {
					if (!CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i).isEmpty()) AccessoryStats.addModifier(CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i));
				}
			}
			if (CuriosPredicates.ALL_ACCESSORIES.test(player.getHeldItem(Hand.OFF_HAND))) {
				AccessoryStats.addModifier(player.getHeldItem(Hand.OFF_HAND));
			} else if (CuriosPredicates.ALL_ACCESSORIES.test(player.getHeldItem(Hand.MAIN_HAND))) {
				AccessoryStats.addModifier(player.getHeldItem(Hand.MAIN_HAND));
			} else {
				for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
					ItemStack itemstack = player.inventory.getStackInSlot(i);
					if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack)) {
						AccessoryStats.addModifier(itemstack);
					}
				}
			}
		}
	}

	public static void addModifiers(PlayerEntity player) {
		if (player != null && CuriosApi.getSlotHelper() != null) {
			for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
				if (modifier != AccessoryModifiers.NONE && modifier != null) {
					int level = 0;
					for (int x = 0; x < AccessoryStats.accessory_ids.length; x++) {
						for (int i = 0; i < CuriosApi.getSlotHelper().getSlotsForType(player, AccessoryStats.accessory_ids[x]); i++) {
							if (modifier.equals(AccessoryStats.getModifier(CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i)))) level++;
							if (modifier.equals(AccessoryStats.getModifierDebuff(CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i)))) level++;
						}
					}
					Stat stat = modifier.getStat();
					if (level > 0) {
						StatProfile.addModifier(player, stat, modifier.name(), level * modifier.getAmount());
					}
					else {
						StatProfile.removeModifier(player, stat, modifier.name());
					}
				}
			}
		}
	}
}
