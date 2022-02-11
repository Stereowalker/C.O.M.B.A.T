package com.stereowalker.combat.world.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class SoulPickaxeItem extends PickaxeItem implements SoulConstruct {
	private int tick = 0;
	
	public SoulPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof Player) {
			decompose(stack, worldIn, (Player)entityIn);
		}
	}

	@Override
	public int getDecomposeTick() {
		return tick;
	}

	@Override
	public void tickUp() {
		tick++;
	}

	@Override
	public void resetDecomposeTick() {
		tick = 0;
	}
}
