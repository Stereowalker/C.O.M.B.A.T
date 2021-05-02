package com.stereowalker.combat.entity.monster;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SummonedMobEntity extends MobEntity {
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(SummonedMobEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	protected SummonedMobEntity(EntityType<? extends SummonedMobEntity> type, World p_i48553_2_) {
		super(type, p_i48553_2_);
	}
	
	protected void registerData() {
	   super.registerData();
	   this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
	}
	
	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
	   super.writeAdditional(compound);
	   if (this.getOwnerId() == null) {
	      compound.putString("OwnerUUID", "");
	   } else {
	      compound.putString("OwnerUUID", this.getOwnerId().toString());
	   }
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
	   super.readAdditional(compound);
	   UUID uuid;
		if (compound.hasUniqueId("Owner")) {
			uuid = compound.getUniqueId("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s);
		}

		if (uuid != null) {
			try {
				this.setOwnerId(uuid);
			} catch (Throwable throwable) {
			}
		}
	}
	
	private int ticks = 0;
	
	@Override
	public void tick() {
		super.tick();
		ticks++;
	}
	
	@Nullable
	   public UUID getOwnerId() {
	      return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID)null);
	   }

	   public void setOwnerId(@Nullable UUID p_184754_1_) {
	      this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
	   }
	
	@Nullable
	   public LivingEntity getOwner() {
	      try {
	         UUID uuid = this.getOwnerId();
	         return uuid == null ? null : this.world.getPlayerByUuid(uuid);
	      } catch (IllegalArgumentException var2) {
	         return null;
	      }
	   }
	
	   public boolean isOwner(LivingEntity entityIn) {
	      return entityIn == this.getOwner();
	   }
	
	   public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
	      return true;
	   }

	   public Team getTeam() {
		   LivingEntity entitylivingbase = this.getOwner();
	         if (entitylivingbase != null) {
	            return entitylivingbase.getTeam();
	         }

	      return super.getTeam();
	   }
	
	   /**
	    * Returns whether this Entity is on the same team as the given Entity.
	    */
	   public boolean isOnSameTeam(Entity entityIn) {
		   LivingEntity entitylivingbase = this.getOwner();
	         if (entityIn == entitylivingbase) {
	            return true;
	         }

	         if (entitylivingbase != null) {
	            return entitylivingbase.isOnSameTeam(entityIn);
	         }

	      return super.isOnSameTeam(entityIn);
	   }

	   /**
	    * Called when the mob's health reaches 0.
	    */
	   public void onDeath(DamageSource cause) {
	      if (!this.world.isRemote && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
	         this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage(), Util.DUMMY_UUID);
	      }

	      super.onDeath(cause);
	   }

}
