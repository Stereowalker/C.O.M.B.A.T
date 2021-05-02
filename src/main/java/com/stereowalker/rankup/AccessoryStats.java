package com.stereowalker.rankup;

import java.util.List;
import java.util.Random;

import com.stereowalker.combat.compat.curios.CuriosPredicates;
import com.stereowalker.unionlib.item.AccessoryItem;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AccessoryStats {
	public static String[] accessory_ids = {"ring", "necklace", "bracelet", "charm"};
	static Random random = new Random();

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

	@OnlyIn(Dist.CLIENT)
	public static void modifierTooltip(List<ITextComponent> tooltip, ItemStack itemstack) {
		if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack) || itemstack.getItem() instanceof AccessoryItem) {
			if (AccessoryStats.getModifierDebuff(itemstack) != null) {
				String amountText = AccessoryStats.getModifierDebuff(itemstack).AmountText();
				String typeText = AccessoryStats.getModifierDebuff(itemstack).getType().getName();
				String name = amountText+" "+typeText;
				if (name != null) {
					tooltip.add(1, new StringTextComponent(name).mergeStyle(TextFormatting.RED));
				}
			}
			if (AccessoryStats.getModifier(itemstack) != null) {
				String amountText = AccessoryStats.getModifier(itemstack).AmountText();
				String typeText = AccessoryStats.getModifier(itemstack).getType().getName();
				String name = amountText+" "+typeText;
				if (name != null) {
					tooltip.add(1, new StringTextComponent(name).mergeStyle(TextFormatting.AQUA));
				}
			}
		}
	}

	public static void addEffectsToChestItems(Container chest) {
		for(int i = 0; i < chest.getInventory().size(); ++i) {
			ItemStack itemstack = chest.getInventory().get(i);
			if (CuriosPredicates.ALL_ACCESSORIES.test(itemstack) || itemstack.getItem() instanceof AccessoryItem) {
				AccessoryStats.addModifier(itemstack);
			}
		}
	}
}
