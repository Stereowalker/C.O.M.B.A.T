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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public class ZombieMinion extends Zombie implements Minion<ZombieMinion> {
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(ZombieMinion.class, EntityDataSerializers.OPTIONAL_UUID);
	public ZombieMinion(EntityType<? extends ZombieMinion> type, Level world) {
		super(type, world);
	}

	public ZombieMinion(Level p_i48549_2_) {
		this(CEntityType.ZOMBIE_MINION, p_i48549_2_);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new MinionFollowCasterGoal(this, 1.0D, 10.0F, 2.0F));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, new MinionCasterHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new MinionCasterHurtTargetGoal(this));
		this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, true, (p_213619_0_) -> {
			return p_213619_0_ instanceof Enemy && !(p_213619_0_ instanceof Creeper || p_213619_0_ instanceof Minion);
		}));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getMasterId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getMasterId().toString());
		}
	}
	
	@Override
	public boolean isCustomNameVisible() {
		return true;
	}
	
	@Override
	public Component getCustomName() {
		if (this.getMaster() != null) {
			return Component.literal(this.getMaster().getScoreboardName()+"'s Zombie");
		}
		else return super.getCustomName();
	}
	
	private int ticks = 0;
	
	@Override
	public void tick() {
		super.tick();
		ticks++;
		if (ticks == 1200) {
			this.discard();
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
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
	public ZombieMinion getSelf() {
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
	public void die(DamageSource cause) {
		if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getMaster() instanceof ServerPlayer) {
			this.getMaster().sendSystemMessage(this.getCombatTracker().getDeathMessage());
		}

		super.die(cause);
	}

}
