package com.stereowalker.combat.world.item;

import java.util.List;

import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ManaOrbItem extends Item {
	public ManaOrbItem(Item.Properties builder) {
		super(builder);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		EnergyUtils.setMaxEnergy(stack, 1000, EnergyUtils.EnergyType.MAGIC_ENERGY);
		
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if ((isSelected || itemSlot == 0) && !EnergyUtils.isFull(stack, EnergyUtils.EnergyType.MAGIC_ENERGY)) {
			if (entityIn instanceof Player && ((Player)entityIn).isCreative()) {
				EnergyUtils.fillToTheBrim(stack, EnergyUtils.EnergyType.MAGIC_ENERGY);
			}
			else if (entityIn instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity)entityIn;
				if (CombatEntityStats.addMana(entity, -0.02F)) {
					EnergyUtils.addEnergyToItem(stack, 1, EnergyUtils.EnergyType.MAGIC_ENERGY);
				}
			}
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyUtils.EnergyType.MAGIC_ENERGY));
	}
}