package com.stereowalker.combat.entity.ai.goal;

import java.util.EnumSet;

import com.stereowalker.combat.entity.monster.ZombieMinionEntity;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

public class ZombieCasterHurtByTargetGoal extends TargetGoal {
	private final ZombieMinionEntity tameable;
	private LivingEntity attacker;
	private int timestamp;

	public ZombieCasterHurtByTargetGoal(ZombieMinionEntity theDefendingTameableIn) {
		super(theDefendingTameableIn, false);
		this.tameable = theDefendingTameableIn;
		this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		LivingEntity livingentity = this.tameable.getOwner();
		if (livingentity == null) {
			return false;
		} else {
			this.attacker = livingentity.getRevengeTarget();
			int i = livingentity.getRevengeTimer();
			return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingentity);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.tameable.setAttackTarget(this.attacker);
		LivingEntity livingentity = this.tameable.getOwner();
		if (livingentity != null) {
			this.timestamp = livingentity.getRevengeTimer();
		}

		super.startExecuting();
	}
}