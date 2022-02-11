package com.stereowalker.combat.world.item;

import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface Magisteel extends DyeableWeaponItem {
	
	public default boolean isUsingMana(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("UsingMana");
	}

	public default void switchActivity(ItemStack stack, LivingEntity livingEntity) {
		stack.getOrCreateTag().putBoolean("UsingMana", !stack.getOrCreateTag().getBoolean("UsingMana"));
		stack.hurtAndBreak(1, livingEntity, (p_220045_0_) -> {
			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
	}
	
	public default void setUsingMana(ItemStack stack, boolean activity) {
		stack.getOrCreateTag().putBoolean("UsingMana", activity);
	}
	
	public default void handleInInventory(ItemStack stack, Entity entityIn) {
		EnergyUtils.setMaxEnergy(stack, 500, EnergyUtils.EnergyType.MAGIC_ENERGY);
		if (entityIn instanceof LivingEntity) {
			if (entityIn.tickCount%20 == 0) {
				if (isUsingMana(stack)) {
					EnergyUtils.addEnergyToItem(stack, -1, EnergyUtils.EnergyType.MAGIC_ENERGY);
				} 
				else if (!EnergyUtils.isFull(stack, EnergyUtils.EnergyType.MAGIC_ENERGY) && CombatEntityStats.addMana((LivingEntity) entityIn, -0.02F)) {
					EnergyUtils.addEnergyToItem(stack, 1, EnergyUtils.EnergyType.MAGIC_ENERGY);
				}
			}
			if (entityIn instanceof Player && ((Player)entityIn).isCreative()) {
				EnergyUtils.fillToTheBrim(stack, EnergyUtils.EnergyType.MAGIC_ENERGY);
			}
			if (getColor(stack) != CombatEntityStats.getManaColor((LivingEntity) entityIn)) {
				setColor(stack, CombatEntityStats.getManaColor((LivingEntity) entityIn));
			}
		}
		if (EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.MAGIC_ENERGY)) {
			setUsingMana(stack, false);
		}
	}
}
