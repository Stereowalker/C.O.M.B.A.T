package com.stereowalker.combat.world.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicStoneItem extends Item {

	public MagicStoneItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
//		if (isSelected) {
//			stack.setDamage(stack.getDamage() - 1);
//		}
	}

}
