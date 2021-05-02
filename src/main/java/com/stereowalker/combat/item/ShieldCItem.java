package com.stereowalker.combat.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class ShieldCItem extends ShieldItem {

	public ShieldCItem(Properties builder) {
		super(builder);
	}
	
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

}
