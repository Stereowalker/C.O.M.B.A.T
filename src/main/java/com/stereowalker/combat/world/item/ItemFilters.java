package com.stereowalker.combat.world.item;

import java.util.function.Predicate;

import com.stereowalker.combat.tags.ItemCTags;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TridentItem;

public class ItemFilters {
	public static final Predicate<ItemStack> THROWABLE_WEAPONS = (stack) -> {
		return stack.getItem() instanceof ChakramItem || stack.getItem() instanceof SpearItem;
	};

	public static final Predicate<ItemStack> TWO_HANDED_WEAPONS = (stack) -> {
		return stack.getItem() instanceof TridentItem ||  stack.getItem() instanceof SpearItem ||  stack.getItem() instanceof KatanaItem ||  stack.getItem() instanceof HalberdItem || stack.getItem() instanceof ScytheItem;
	};

	public static final Predicate<ItemStack> BLOCKABLE_WEAPONS = (stack) -> {
		return stack.is(ItemCTags.DOUBLE_EDGE_STRAIGHT_WEAPON) || stack.is(ItemCTags.SINGLE_EDGE_CURVED_WEAPON);
	};

	public static final Predicate<ItemStack> HEAVY_WEAPONS = (stack) -> {
		return stack.getItem() instanceof HammerItem;
	};

	public static final Predicate<ItemStack> RINGS = (stack) -> {
		return stack.getItem() instanceof AbstractRingItem;
	};

	public static final Predicate<ItemStack> NECKLACES = (stack) -> {
		return stack.getItem() instanceof AbstractNecklaceItem;
	};

	public static final Predicate<ItemStack> SPELLBOOKS = (stack) -> {
		return stack.getItem() instanceof AbstractSpellBookItem;
	};

	public static final Predicate<ItemStack> WANDS = (stack) -> {
		return stack.getItem() instanceof AbstractMagicCastingItem;
	};

	public static final Predicate<ItemStack> RANGED_WEAPONS = (stack) -> {
		return stack.getItem() instanceof ProjectileWeaponItem;
	};

	public static final Predicate<ItemStack> SHIELDS = (stack) -> {
		return stack.isShield(null);
	};

	public static final Predicate<ItemStack> MELEE_WEAPONS = (stack) -> {
		return THROWABLE_WEAPONS.test(stack) || TWO_HANDED_WEAPONS.test(stack) || stack.is(ItemCTags.EDGELESS_THRUSTING_WEAPON) || stack.is(ItemCTags.SINGLE_EDGE_CURVED_WEAPON) || stack.is(ItemCTags.DOUBLE_EDGE_STRAIGHT_WEAPON)
				|| HEAVY_WEAPONS.test(stack);
	};

	public static final Predicate<ItemStack> ALL_WEAPONS = (stack) -> {
		return RANGED_WEAPONS.test(stack) || MELEE_WEAPONS.test(stack);
	};
}
