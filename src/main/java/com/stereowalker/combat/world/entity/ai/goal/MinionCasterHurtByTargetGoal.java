package com.stereowalker.combat.world.entity.ai.goal;

import java.util.EnumSet;

import com.stereowalker.combat.world.entity.monster.Minion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class MinionCasterHurtByTargetGoal extends TargetGoal {
	private final Minion<?> tameable;
	private LivingEntity attacker;
	private int timestamp;

	public MinionCasterHurtByTargetGoal(Minion<?> theDefendingTameableIn) {
		super(theDefendingTameableIn.getSelf(), false);
		this.tameable = theDefendingTameableIn;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		LivingEntity livingentity = this.tameable.getMaster();
		if (livingentity == null) {
			return false;
		} else {
			this.attacker = livingentity.getLastHurtByMob();
			int i = livingentity.getLastHurtByMobTimestamp();
			return i != this.timestamp && this.canAttack(this.attacker, TargetingConditions.DEFAULT) && this.tameable.wantsToAttack(this.attacker, livingentity);
		}
	}

	@Override
	public void start() {
		if (tameable instanceof Mob) {
			((Mob)this.tameable).setTarget(this.attacker);
		}
		LivingEntity livingentity = this.tameable.getMaster();
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtByMobTimestamp();
		}

		super.start();
	}
}