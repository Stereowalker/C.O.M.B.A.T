package com.stereowalker.combat.item;

import java.util.function.Predicate;

import com.stereowalker.combat.config.Config;
import com.stereowalker.unionlib.util.RegistryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.TridentItem;

public class ItemFilters {
	public static final Predicate<ItemStack> THROWABLE_WEAPONS = (stack) -> {
		return stack.getItem() instanceof ChakramItem || stack.getItem() instanceof SpearItem;
	};

	public static final Predicate<ItemStack> TWO_HANDED_WEAPONS = (stack) -> {
		return stack.getItem() instanceof TridentItem ||  stack.getItem() instanceof SpearItem ||  stack.getItem() instanceof KatanaItem ||  stack.getItem() instanceof HalberdItem || stack.getItem() instanceof ScytheItem;
	};

	public static final Predicate<ItemStack> EDGELESS_THRUSTING_WEAPONS = (stack) -> {
		boolean flag = false;
		for (String sword : Config.COMMON.edgelessThrustingWeapons.get()) {
			flag = RegistryHelper.matchesRegisteredEntry(sword, stack.getItem());
			if (flag) {
				break;
			}
		}
		return flag 
				|| stack.getItem() == CItems.WOODEN_RAPIER
				|| stack.getItem() == CItems.STONE_RAPIER
				|| stack.getItem() == CItems.IRON_RAPIER
				|| stack.getItem() == CItems.GOLDEN_RAPIER
				|| stack.getItem() == CItems.DIAMOND_RAPIER
				|| stack.getItem() == CItems.NETHERITE_RAPIER;
	};

	public static final Predicate<ItemStack> SINGLE_EDGE_CURVED_WEAPONS = (stack) -> {
		boolean flag = false;
		for (String sword : Config.COMMON.singleEdgeCurvedWeapons.get()) {
			flag = RegistryHelper.matchesRegisteredEntry(sword, stack.getItem());
			if (flag) {
				break;
			}
		}
		return flag 
				|| stack.getItem() == CItems.WOODEN_KATANA
				|| stack.getItem() == CItems.STONE_KATANA
				|| stack.getItem() == CItems.IRON_KATANA
				|| stack.getItem() == CItems.GOLDEN_KATANA
				|| stack.getItem() == CItems.DIAMOND_KATANA
				|| stack.getItem() == CItems.NETHERITE_KATANA;
	};

	public static final Predicate<ItemStack> DOUBLE_EDGE_STRAIGHT_WEAPONS = (stack) -> {
		boolean flag = false;
		for (String sword : Config.COMMON.doubleEdgeStraightWeapons.get()) {
			flag = RegistryHelper.matchesRegisteredEntry(sword, stack.getItem());
			if (flag) {
				break;
			}
		}
		return flag 
				|| stack.getItem() == Items.WOODEN_SWORD
				|| stack.getItem() == Items.STONE_SWORD
				|| stack.getItem() == Items.IRON_SWORD
				|| stack.getItem() == Items.GOLDEN_SWORD
				|| stack.getItem() == Items.DIAMOND_SWORD
				|| stack.getItem() == Items.NETHERITE_SWORD;
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
		return stack.getItem() instanceof ShootableItem;
	};

	public static final Predicate<ItemStack> SHIELDS = (stack) -> {
		return stack.isShield(null);
	};

	public static final Predicate<ItemStack> MELEE_WEAPONS = (stack) -> {
		return THROWABLE_WEAPONS.test(stack) || TWO_HANDED_WEAPONS.test(stack) || EDGELESS_THRUSTING_WEAPONS.test(stack) || SINGLE_EDGE_CURVED_WEAPONS.test(stack) || DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack)
				|| HEAVY_WEAPONS.test(stack);
	};

	public static final Predicate<ItemStack> ALL_WEAPONS = (stack) -> {
		return RANGED_WEAPONS.test(stack) || MELEE_WEAPONS.test(stack);
	};
}
