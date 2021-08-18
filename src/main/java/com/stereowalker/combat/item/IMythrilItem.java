package com.stereowalker.combat.item;

import com.stereowalker.combat.advancements.CCriteriaTriggers;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public interface IMythrilItem {

	public default boolean isUsingEnergy(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("UsingEnergy");
	}

	public default void switchActivity(ItemStack stack, LivingEntity livingEntity) {
		stack.getOrCreateTag().putBoolean("UsingEnergy", !stack.getOrCreateTag().getBoolean("UsingEnergy"));
		stack.damageItem(1, livingEntity, (p_220045_0_) -> {
			p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
	}

	public default void setUsingEnergy(ItemStack stack, boolean activity) {
		stack.getOrCreateTag().putBoolean("UsingEnergy", activity);
	}

	public default void handleInInventory(ItemStack stack, Entity entityIn) {
		EnergyUtils.setMaxEnergy(stack, getMaxEnergy(), EnergyType.DIVINE_ENERGY);
		if (entityIn instanceof LivingEntity) {
			if (entityIn.ticksExisted%20 == 0) {
				if (isUsingEnergy(stack)) {
					if (entityIn instanceof ServerPlayerEntity) CCriteriaTriggers.ITEM_ELECTRICALLY_CHARGED.trigger((ServerPlayerEntity)entityIn, stack);
					EnergyUtils.addEnergyToItem(stack, -1, EnergyType.DIVINE_ENERGY);
				}
			}
			if (entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).isCreative()) {
				EnergyUtils.fillToTheBrim(stack, EnergyType.DIVINE_ENERGY);
			}
		}
		if (EnergyUtils.isDrained(stack, EnergyType.DIVINE_ENERGY)) {
			setUsingEnergy(stack, false);
		}
	}
	
	public abstract int getMaxEnergy();
}
