package com.stereowalker.combat.world.entity.monster;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.ai.goal.MinionCasterHurtByTargetGoal;
import com.stereowalker.combat.world.entity.ai.goal.MinionCasterHurtTargetGoal;
import com.stereowalker.combat.world.entity.ai.goal.MinionFollowCasterGoal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public class SkeletonMinion extends AbstractSkeleton implements Minion<SkeletonMinion> {
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(SkeletonMinion.class, EntityDataSerializers.OPTIONAL_UUID);
	public SkeletonMinion(EntityType<? extends SkeletonMinion> type, Level worldIn) {
		super(type, worldIn);
	}

	public SkeletonMinion(Level world) {
		this(CEntityType.SKELETON_MINION, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(4, new MinionFollowCasterGoal(this, 1.0D, 10.0F, 2.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new MinionCasterHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new MinionCasterHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, true, (p_213619_0_) -> {
			return p_213619_0_ instanceof Enemy && !(p_213619_0_ instanceof Creeper || p_213619_0_ instanceof Minion);
		}));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	@Nullable
	@Override
	protected ResourceLocation getDefaultLootTable() {
		return EntityType.SKELETON.getDefaultLootTable();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getMasterId() == null) {
			compound.putString("MasterUUID", "");
		} else {
			compound.putString("MasterUUID", this.getMasterId().toString());
		}
	}
	
	private int ticks = 0;
	@Override
	public void tick() {
		super.tick();
		ticks++;
		if (ticks >= 1200) {
			this.discard();
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}

		if (uuid != null) {
			try {
				this.setMasterId(uuid);
			} catch (Throwable throwable) {
			}
		}
	}

	@Override
	public SkeletonMinion getSelf() {
		return this;
	}

	@Nullable
	@Override
	public UUID getMasterId() {
		return this.entityData.get(OWNER_UNIQUE_ID).orElse((UUID)null);
	}

	@Override
	public void setMasterId(@Nullable UUID p_184754_1_) {
		this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
	}

	@Nullable
	@Override
	public LivingEntity getMaster() {
		try {
			UUID uuid = this.getMasterId();
			return uuid == null ? null : this.level.getPlayerByUUID(uuid);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entityIn) {
		return entityIn == this.getMaster();
	}

	@Override
	public Team getTeam() {
		LivingEntity entitylivingbase = this.getMaster();
		if (entitylivingbase != null) {
			return entitylivingbase.getTeam();
		}

		return super.getTeam();
	}

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	@Override
	public boolean isAlliedTo(Entity entityIn) {
		LivingEntity entitylivingbase = this.getMaster();
		if (entityIn == entitylivingbase) {
			return true;
		}

		if (entitylivingbase != null) {
			return entitylivingbase.isAlliedTo(entityIn);
		}

		return super.isAlliedTo(entityIn);
	}
	
	@Override
	public boolean isCustomNameVisible() {
		return true;
	}
	
	@Override
	public Component getCustomName() {
		if (this.getMaster() != null) {
			return Component.literal(this.getMaster().getScoreboardName()+"'s Skeleton");
		}
		else return super.getCustomName();
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void die(DamageSource cause) {
		if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getMaster() instanceof ServerPlayer) {
			this.getMaster().sendSystemMessage(this.getCombatTracker().getDeathMessage());
		}
		super.die(cause);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		Entity entity = source.getEntity();
		if (entity instanceof Creeper) {
			Creeper creeperentity = (Creeper)entity;
			if (creeperentity.canDropMobsSkull()) {
				creeperentity.increaseDroppedSkulls();
				this.spawnAtLocation(Items.SKELETON_SKULL);
			}
		}

	}
}