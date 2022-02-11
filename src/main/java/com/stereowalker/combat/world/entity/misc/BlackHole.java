package com.stereowalker.combat.world.entity.misc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.monster.SkeletonMinion;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class BlackHole extends Entity {
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(SkeletonMinion.class, EntityDataSerializers.OPTIONAL_UUID);

	public BlackHole(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
	}

	private int ticks = 0;
	@Override
	public void tick() {
		super.tick();
		ticks++;
		setYRot(getYRot()+1);
		if (ticks >= 600) {
			this.discard();
		}
		List<LivingEntity> pulledEntities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition().relative(Direction.NORTH, 15).relative(Direction.WEST, 15).relative(Direction.UP, 15), this.blockPosition().relative(Direction.SOUTH, 15).relative(Direction.EAST, 15).relative(Direction.DOWN, 15)));
		for (LivingEntity living: pulledEntities) {
			if (living instanceof Player) {
				if(!((Player)living).getAbilities().mayfly) {
					Vec3 holePos  = this.position();
					Vec3 livingPos  = living.position();
					Vec3 pullDirection = holePos.subtract(livingPos).scale(0.05);
					living.setDeltaMovement(living.getDeltaMovement().add(pullDirection));
				}
			} else {
				Vec3 holePos  = this.position();
				Vec3 livingPos  = living.position();
				Vec3 pullDirection = holePos.subtract(livingPos).scale(0.05);
				living.setDeltaMovement(living.getDeltaMovement().add(pullDirection));
			}
		}
		List<LivingEntity> inRangeEntities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition().relative(Direction.NORTH, 1).relative(Direction.WEST, 1).relative(Direction.UP, 1), this.blockPosition().relative(Direction.SOUTH, 1).relative(Direction.EAST, 1).relative(Direction.DOWN, 1)));
		for (LivingEntity living: inRangeEntities) {
			if (getOwner() != null) {
				living.hurt(CDamageSource.causeBlackHoleDamage(getOwner()), (float) (1.0F * getOwner().getAttributeValue(CAttributes.MAGIC_STRENGTH)));
			}
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}

		if (uuid != null) {
			try {
				this.setOwnerId(uuid);
			} catch (Throwable throwable) {
			}
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
	}

	@Nullable
	public UUID getOwnerId() {
		return this.entityData.get(OWNER_UNIQUE_ID).orElse((UUID)null);
	}

	public void setOwnerId(@Nullable UUID p_184754_1_) {
		this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerId();
			return uuid == null ? null : this.level.getPlayerByUUID(uuid);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entityIn) {
		return entityIn == this.getOwner();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
