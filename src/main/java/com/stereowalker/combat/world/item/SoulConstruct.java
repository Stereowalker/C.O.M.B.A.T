package com.stereowalker.combat.world.item;

import java.util.function.Supplier;

import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface SoulConstruct {
	public default void decompose(ItemStack stack, Level worldIn, Player player) {
		decompose(stack, worldIn, player, () -> false);
	}
	
	public default void decompose(ItemStack stack, Level worldIn, Player player, Supplier<Boolean> breakCondition) {
		if(!worldIn.isClientSide && !player.getAbilities().instabuild) {
			if((pauseOnUse() && ((float)(stack.getUseDuration() - player.getUseItemRemainingTicks()) / 20.0F == 3600.0F)) || !pauseOnUse()) {
				tickUp();
				if (getDecomposeTick() > 20) {
					resetDecomposeTick();
					if (!CombatEntityStats.addMana(player, -0.64F) || breakCondition.get()) {
						stack.hurtAndBreak((stack.getMaxDamage()-stack.getDamageValue()), player, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
						});
					} else {
						stack.hurtAndBreak(1, player, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
						});
					}
				}
			}
		}
	}

	public default boolean pauseOnUse() {
		return false;
	}
	public int getDecomposeTick();
	public void tickUp();
	public void resetDecomposeTick();
}
