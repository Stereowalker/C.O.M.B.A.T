package com.stereowalker.rankup.compat.curios.accessories;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.compat.curios.CuriosPredicates;
import com.stereowalker.rankup.AccessoryModifiers;
import com.stereowalker.rankup.AccessoryStats;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.StatProfile;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosAccesories {

	public static void addEffectsToAccessory(Player player) {
		if (player != null && CuriosApi.getSlotHelper() != null) {
			for (int x = 0; x < AccessoryStats.accessory_ids.length; x++) {
				for (int i = 0; i < CuriosApi.getSlotHelper().getSlotsForType(player, AccessoryStats.accessory_ids[x]); i++) {
					if (!CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i).isEmpty()) AccessoryStats.addModifier(CuriosCompat.getSlotsForType(player, AccessoryStats.accessory_ids[x], i));
				}
			}
			if (CuriosPredicates.ALL_ACCESSORIES.test(player.getItemInHand(InteractionHand.OFF_HAND))) {
				AccessoryStats.addModifier(player.getItemInHand(InteractionHand.OFF_HAND));
			} else if (CuriosPredicates.ALL_ACCESSORIES.test(player.getItemInHand(InteractionHand.MAIN_HAND))) {
				AccessoryStats.addModifier(player.getItemInHand(InteractionHand.MAIN_HAND));
			} else {
				for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
					ItemStack itemstack = player.getInventory().getItem(i);
					if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack)) {
						AccessoryStats.addModifier(itemstack);
					}
				}
			}
		}
	}

	public static void addModifiers(Player player) {
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
					ResourceKey<Stat> statKey = ResourceKey.create(CombatRegistries.STATS_REGISTRY, modifier.getStat());
					if (level > 0) {
						StatProfile.addModifier(player, statKey, modifier.name(), level * modifier.getAmount());
					}
					else {
						StatProfile.removeModifier(player, statKey, modifier.name());
					}
				}
			}
		}
	}
}
