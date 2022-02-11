package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class SoulSealingEnchantment extends Enchantment {
	public SoulSealingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, CEnchantmentCategory.ANY_WEAPON, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return enchantmentLevel * 14;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 25;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != CEnchantments.CONTAINER_EXTRACTION;
	}
}