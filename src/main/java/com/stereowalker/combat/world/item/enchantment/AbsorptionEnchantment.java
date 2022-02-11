package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class AbsorptionEnchantment extends Enchantment {
	public AbsorptionEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.ARMOR, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 6 + 8 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 19;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench instanceof ProtectionEnchantment;
	}
}