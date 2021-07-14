package com.stereowalker.combat.entity.projectile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMagicProjectileEntity extends /* Projectile */Entity {
	private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.BYTE);
	protected static final DataParameter<Optional<UUID>> field_212362_a = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private static final DataParameter<Byte> PIERCE_LEVEL = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> SPIN_MIDAIR = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.BOOLEAN);
	@Nullable
	private BlockState inBlockState;
	protected boolean inGround;
	protected int timeInGround;
	public int arrowShake;
	public UUID shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;
	@SuppressWarnings("unused")
	private int knockbackStrength;
	private SoundEvent hitSound = this.getHitSound();
	private IntOpenHashSet piercedEntities;
	private List<Entity> hitEntities;

	protected AbstractMagicProjectileEntity(EntityType<? extends AbstractMagicProjectileEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected AbstractMagicProjectileEntity(EntityType<? extends AbstractMagicProjectileEntity> type, double x, double y, double z, World worldIn) {
		this(type, worldIn);
		this.setPosition(x, y, z);
	}

	protected AbstractMagicProjectileEntity(EntityType<? extends AbstractMagicProjectileEntity> type, LivingEntity shooter, World worldIn) {
		this(type, shooter.getPosX(), shooter.getPosYEye() - (double)0.1F, shooter.getPosZ(), worldIn);
		this.setShooter(shooter);

	}

	public void setHitSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	protected void registerData() {
		this.dataManager.register(CRITICAL, (byte)0);
		this.dataManager.register(field_212362_a, Optional.empty());
		this.dataManager.register(PIERCE_LEVEL, (byte)0);
		this.dataManager.register(SPIN_MIDAIR, true);
	}

	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
		float f1 = -MathHelper.sin(pitch * ((float)Math.PI / 180F));
		float f2 = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
		this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
		this.setMotion(this.getMotion().add(shooter.getMotion().x, shooter.isOnGround() ? 0.0D : shooter.getMotion().y, shooter.getMotion().z));
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vector3d Vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
		this.setMotion(Vector3d);
		float f = MathHelper.sqrt(horizontalMag(Vector3d));
		this.rotationYaw = (float)(MathHelper.atan2(Vector3d.x, Vector3d.z) * (double)(180F / (float)Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(Vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Sets a target for the client to interpolate towards over the next few ticks
	 */
	@OnlyIn(Dist.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	/**
	 * Updates the entity motion clientside, called by packets from the server
	 */
	@OnlyIn(Dist.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.setMotion(x, y, z);
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
			this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@SuppressWarnings("deprecation")
	public void tick() {
		super.tick();
		boolean flag = this.getNoClip();
		Vector3d vector3d = this.getMotion();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F && this.spinMidAir()) {
			float f = MathHelper.sqrt(horizontalMag(vector3d));
			this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
			this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = this.getPosition();
		BlockState blockstate = this.world.getBlockState(blockpos);
		if (!blockstate.isAir(this.world, blockpos) && !flag) {
			VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
			if (!voxelshape.isEmpty()) {
				Vector3d Vector3d1 = this.getPositionVec();

				for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
					if (axisalignedbb.offset(blockpos).contains(Vector3d1)) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.isWet()) {
			this.extinguish();
		}

		if (this.inGround && !flag) {
			if (this.inBlockState != blockstate && this.world.hasNoCollisions(this.getBoundingBox().grow(0.06D))) {
				this.inGround = false;
				this.setMotion(vector3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else if (!this.world.isRemote) {
				this.func_225516_i_();
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			++this.ticksInAir;
			Vector3d Vector3d2 = this.getPositionVec();
			Vector3d Vector3d3 = Vector3d2.add(vector3d);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(Vector3d2, Vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
				Vector3d3 = raytraceresult.getHitVec();
			}

			while(!this.removed) {
				EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(Vector3d2, Vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
					Entity entity1 = this.getShooter();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.isAirBorne = true;
				}

				if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
			}

			vector3d = this.getMotion();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (this.getIsCritical()) {
				for(int i = 0; i < 4; ++i) {
					this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d3 * (double)i / 4.0D, this.getPosY() + d4 * (double)i / 4.0D, this.getPosZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = this.getPosX() + d3;
			double d1 = this.getPosY() + d4;
			double d2 = this.getPosZ() + d0;
			float f1 = MathHelper.sqrt(horizontalMag(vector3d));
			if (this.spinMidAir()) {
				if (flag) {
					this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
				} else {
					this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
				}

				for(this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
					;
				}

				while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
					this.prevRotationPitch += 360.0F;
				}

				while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
					this.prevRotationYaw -= 360.0F;
				}

				while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
					this.prevRotationYaw += 360.0F;
				}

				this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
				this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
			}
			float f2 = 0.99F;
			if (this.isInWater()) {
				for(int j = 0; j < 4; ++j) {
					this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = this.getWaterDrag();
			}

			this.setMotion(vector3d.scale((double)f2));
			if (!this.hasNoGravity() && !flag) {
				Vector3d Vector3d4 = this.getMotion();
				this.setMotion(Vector3d4.x, Vector3d4.y - (double)0.0125F, Vector3d4.z);
			}

			this.setPosition(d5, d1, d2);
			this.doBlockCollisions();
		}
	}

	public boolean spinMidAir() {
		return dataManager.get(SPIN_MIDAIR);
	}

	public void setSpinMidAir(boolean value) {
		dataManager.set(SPIN_MIDAIR, value);
	}

	protected void func_225516_i_() {
		++this.ticksInGround;
		if (this.ticksInGround >= 1) {
			this.remove();
		}

	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn) {
		RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
			this.onEntityHit((EntityRayTraceResult)raytraceResultIn);
		} else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
			BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
			this.inBlockState = blockstate;
			Vector3d Vector3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
			this.setMotion(Vector3d);
			Vector3d Vector3d1 = Vector3d.normalize().scale((double)0.05F);
			this.setRawPosition(this.getPosX() - Vector3d1.x, this.getPosY() - Vector3d1.y, this.getPosZ() - Vector3d1.z);
			this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			this.setIsCritical(false);
			this.setPierceLevel((byte)0);
			this.setHitSound(SoundEvents.ENTITY_ARROW_HIT);
			this.setShotFromCrossbow(false);
			this.func_213870_w();
			//TODO: Once ProjectileEntity becomes public
			//			 blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
		}

	}

	private void func_213870_w() {
		if (this.hitEntities != null) {
			this.hitEntities.clear();
		}

		if (this.piercedEntities != null) {
			this.piercedEntities.clear();
		}

	}

	/**
	 * Called when the arrow hits an entity
	 */
	protected void onEntityHit(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
		if (entity != this.getShooter()) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity)entity;
				this.magicHit(livingentity);
			}
			this.remove();
		}
	}

	abstract void magicHit(LivingEntity living);

	/**
	 * The sound made when an entity is hit by this projectile
	 */
	protected abstract SoundEvent getHitSound();

	/**
	 * Gets the EntityRayTraceResult representing the entity hit
	 */
	@Nullable
	protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
		return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_213871_1_) -> {
			return !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.canBeCollidedWith() && (p_213871_1_ != this.getShooter() || this.ticksInAir >= 5) && (this.piercedEntities == null || !this.piercedEntities.contains(p_213871_1_.getEntityId()));
		});
	}

	@SuppressWarnings("deprecation")
	public void writeAdditional(CompoundNBT compound) {
		compound.putShort("life", (short)this.ticksInGround);
		if (this.inBlockState != null) {
			compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
		}

		compound.putByte("shake", (byte)this.arrowShake);
		compound.putBoolean("inGround", this.inGround);
		compound.putDouble("damage", this.damage);
		compound.putBoolean("crit", this.getIsCritical());
		compound.putByte("PierceLevel", this.getPierceLevel());
		if (this.shootingEntity != null) {
			compound.putUniqueId("OwnerUUID", this.shootingEntity);
		}

		compound.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
		compound.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@SuppressWarnings("deprecation")
	public void readAdditional(CompoundNBT compound) {
		this.ticksInGround = compound.getShort("life");
		if (compound.contains("inBlockState", 10)) {
			this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
		}

		this.arrowShake = compound.getByte("shake") & 255;
		this.inGround = compound.getBoolean("inGround");
		if (compound.contains("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		this.setIsCritical(compound.getBoolean("crit"));
		this.setPierceLevel(compound.getByte("PierceLevel"));
		if (compound.hasUniqueId("OwnerUUID")) {
			this.shootingEntity = compound.getUniqueId("OwnerUUID");
		}

		if (compound.contains("SoundEvent", 8)) {
			this.hitSound = Registry.SOUND_EVENT.getOptional(new ResourceLocation(compound.getString("SoundEvent"))).orElse(this.getHitSound());
		}

		this.setShotFromCrossbow(compound.getBoolean("ShotFromCrossbow"));
	}

	public void setShooter(@Nullable Entity entityIn) {
		this.shootingEntity = entityIn == null ? null : entityIn.getUniqueID();
	}

	@Nullable
	public Entity getShooter() {
		return this.shootingEntity != null && this.world instanceof ServerWorld ? ((ServerWorld)this.world).getEntityByUuid(this.shootingEntity) : null;
	}

	protected boolean func_225502_at_() {
		return false;
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	public double getDamage() {
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	public boolean canBeAttackedWithItem() {
		return false;
	}

	protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 0.0F;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean critical) {
		this.setArrowFlag(1, critical);
	}

	public void setPierceLevel(byte level) {
		this.dataManager.set(PIERCE_LEVEL, level);
	}

	private void setArrowFlag(int p_203049_1_, boolean p_203049_2_) {
		byte b0 = this.dataManager.get(CRITICAL);
		if (p_203049_2_) {
			this.dataManager.set(CRITICAL, (byte)(b0 | p_203049_1_));
		} else {
			this.dataManager.set(CRITICAL, (byte)(b0 & ~p_203049_1_));
		}

	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.dataManager.get(CRITICAL);
		return (b0 & 1) != 0;
	}

	/**
	 * Whether the arrow was shot from a crossbow.
	 */
	public boolean getShotFromCrossbow() {
		byte b0 = this.dataManager.get(CRITICAL);
		return (b0 & 4) != 0;
	}

	public byte getPierceLevel() {
		return this.dataManager.get(PIERCE_LEVEL);
	}

	public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
		int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
		this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));
		if (i > 0) {
			this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
		}

		if (j > 0) {
			this.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0) {
			this.setFire(100);
		}

	}

	protected float getWaterDrag() {
		return 0.001F;
	}

	/**
	 * Sets if this arrow can noClip
	 */
	public void setNoClip(boolean noClipIn) {
		this.noClip = noClipIn;
		this.setArrowFlag(2, noClipIn);
	}

	/**
	 * Whether the arrow can noClip
	 */
	public boolean getNoClip() {
		if (!this.world.isRemote) {
			return this.noClip;
		} else {
			return (this.dataManager.get(CRITICAL) & 2) != 0;
		}
	}

	/**
	 * Sets data about if this arrow entity was shot from a crossbow
	 */
	public void setShotFromCrossbow(boolean fromCrossbow) {
		this.setArrowFlag(4, fromCrossbow);
	}

	public IPacket<?> createSpawnPacket() {
		Entity entity = this.getShooter();
		return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
	}
}