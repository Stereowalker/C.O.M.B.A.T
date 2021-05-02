package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;

public interface ILegendaryGear {

	public default Rarity getRarity() {
		return Rarity.EPIC;
	}

	public default void legendaryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof LivingEntity && isSelected) {
			if (CombatEntityStats.addMana((LivingEntity) entityIn, -0.005F)) {
				legendaryAbilityTick(stack, worldIn, entityIn, itemSlot);
			}
		}
	}

	public default void legendaryArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (CombatEntityStats.addMana((LivingEntity) player, -0.005F)) {
			legendaryAbilityTick(stack, player.world, player, 0);
		}
	}

	void legendaryAbilityTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot);
}
