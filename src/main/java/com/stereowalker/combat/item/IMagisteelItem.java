package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public interface IMagisteelItem extends IDyeableWeaponItem {
	
	public default boolean isUsingMana(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("UsingMana");
	}

	public default void switchActivity(ItemStack stack, LivingEntity livingEntity) {
		stack.getOrCreateTag().putBoolean("UsingMana", !stack.getOrCreateTag().getBoolean("UsingMana"));
		stack.damageItem(1, livingEntity, (p_220045_0_) -> {
			p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
	}
	
	public default void setUsingMana(ItemStack stack, boolean activity) {
		stack.getOrCreateTag().putBoolean("UsingMana", activity);
	}
	
	public default void handleInInventory(ItemStack stack, Entity entityIn) {
		EnergyUtils.setMaxEnergy(stack, 500, EnergyType.MAGIC_ENERGY);
		if (entityIn instanceof LivingEntity) {
			if (entityIn.ticksExisted%20 == 0) {
				if (isUsingMana(stack)) {
					EnergyUtils.addEnergyToItem(stack, -1, EnergyType.MAGIC_ENERGY);
				} 
				else if (!EnergyUtils.isFull(stack, EnergyType.MAGIC_ENERGY) && CombatEntityStats.addMana((LivingEntity) entityIn, -0.02F)) {
					EnergyUtils.addEnergyToItem(stack, 1, EnergyType.MAGIC_ENERGY);
				}
			}
			if (entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).isCreative()) {
				EnergyUtils.fillToTheBrim(stack, EnergyType.MAGIC_ENERGY);
			}
			if (getColor(stack) != CombatEntityStats.getManaColor((LivingEntity) entityIn)) {
				setColor(stack, CombatEntityStats.getManaColor((LivingEntity) entityIn));
			}
		}
		if (EnergyUtils.isDrained(stack, EnergyType.MAGIC_ENERGY)) {
			setUsingMana(stack, false);
		}
	}
}
