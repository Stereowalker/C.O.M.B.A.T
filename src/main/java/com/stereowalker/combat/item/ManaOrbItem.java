package com.stereowalker.combat.item;

import java.util.List;

import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ManaOrbItem extends Item {
	public ManaOrbItem(Item.Properties builder) {
		super(builder);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		EnergyUtils.setMaxEnergy(stack, 1000, EnergyType.MAGIC_ENERGY);
		
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (isSelected) {
			if (entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).isCreative()) {
				EnergyUtils.addEnergyToItem(stack, EnergyUtils.getMaxEnergy(stack, EnergyType.MAGIC_ENERGY), EnergyType.MAGIC_ENERGY);
			}
			else if (entityIn instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity)entityIn;
				if (stack.getDamage() > 0) {
					if (CombatEntityStats.addMana(entity, -0.02F)) {
						EnergyUtils.addEnergyToItem(stack, 1, EnergyType.MAGIC_ENERGY);
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyType.MAGIC_ENERGY));
	}
}