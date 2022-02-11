package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class CEnchantmentHelper {

	public static int getBoomerangModifier(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.BOOMERANG, stack);
	}

	public static int getPenetrationModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.PENETRATION, thrownStack);
	}
	
	public static int getHollowedFLightModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.HOLLOWED_FLIGHT, thrownStack);
	}
	
	public static int getShortThrowModifier(ItemStack thrownStack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.SHORT_THROW, thrownStack);
	}
	
	public static int getCooldownReductionModifier(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.COOLDOWN_REDUCTION, stack);
	}
	
	public static int getAbsorptionModifier(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.ABSORPTION, stack);
	}
	
	public static int getIceAspectModifier(LivingEntity player) {
		return EnchantmentHelper.getEnchantmentLevel(CEnchantments.ICE_ASPECT, player);
	}
	
	public static boolean hasRestoring(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.RESTORING, stack) > 0;
	}
	
	public static boolean hasRetaining(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.RETAINING, stack) > 0;
	}
	
	public static boolean hasSoulSealing(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.SOUL_SEALING, stack) > 0;
	}
	
	public static boolean hasContainerExtraction(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.CONTAINER_EXTRACTION, stack) > 0;
	}
	
	public static boolean hasNoCooldown(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.NO_COOLDOWN, stack) > 0;
	}
}
