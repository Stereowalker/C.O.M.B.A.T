package com.stereowalker.combat.world.entity.magic;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.world.spellcraft.SpellInstance;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class AbstractSpellCircle extends Entity {
	protected static final EntityDataAccessor<CompoundTag> SPELL = SynchedEntityData.defineId(AbstractSpellCircle.class, EntityDataSerializers.COMPOUND_TAG);
	protected static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(AbstractSpellCircle.class, EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> EFFECT_HEIGHT = SynchedEntityData.defineId(AbstractSpellCircle.class, EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(AbstractSpellCircle.class, EntityDataSerializers.OPTIONAL_UUID);

	public AbstractSpellCircle(EntityType<? extends AbstractSpellCircle> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(SPELL, new CompoundTag());
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
		this.entityData.define(RADIUS, 0.5F);
		this.entityData.define(EFFECT_HEIGHT, 0.5F);
	}

	public void setRadius(float radiusIn) {
		if (!this.level.isClientSide) {
			this.getEntityData().set(RADIUS, radiusIn);
		}

	}

	public void setEffectHeight(float heightIn) {
		if (!this.level.isClientSide) {
			this.getEntityData().set(EFFECT_HEIGHT, heightIn);
		}

	}

	@Override
	public void refreshDimensions() {
		double d0 = this.getX();
		double d1 = this.getY();
		double d2 = this.getZ();
		super.refreshDimensions();
		this.setPos(d0, d1, d2);
	}

	public float getRadius() {
		return this.getEntityData().get(RADIUS);
	}

	public float getEffectHeight() {
		return this.getEntityData().get(EFFECT_HEIGHT);
	}

	public void setSpell(SpellInstance spell) {
		this.entityData.set(SPELL, spell.write(new CompoundTag()));
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.entityData.get(SPELL));
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
		this.setRadius(compound.getFloat("Radius"));
		this.setEffectHeight(compound.getFloat("EffectHeight"));

		if (compound.contains("Spell")) {
			this.setSpell(SpellInstance.read(compound.getCompound("Spell")));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
		compound.putFloat("Radius", this.getRadius());
		compound.putFloat("EffectHeight", this.getEffectHeight());
		compound.put("Spell", this.getSpell().write(new CompoundTag()));
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

	public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
		return true;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key)
	{
		if (SPELL.equals(key)) {
			this.setSpell(this.getSpell());
		}
		if (RADIUS.equals(key)) {
			this.refreshDimensions();
		}
		if (EFFECT_HEIGHT.equals(key)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public EntityDimensions getDimensions(Pose poseIn) {
		return EntityDimensions.scalable(this.getRadius() * 1.0F, this.getEffectHeight() * 1.0F);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
