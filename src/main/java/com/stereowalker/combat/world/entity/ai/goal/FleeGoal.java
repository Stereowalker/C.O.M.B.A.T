package com.stereowalker.combat.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class FleeGoal<T extends LivingEntity> extends Goal {
	protected final PathfinderMob entity;
	private final double farSpeed;
	private final double nearSpeed;
	protected T avoidTarget;
	protected final float avoidDistance;
	protected Path path;
	protected final PathNavigation navigation;
	protected final Class<T> classToAvoid;
	protected final Predicate<LivingEntity> avoidTargetSelector;
	protected final Predicate<LivingEntity> field_203784_k;
	private final TargetingConditions builtTargetSelector;

	public FleeGoal(PathfinderMob entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
		this(entityIn, classToAvoidIn, (p_200828_0_) -> {
			return true;
		}, avoidDistanceIn, farSpeedIn, nearSpeedIn, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
	}

	public FleeGoal(PathfinderMob entityIn, Class<T> avoidClass, Predicate<LivingEntity> targetPredicate, float distance, double nearSpeedIn, double farSpeedIn, Predicate<LivingEntity> p_i48859_9_) {
		this.entity = entityIn;
		this.classToAvoid = avoidClass;
		this.avoidTargetSelector = targetPredicate;
		this.avoidDistance = distance;
		this.farSpeed = nearSpeedIn;
		this.nearSpeed = farSpeedIn;
		this.field_203784_k = p_i48859_9_;
		this.navigation = entityIn.getNavigation();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		this.builtTargetSelector = TargetingConditions.forCombat().range((double)distance).selector(p_i48859_9_.and(targetPredicate));
	}

	public FleeGoal(PathfinderMob p_i48860_1_, Class<T> p_i48860_2_, float p_i48860_3_, double p_i48860_4_, double p_i48860_6_, Predicate<LivingEntity> p_i48860_8_) {
		this(p_i48860_1_, p_i48860_2_, (p_203782_0_) -> {
			return true;
		}, p_i48860_3_, p_i48860_4_, p_i48860_6_, p_i48860_8_);
	}

	@Override
	 public boolean canUse() {
		 if (entity.hasEffect(CMobEffects.FEAR)) {
			 this.avoidTarget = this.entity.level.getNearestEntity(this.classToAvoid, this.builtTargetSelector, this.entity, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.entity.getBoundingBox().inflate((double)this.avoidDistance, 3.0D, (double)this.avoidDistance));
			 if (this.avoidTarget == null) {
				 return false;
			 } else {
				 Vec3 vec3d = DefaultRandomPos.getPosAway(this.entity, 16, 7, new Vec3(this.avoidTarget.getX(), this.avoidTarget.getY(), this.avoidTarget.getZ()));
				 if (vec3d == null) {
					 return false;
				 } else if (this.avoidTarget.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) < this.avoidTarget.distanceToSqr(this.entity)) {
					 return false;
				 } else {
					 this.path = this.navigation.createPath(vec3d.x, vec3d.y, vec3d.z, 0);
					 return this.path != null;
				 }
			 }
		 }
		 else {
			 return false;
		 }
	 }

	 @Override
	 public boolean canContinueToUse() {
		 return !this.navigation.isDone();
	 }

	 @Override
	 public void start() {
		 this.navigation.moveTo(this.path, this.farSpeed);
	 }

	 @Override
	 public void stop() {
		 this.avoidTarget = null;
	 }

	 @Override
	 public void tick() {
		 if (this.entity.distanceToSqr(this.avoidTarget) < 49.0D) {
			 this.entity.getNavigation().setSpeedModifier(this.nearSpeed);
		 } else {
			 this.entity.getNavigation().setSpeedModifier(this.farSpeed);
		 }

	 }
}