package com.stereowalker.combat.entity.magic;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.spell.SpellInstance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractSpellCircleEntity extends Entity {
	protected static final DataParameter<CompoundNBT> SPELL = EntityDataManager.createKey(AbstractSpellCircleEntity.class, DataSerializers.COMPOUND_NBT);
	protected static final DataParameter<Float> RADIUS = EntityDataManager.createKey(AbstractSpellCircleEntity.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> EFFECT_HEIGHT = EntityDataManager.createKey(AbstractSpellCircleEntity.class, DataSerializers.FLOAT);
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(AbstractSpellCircleEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public AbstractSpellCircleEntity(EntityType<? extends AbstractSpellCircleEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void registerData() {
		this.dataManager.register(SPELL, new CompoundNBT());
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
		this.dataManager.register(RADIUS, 0.5F);
		this.dataManager.register(EFFECT_HEIGHT, 0.5F);
	}

	public void setRadius(float radiusIn) {
		if (!this.world.isRemote) {
			this.getDataManager().set(RADIUS, radiusIn);
		}

	}

	public void setEffectHeight(float heightIn) {
		if (!this.world.isRemote) {
			this.getDataManager().set(EFFECT_HEIGHT, heightIn);
		}

	}

	public void recalculateSize() {
		double d0 = this.getPosX();
		double d1 = this.getPosY();
		double d2 = this.getPosZ();
		super.recalculateSize();
		this.setPosition(d0, d1, d2);
	}

	public float getRadius() {
		return this.getDataManager().get(RADIUS);
	}

	public float getEffectHeight() {
		return this.getDataManager().get(EFFECT_HEIGHT);
	}

	public void setSpell(SpellInstance spell) {
		this.dataManager.set(SPELL, spell.write(new CompoundNBT()));
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.dataManager.get(SPELL));
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
		this.setRadius(compound.getFloat("Radius"));
		this.setEffectHeight(compound.getFloat("EffectHeight"));

		if (compound.contains("Spell")) {
			this.setSpell(SpellInstance.read(compound.getCompound("Spell")));
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
		compound.putFloat("Radius", this.getRadius());
		compound.putFloat("EffectHeight", this.getEffectHeight());
		compound.put("Spell", this.getSpell().write(new CompoundNBT()));
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

	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (SPELL.equals(key)) {
			this.setSpell(this.getSpell());
		}
		if (RADIUS.equals(key)) {
			this.recalculateSize();
		}
		if (EFFECT_HEIGHT.equals(key)) {
			this.recalculateSize();
		}
		super.notifyDataManagerChange(key);
	}

	@Override
	public EntitySize getSize(Pose poseIn) {
		return EntitySize.flexible(this.getRadius() * 1.0F, this.getEffectHeight() * 1.0F);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
