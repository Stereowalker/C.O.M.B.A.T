package com.stereowalker.combat.enchantment;

import java.util.function.Predicate;

import com.stereowalker.combat.item.GunItem;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class IncendiaryEnchantment extends Enchantment {
	public static final Predicate<Item> GUN = (p_220002_0_) -> {
		return p_220002_0_.getItem() instanceof GunItem;
	};
	public IncendiaryEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.create("gun", GUN), slots);
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 20;
	}

	public int func_223551_b(int p_223551_1_) {
		return 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 1;
	}
}