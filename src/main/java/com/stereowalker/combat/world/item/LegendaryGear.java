package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public interface LegendaryGear {

	public default Rarity getRarity() {
		return Rarity.EPIC;
	}

	public default void legendaryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof LivingEntity && isSelected) {
			if (CombatEntityStats.addMana((LivingEntity) entityIn, -0.005F)) {
				legendaryAbilityTick(stack, worldIn, entityIn, itemSlot);
			}
		}
	}

	public default void legendaryArmorTick(ItemStack stack, Level world, Player player) {
		if (CombatEntityStats.addMana((LivingEntity) player, -0.005F)) {
			legendaryAbilityTick(stack, player.level, player, 0);
		}
	}

	void legendaryAbilityTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot);
}
