package com.stereowalker.combat.entity.item;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MeteorEntity extends Entity {
	private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(MeteorEntity.class, DataSerializers.VARINT);

	public MeteorEntity(EntityType<? extends MeteorEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public MeteorEntity(World worldIn, MeteorSize size, double x, double y, double z) {
		this(CEntityType.METEOR, worldIn);
		this.setMeteorSize(size);
		this.setPosition(x, y, z);
		this.setMotion(Vector3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**
	 * Returns the <b>solid</b> collision bounding box for this entity. Used to make (e.g.) boats solid. Return null if
	 * this entity is not solid.
	 *  
	 * For general purposes, use {@link #width} and {@link #height}.
	 *  
	 * @see getEntityBoundingBox
	 */
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getBoundingBox();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean canBePushed() {
		return true;
	}

	@Override
	protected void registerData() {
		this.dataManager.register(SIZE, MeteorEntity.MeteorSize.LARGE.ordinal());
	}

	@Override
	public void tick() {
		this.setMotion(this.getMotion().add(0, -0.11F, 0));
		this.move(MoverType.SELF, this.getMotion());
		this.prevPosX = this.getPosX();
		this.prevPosY = this.getPosY();
		this.prevPosZ = this.getPosZ();
		this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.3D, this.getPosZ(), this.rand.nextGaussian() * 0.05D, this.getMotion().y * 0.5D, this.rand.nextGaussian() * 0.05D);
		if(onGround && !this.world.isRemote) {
			this.remove();
			this.explode();
		}
		super.tick();
	}

	private void explode() {
		this.world.createExplosion(this, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 16.0F), this.getPosZ(), this.getMeteorSize().getExplosion(), true, Mode.DESTROY);
	}

	public void setMeteorSize(MeteorEntity.MeteorSize size) {
		this.dataManager.set(SIZE, size.ordinal());
	}

	public MeteorEntity.MeteorSize getMeteorSize() {
		return MeteorEntity.MeteorSize.byId(this.dataManager.get(SIZE));
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("Size", 8)) {
			this.setMeteorSize(MeteorEntity.MeteorSize.getSizeFromString(compound.getString("Size")));
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putFloat("Size", this.getMeteorSize().getSize());	
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public EntitySize getSize(Pose poseIn) {
		return EntitySize.flexible(this.getMeteorSize().getSize() * 1.0F, this.getMeteorSize().getSize() * 1.0F);
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
		public static MeteorEntity.MeteorSize byId(int id) {
			MeteorEntity.MeteorSize[] aentityboat$type = values();
			if (id < 0 || id >= aentityboat$type.length) {
				id = 0;
			}
			return aentityboat$type[id];
		}

		public static MeteorEntity.MeteorSize getSizeFromString(String nameIn) {
			MeteorEntity.MeteorSize[] aentityboat$type = values();
			for(int i = 0; i < aentityboat$type.length; ++i) {
				if (aentityboat$type[i].getName().equals(nameIn)) {
					return aentityboat$type[i];
				}
			}
			return aentityboat$type[0];
		}
	}

}
