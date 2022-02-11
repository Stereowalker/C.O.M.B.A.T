package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class RetainingEnchantment extends Enchantment {
	public RetainingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.VANISHABLE, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return enchantmentLevel * 25;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 50;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != Enchantments.VANISHING_CURSE;
	}
}