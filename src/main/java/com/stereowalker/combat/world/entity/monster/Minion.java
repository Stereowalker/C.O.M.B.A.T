package com.stereowalker.combat.world.entity.monster;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public interface Minion<T extends Mob> {
	public UUID getMasterId();
	public void setMasterId(@Nullable UUID p_184754_1_);
	@Nullable
	public LivingEntity getMaster();
	public T getSelf();

	public default boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
		return true;
	}
	
}
