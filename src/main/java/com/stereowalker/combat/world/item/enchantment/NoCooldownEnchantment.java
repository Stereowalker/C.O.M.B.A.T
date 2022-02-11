package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class NoCooldownEnchantment extends Enchantment {
	
	public NoCooldownEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, CEnchantmentCategory.WAND, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 5 + enchantmentLevel * 7;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench) && ench != CEnchantments.COOLDOWN_REDUCTION;
	}
}