package com.stereowalker.combat.world.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class BloodSwordItem extends SwordItem {
	private int tick = 0;

	public BloodSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof Player) {
			Player player = (Player)entityIn;
			if(!worldIn.isClientSide && !player.getAbilities().instabuild) {
				tick++;
				if (tick > 20) {
					tick = 0;
					stack.hurtAndBreak(1, player, (p_220009_1_) -> {
						p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
					});
				}
			}
		}
	}

}
