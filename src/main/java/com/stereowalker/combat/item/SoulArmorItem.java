package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SoulArmorItem extends ArmorItem{
	private int tick = 0;
	
	public SoulArmorItem(IArmorMaterial material, EquipmentSlotType slots, Properties builder) {
		super(material, slots, builder);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entityIn;
			if(!worldIn.isRemote && !player.abilities.isCreativeMode) {
				tick++;
				if (tick > 20) {
					tick = 0;
					if(itemSlot > 3) {
						stack.damageItem((stack.getMaxDamage()-stack.getDamage()), player, (p_220009_1_) -> {
		                      p_220009_1_.sendBreakAnimation(player.getActiveHand());
		                   });
					}
				}
			}
		}
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if(!world.isRemote && !player.abilities.isCreativeMode) {
			tick++;
			if (tick > 20) {
				tick = 0;
				if (!CombatEntityStats.addMana(player, -0.02F)) {
					stack.damageItem((stack.getMaxDamage()-stack.getDamage()), player, (p_220009_1_) -> {
	                      p_220009_1_.sendBreakAnimation(player.getActiveHand());
	                   });
				}
			}
		}
	}

}
