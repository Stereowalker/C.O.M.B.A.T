package com.stereowalker.rankup.compat.curios.accessories;

import java.util.List;
import java.util.Random;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.compat.curios.CuriosPredicates;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.stat.StatProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosAccesories {
	static Random random = new Random();
	static String[] accessory_ids = {"ring", "necklace", "bracelet", "charm"};

	public static void addEffectsToAccessory(PlayerEntity player) {
		if (player != null && CuriosApi.getSlotHelper() != null) {
			for (int x = 0; x < accessory_ids.length; x++) {
				for (int i = 0; i < CuriosApi.getSlotHelper().getSlotsForType(player, accessory_ids[x]); i++) {
					if (!CuriosCompat.getSlotsForType(player, accessory_ids[x], i).isEmpty()) addModifier(CuriosCompat.getSlotsForType(player, accessory_ids[x], i));
				}
			}
			if (CuriosPredicates.ALL_ACCESSORIES.test(player.getHeldItem(Hand.OFF_HAND))) {
				addModifier(player.getHeldItem(Hand.OFF_HAND));
			} else if (CuriosPredicates.ALL_ACCESSORIES.test(player.getHeldItem(Hand.MAIN_HAND))) {
				addModifier(player.getHeldItem(Hand.MAIN_HAND));
			} else {
				for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
					ItemStack itemstack = player.inventory.getStackInSlot(i);
					if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack)) {
						addModifier(itemstack);
					}
				}
			}
		}
	}

	public static void addEffectsToChestItems(Container chest) {
		for(int i = 0; i < chest.getInventory().size(); ++i) {
			ItemStack itemstack = chest.getInventory().get(i);
			if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack)) {
				addModifier(itemstack);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void modifierTooltip(List<ITextComponent> tooltip, ItemStack itemstack) {
		if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack)) {
			if (getModifierDebuff(itemstack) != null) {
				String amountText = getModifierDebuff(itemstack).AmountText();
				String typeText = getModifierDebuff(itemstack).getType().getName();
				String name = amountText+" "+typeText;
				if (name != null) {
					tooltip.add(1, new StringTextComponent(name).mergeStyle(TextFormatting.RED));
				}
			}
			if (getModifier(itemstack) != null) {
				String amountText = getModifier(itemstack).AmountText();
				String typeText = getModifier(itemstack).getType().getName();
				String name = amountText+" "+typeText;
				if (name != null) {
					tooltip.add(1, new StringTextComponent(name).mergeStyle(TextFormatting.AQUA));
				}
			}
		}
	}

	public static void addModifiers(PlayerEntity player) {
		if (player != null && CuriosApi.getSlotHelper() != null) {
			for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
				if (modifier != AccessoryModifiers.NONE && modifier != null) {
					int level = 0;
					for (int x = 0; x < accessory_ids.length; x++) {
						for (int i = 0; i < CuriosApi.getSlotHelper().getSlotsForType(player, accessory_ids[x]); i++) {
							if (modifier.equals(getModifier(CuriosCompat.getSlotsForType(player, accessory_ids[x], i)))) level++;
							if (modifier.equals(getModifierDebuff(CuriosCompat.getSlotsForType(player, accessory_ids[x], i)))) level++;
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

	public static void addModifier(ItemStack stack) {
		AccessoryModifiers modifier = AccessoryModifiers.getRandomModifier(random, false, null);
		AccessoryModifiers modifierDebuff = AccessoryModifiers.getRandomModifier(random, true, modifier.getType());
		if (!stack.getOrCreateTag().contains("combat:modifier")) {
			stack.getTag().putString("combat:modifier", modifier.name());
		}
		if (!stack.getOrCreateTag().contains("combat:modifierDebuff")) {
			stack.getTag().putString("combat:modifierDebuff", modifierDebuff.name());
		}
	}

	public static AccessoryModifiers getModifier(ItemStack stack) {
		if (stack.getTag() != null) {
			if (stack.getTag().contains("combat:modifier")) {
				for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
					if (modifier != AccessoryModifiers.NONE) {
						if (modifier.name().equals(stack.getTag().getString("combat:modifier"))) {
							return modifier;
						}
					}
				}
			}
		}
		return null;
	}

	public static AccessoryModifiers getModifierDebuff(ItemStack stack) {
		if (stack.getTag() != null) {
			if (stack.getTag().contains("combat:modifierDebuff")) {
				for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
					if (modifier != AccessoryModifiers.NONE) {
						if (modifier.name().equals(stack.getTag().getString("combat:modifierDebuff"))) {
							return modifier;
						}
					}
				}
			}
		}
		return null;
	}
}
