package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class BurningSpikesEnchantment extends Enchantment {
	public BurningSpikesEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, CEnchantmentCategory.SHIELD, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return super.getMinCost(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.isShield(null) ? true : super.canEnchant(stack);
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != CEnchantments.SPIKES;
	}
}