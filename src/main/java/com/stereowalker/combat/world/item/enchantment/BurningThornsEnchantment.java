package com.stereowalker.combat.world.item.enchantment;

import java.util.Map.Entry;

import java.util.Random;

import com.stereowalker.combat.world.damagesource.CDamageSource;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class BurningThornsEnchantment extends Enchantment {
	public BurningThornsEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentCategory.ARMOR_CHEST, slots);
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
		return stack.getItem() instanceof ArmorItem ? true : super.canEnchant(stack);
	}

	@Override
	public void doPostHurt(LivingEntity user, Entity attacker, int level) {
		Random random = user.getRandom();
		Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CEnchantments.BURNING_THORNS, user);
		if (shouldHit(level, random)) {
			if (attacker != null) {
				attacker.setSecondsOnFire(level * 40);
				attacker.hurt(CDamageSource.causeBurningThornsDamage(user), (float)getDamage(level, random));
			}

			if (entry != null) {
				entry.getValue().hurtAndBreak(3, user, (p_222183_1_) -> {
					p_222183_1_.broadcastBreakEvent(entry.getKey());
				});
			}
		} else if (entry != null) {
			entry.getValue().hurtAndBreak(1, user, (p_222182_1_) -> {
				p_222182_1_.broadcastBreakEvent(entry.getKey());
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
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != Enchantments.THORNS;
	}
}