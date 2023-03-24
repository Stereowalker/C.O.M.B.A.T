package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class CooldownReductionEnchantment extends Enchantment {
	
	public CooldownReductionEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, CEnchantmentCategory.WAND, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 5 + enchantmentLevel * 5;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench) && ench != CEnchantments.NO_COOLDOWN;
	}
}