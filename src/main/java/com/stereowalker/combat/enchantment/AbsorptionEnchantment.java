package com.stereowalker.combat.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AbsorptionEnchantment extends Enchantment {
	public AbsorptionEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR, slots);
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 6 + 8 * (enchantmentLevel - 1);
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 19;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 4;
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) 
	{
		return super.canApplyTogether(ench) && ench instanceof ProtectionEnchantment;
	}
}