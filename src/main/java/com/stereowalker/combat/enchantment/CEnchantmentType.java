package com.stereowalker.combat.enchantment;

import com.stereowalker.combat.item.ItemFilters;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class CEnchantmentType {
	public static final EnchantmentType THROWN = EnchantmentType.create("throwable_weapon", (item) -> {return ItemFilters.THROWABLE_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType TWO_HAND = EnchantmentType.create("two_hand", (item) -> {return ItemFilters.TWO_HANDED_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType SINGLE_EDGE = EnchantmentType.create("single_edge", (item) -> {return ItemFilters.EDGELESS_THRUSTING_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType CURVED = EnchantmentType.create("single_edge", (item) -> {return ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType HEAVY = EnchantmentType.create("heavy", (item) -> {return ItemFilters.HEAVY_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType ANY_WEAPON = EnchantmentType.create("any_weapon", (item) -> {return ItemFilters.ALL_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType MELEE_WEAPON = EnchantmentType.create("melee_weapon", (item) -> {return ItemFilters.MELEE_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentType SHIELD = EnchantmentType.create("shield", (item) -> {return ItemFilters.SHIELDS.test(new ItemStack(item));});
	public static final EnchantmentType WAND = EnchantmentType.create("wand", (item) -> {return ItemFilters.WANDS.test(new ItemStack(item));});
}
