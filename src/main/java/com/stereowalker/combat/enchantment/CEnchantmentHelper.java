package com.stereowalker.combat.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class CEnchantmentHelper {

	public static int getBoomerangModifier(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.BOOMERANG, stack);
	}

	public static int getPenetrationModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.PENETRATION, thrownStack);
	}
	
	public static int getHollowedFLightModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.HOLLOWED_FLIGHT, thrownStack);
	}
	
	public static int getShortThrowModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.SHORT_THROW, thrownStack);
	}
	
	public static int getCooldownReductionModifier(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.COOLDOWN_REDUCTION, stack);
	}
	
	public static int getIceAspectModifier(LivingEntity player) {
		return EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.ICE_ASPECT, player);
	}
	
	public static boolean hasRestoring(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.RESTORING, stack) > 0;
	}
	
	public static boolean hasRetaining(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.RETAINING, stack) > 0;
	}
	
	public static boolean hasSoulSealing(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.SOUL_SEALING, stack) > 0;
	}
	
	public static boolean hasContainerExtraction(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.CONTAINER_EXTRACTION, stack) > 0;
	}
	
	public static boolean hasNoCooldown(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.NO_COOLDOWN, stack) > 0;
	}
}
