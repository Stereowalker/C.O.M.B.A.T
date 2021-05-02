package com.stereowalker.combat.compat.curios;

import java.util.function.Predicate;

import com.stereowalker.combat.tags.CTags;

import net.minecraft.item.ItemStack;

public class CuriosPredicates {
	public static final Predicate<ItemStack> ALL_RINGS = (stack) -> {
		return stack.getItem().isIn(CTags.ItemCTags.RINGS);
	};
	
	public static final Predicate<ItemStack> ALL_CHARMS = (stack) -> {
		return stack.getItem().isIn(CTags.ItemCTags.CHARMS);
	};
	
	public static final Predicate<ItemStack> ALL_NECKLACES = (stack) -> {
		return stack.getItem().isIn(CTags.ItemCTags.NECKLACES);
	};
	
	public static final Predicate<ItemStack> ALL_BRACELETS = (stack) -> {
		return stack.getItem().isIn(CTags.ItemCTags.BRACELETS);
	};
	
	public static final Predicate<ItemStack> ALL_ACCESSORIES = (stack) -> {
		return ALL_BRACELETS.test(stack) || ALL_NECKLACES.test(stack) || ALL_CHARMS.test(stack) || ALL_RINGS.test(stack);
	};
}
