package com.stereowalker.combat.enchantment;

import java.util.Map.Entry;
import java.util.Random;

import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class BurningThornsEnchantment extends Enchantment {
	public BurningThornsEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.ARMOR_CHEST, slots);
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 3;
	}

	/**
	 * Determines if this enchantment can be applied to a specific ItemStack.
	 */
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem ? true : super.canApply(stack);
	}

	/**
	 * Whenever an entity that has this enchantment on one of its associated items is damaged this method will be called.
	 */
	public void onUserHurt(LivingEntity user, Entity attacker, int level) {
		Random random = user.getRNG();
		Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(CEnchantments.BURNING_THORNS, user);
		if (shouldHit(level, random)) {
			if (attacker != null) {
				attacker.setFire(level * 40);
				attacker.attackEntityFrom(CDamageSource.causeBurningThornsDamage(user), (float)getDamage(level, random));
			}

			if (entry != null) {
				entry.getValue().damageItem(3, user, (p_222183_1_) -> {
					p_222183_1_.sendBreakAnimation(entry.getKey());
				});
			}
		} else if (entry != null) {
			entry.getValue().damageItem(1, user, (p_222182_1_) -> {
				p_222182_1_.sendBreakAnimation(entry.getKey());
			});
		}

	}

	public static boolean shouldHit(int level, Random rnd) {
		if (level <= 0) {
			return false;
		} else {
			return rnd.nextFloat() < 0.15F * (float)level;
		}
	}

	public static int getDamage(int level, Random rnd) {
		return level > 10 ? level - 10 : 1 + rnd.nextInt(4);
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) 
	{
		return super.canApplyTogether(ench) && ench != Enchantments.THORNS;
	}
}