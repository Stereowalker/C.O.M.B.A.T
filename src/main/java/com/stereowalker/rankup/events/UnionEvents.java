package com.stereowalker.rankup.events;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.AccessoryModifiers;
import com.stereowalker.rankup.AccessoryStats;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.StatProfile;
import com.stereowalker.unionlib.util.ModHelper;
import com.stereowalker.unionlib.world.entity.AccessorySlot;
import com.stereowalker.unionlib.world.entity.player.CustomInventoryGetter;
import com.stereowalker.unionlib.world.item.AccessoryItem;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class UnionEvents {
	static List<AccessorySlot> useable_ids = Lists.newArrayList(AccessorySlot.FINGER_1, AccessorySlot.FINGER_2, AccessorySlot.NECK_1);

	@SubscribeEvent
	public static void addEffectsToAccessories(LivingUpdateEvent event) {
		if (!ModHelper.isCuriosLoaded() && Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				addEffectsToAccessory((Player) event.getEntityLiving());
			}
		}
	}

	@SubscribeEvent
	public static void addEffectsToChestItems(PlayerContainerEvent.Open event) {
		if (Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				AccessoryStats.addEffectsToChestItems(event.getContainer());
			}
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void modifierTooltip(ItemTooltipEvent event) {
		if (Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				AccessoryStats.modifierTooltip(event.getToolTip(), event.getItemStack());
			}
		}
	}

	@SubscribeEvent
	public static void addModifiers(LivingUpdateEvent event) {
		if (!ModHelper.isCuriosLoaded() && Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				addModifiers((Player) event.getEntityLiving());
			}
		}
	}
	
	public static void addEffectsToAccessory(Player player) {
		if (player != null) {
			for (AccessorySlot slot : AccessorySlot.values()) {
				if (!((CustomInventoryGetter)player).getUnionInventory().getAccessory(slot).isEmpty())
					AccessoryStats.addModifier(((CustomInventoryGetter)player).getUnionInventory().getAccessory(slot));
			}
			if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AccessoryItem && !Collections.disjoint(useable_ids, ((AccessoryItem)player.getItemInHand(InteractionHand.OFF_HAND).getItem()).getAccessorySlots())) {
				AccessoryStats.addModifier(player.getItemInHand(InteractionHand.OFF_HAND));
			} else if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AccessoryItem && !Collections.disjoint(useable_ids, ((AccessoryItem)player.getItemInHand(InteractionHand.MAIN_HAND).getItem()).getAccessorySlots())) {
				AccessoryStats.addModifier(player.getItemInHand(InteractionHand.MAIN_HAND));
			} else {
				for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
					ItemStack itemstack = player.getInventory().getItem(i);
					if (itemstack.getItem() instanceof AccessoryItem && !Collections.disjoint(useable_ids, ((AccessoryItem)itemstack.getItem()).getAccessorySlots())) {
						AccessoryStats.addModifier(itemstack);
					}
				}
			}
		}
	}

	public static void addModifiers(Player player) {
		if (player != null) {
			for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
				if (modifier != AccessoryModifiers.NONE && modifier != null) {
					int level = 0;
					for (AccessorySlot slot : AccessorySlot.values()) {
						if (modifier.equals(AccessoryStats.getModifier(((CustomInventoryGetter)player).getUnionInventory().getAccessory(slot))))level++;
						if (modifier.equals(AccessoryStats.getModifierDebuff(((CustomInventoryGetter)player).getUnionInventory().getAccessory(slot))))level++;
					}

					ResourceKey<Stat> statKey = ResourceKey.create(CombatRegistries.STATS_REGISTRY, modifier.getStat());
					if (StatProfile.hasModifier(player, statKey, modifier.name())) {
						if (level > 0 && StatProfile.getModifier(player, statKey, modifier.name()) != level * modifier.getAmount()) {
							StatProfile.addModifier(player, statKey, modifier.name(), level * modifier.getAmount());
						}
						else if (level <= 0) {
							StatProfile.removeModifier(player, statKey, modifier.name());
						}
					} else {
						if (level > 0) {
							StatProfile.addModifier(player, statKey, modifier.name(), level * modifier.getAmount());
						}
					}
				}
			}
		}
	}
}
