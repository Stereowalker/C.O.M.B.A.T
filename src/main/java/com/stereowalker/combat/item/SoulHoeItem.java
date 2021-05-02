package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SoulHoeItem extends HoeItem{
	private int tick = 0;
	
	
	public SoulHoeItem(IItemTier tier_, int damage, float speed, Item.Properties properties) {
		super(tier_, damage, speed, properties);
	   }
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entityIn;
			if(!worldIn.isRemote && !player.abilities.isCreativeMode) {
				tick++;
				if (tick > 20) {
					tick = 0;
					if (!CombatEntityStats.addMana(player, -0.02F)) {
						stack.damageItem((stack.getMaxDamage()-stack.getDamage()), player, (p_220009_1_) -> {
		                      p_220009_1_.sendBreakAnimation(player.getActiveHand());
		                   });
					} else {
						stack.damageItem(1, player, (p_220009_1_) -> {
		                      p_220009_1_.sendBreakAnimation(player.getActiveHand());
		                   });
					}
				}
			}
		}
	}

}
