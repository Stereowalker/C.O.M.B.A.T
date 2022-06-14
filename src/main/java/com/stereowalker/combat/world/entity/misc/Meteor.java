package com.stereowalker.combat.world.entity.misc;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Meteor extends Entity {
	private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Meteor.class, EntityDataSerializers.INT);

	public Meteor(EntityType<? extends Meteor> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public Meteor(Level worldIn, MeteorSize size, double x, double y, double z) {
		this(CEntityType.METEOR, worldIn);
		this.setMeteorSize(size);
		this.setPos(x, y, z);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public AABB getBoundingBoxForCulling() {
		return this.getBoundingBox();
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(SIZE, Meteor.MeteorSize.LARGE.ordinal());
	}

	@Override
	public void tick() {
		this.setDeltaMovement(this.getDeltaMovement().add(0, -0.11F, 0));
		this.move(MoverType.SELF, this.getDeltaMovement());
		this.xo = this.getX();
		this.yo = this.getY();
		this.zo = this.getZ();
		this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.3D, this.getZ(), this.random.nextGaussian() * 0.05D, this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
		if(onGround && !this.level.isClientSide) {
			this.discard();
			this.explode();
		}
		super.tick();
	}

	private void explode() {
		this.level.explode(this, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), this.getMeteorSize().getExplosion(), true, Explosion.BlockInteraction.DESTROY);
	}

	public void setMeteorSize(Meteor.MeteorSize size) {
		this.entityData.set(SIZE, size.ordinal());
	}

	public Meteor.MeteorSize getMeteorSize() {
		return Meteor.MeteorSize.byId(this.entityData.get(SIZE));
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		if (compound.contains("Size", 8)) {
			this.setMeteorSize(Meteor.MeteorSize.getSizeFromString(compound.getString("Size")));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putFloat("Size", this.getMeteorSize().getSize());	
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public EntityDimensions getDimensions(Pose poseIn) {
		return EntityDimensions.scalable(this.getMeteorSize().getSize() * 1.0F, this.getMeteorSize().getSize() * 1.0F);
	}

	public enum MeteorSize {
		SMALL(1.0F, "small"),
		MEDIUM(2.0F, "medium"),
		LARGE(3.0F, "large");

		private float size;
		private String name;

		MeteorSize(float size, String name) {
			this.size = size;
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public float getSize() {
			return this.size;
		}

		public float getExplosion() {
			return 2.0F * this.size * this.size;
		}

		/**
		 * Get a boat type by it's enum ordinal
		 */
		public static Meteor.MeteorSize byId(int id) {
			Meteor.MeteorSize[] aentityboat$type = values();
			if (id < 0 || id >= aentityboat$type.length) {
				id = 0;
			}
			return aentityboat$type[id];
		}

		public static Meteor.MeteorSize getSizeFromString(String nameIn) {
			Meteor.MeteorSize[] aentityboat$type = values();
			for(int i = 0; i < aentityboat$type.length; ++i) {
				if (aentityboat$type[i].getName().equals(nameIn)) {
					return aentityboat$type[i];
				}
			}
			return aentityboat$type[0];
		}
	}

}
