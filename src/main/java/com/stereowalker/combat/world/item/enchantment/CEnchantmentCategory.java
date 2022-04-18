package com.stereowalker.combat.world.item.enchantment;

import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.world.item.ItemFilters;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CEnchantmentCategory {
	public static final EnchantmentCategory THROWN = EnchantmentCategory.create("throwable_weapon", (item) -> {return ItemFilters.THROWABLE_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentCategory TWO_HAND = EnchantmentCategory.create("two_hand", (item) -> {return ItemFilters.TWO_HANDED_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentCategory EDGELESS = EnchantmentCategory.create("edgeless", (item) -> ItemCTags.EDGELESS_THRUSTING_WEAPON.contains(item));
	public static final EnchantmentCategory CURVED = EnchantmentCategory.create("curved", (item) -> ItemCTags.SINGLE_EDGE_CURVED_WEAPON.contains(item));
	public static final EnchantmentCategory HEAVY = EnchantmentCategory.create("heavy", (item) -> {return ItemFilters.HEAVY_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentCategory ANY_WEAPON = EnchantmentCategory.create("any_weapon", (item) -> {return ItemFilters.ALL_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentCategory MELEE_WEAPON = EnchantmentCategory.create("melee_weapon", (item) -> {return ItemFilters.MELEE_WEAPONS.test(new ItemStack(item));});
	public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("shield", (item) -> {return ItemFilters.SHIELDS.test(new ItemStack(item));});
	public static final EnchantmentCategory WAND = EnchantmentCategory.create("wand", (item) -> {return ItemFilters.WANDS.test(new ItemStack(item));});
}
