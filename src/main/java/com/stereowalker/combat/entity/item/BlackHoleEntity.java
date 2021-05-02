package com.stereowalker.combat.entity.item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.entity.monster.SkeletonMinionEntity;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlackHoleEntity extends Entity {
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(SkeletonMinionEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public BlackHoleEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void registerData() {
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
	}

	private int ticks = 0;
	@Override
	public void tick() {
		super.tick();
		ticks++;
		rotationYaw++;
		if (ticks >= 600) {
			this.remove();
		}
		List<LivingEntity> pulledEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosition().offset(Direction.NORTH, 15).offset(Direction.WEST, 15).offset(Direction.UP, 15), this.getPosition().offset(Direction.SOUTH, 15).offset(Direction.EAST, 15).offset(Direction.DOWN, 15)));
		for (LivingEntity living: pulledEntities) {
			if (living instanceof PlayerEntity) {
				if(!((PlayerEntity)living).abilities.allowFlying) {
					Vector3d holePos  = this.getPositionVec();
					Vector3d livingPos  = living.getPositionVec();
					Vector3d pullDirection = holePos.subtract(livingPos).scale(0.05);
					living.setMotion(living.getMotion().add(pullDirection));
				}
			} else {
				Vector3d holePos  = this.getPositionVec();
				Vector3d livingPos  = living.getPositionVec();
				Vector3d pullDirection = holePos.subtract(livingPos).scale(0.05);
				living.setMotion(living.getMotion().add(pullDirection));
			}
		}
		List<LivingEntity> inRangeEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosition().offset(Direction.NORTH, 1).offset(Direction.WEST, 1).offset(Direction.UP, 1), this.getPosition().offset(Direction.SOUTH, 1).offset(Direction.EAST, 1).offset(Direction.DOWN, 1)));
		for (LivingEntity living: inRangeEntities) {
			if (getOwner() != null) {
				living.attackEntityFrom(CDamageSource.causeBlackHoleDamage(getOwner()), (float) (1.0F * getOwner().getAttributeValue(CAttributes.MAGIC_STRENGTH)));
			}
		}
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
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

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
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

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
