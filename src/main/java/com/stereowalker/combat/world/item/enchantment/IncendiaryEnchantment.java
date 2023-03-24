package com.stereowalker.combat.world.item.enchantment;

import java.util.function.Predicate;

import com.stereowalker.combat.world.item.GunItem;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class IncendiaryEnchantment extends Enchantment {
	public static final Predicate<Item> GUN = (p_220002_0_) -> {
		return p_220002_0_.asItem() instanceof GunItem;
	};
	public IncendiaryEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.create("gun", GUN), slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 20;
	}

	@Override
	public int getMaxCost(int p_223551_1_) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}
}