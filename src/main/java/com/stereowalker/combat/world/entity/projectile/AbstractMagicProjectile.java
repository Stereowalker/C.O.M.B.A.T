package com.stereowalker.combat.world.entity.projectile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMagicProjectile extends Projectile {
	private static final EntityDataAccessor<Byte> CRITICAL = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> field_212362_a = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> SPIN_MIDAIR = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.BOOLEAN);
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
	private IntOpenHashSet piercingIgnoreEntityIds;
	private List<Entity> hitEntities;

	protected AbstractMagicProjectile(EntityType<? extends AbstractMagicProjectile> type, Level worldIn) {
		super(type, worldIn);
	}

	protected AbstractMagicProjectile(EntityType<? extends AbstractMagicProjectile> type, double x, double y, double z, Level worldIn) {
		this(type, worldIn);
		this.setPos(x, y, z);
	}

	protected AbstractMagicProjectile(EntityType<? extends AbstractMagicProjectile> type, LivingEntity shooter, Level worldIn) {
		this(type, shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ(), worldIn);
		this.setOwner(shooter);

	}

	public void setHitSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getSize() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getViewScale();
		return distance < d0 * d0;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(CRITICAL, (byte)0);
		this.entityData.define(field_212362_a, Optional.empty());
		this.entityData.define(PIERCE_LEVEL, (byte)0);
		this.entityData.define(SPIN_MIDAIR, true);
	}

	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
		float f = -Mth.sin(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
		float f1 = -Mth.sin(pitch * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
		this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
		this.setDeltaMovement(this.getDeltaMovement().add(shooter.getDeltaMovement().x, shooter.isOnGround() ? 0.0D : shooter.getDeltaMovement().y, shooter.getDeltaMovement().z));
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		this.ticksInGround = 0;
	}

	/**
	 * Sets a target for the client to interpolate towards over the next few ticks
	 */
	@Override
	public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPos(x, y, z);
		this.setRot(yaw, pitch);
	}

	/**
	 * Updates the entity motion clientside, called by packets from the server
	 */
	@Override
	public void lerpMotion(double x, double y, double z) {
		super.lerpMotion(x, y, z);
		this.ticksInGround = 0;

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();
		boolean flag = this.getNoClip();
		Vec3 vector3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F && this.spinMidAir()) {
			double d0 = vector3d.horizontalDistance();
			this.setYRot((float)(Mth.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
			this.setXRot((float)(Mth.atan2(vector3d.y, d0) * (double)(180F / (float)Math.PI)));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}

		BlockPos blockpos = this.blockPosition();
		BlockState blockstate = this.level.getBlockState(blockpos);
		if (!blockstate.isAir() && !flag) {
			VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
			if (!voxelshape.isEmpty()) {
				Vec3 Vector3d1 = this.position();

				for(AABB axisalignedbb : voxelshape.toAabbs()) {
					if (axisalignedbb.move(blockpos).contains(Vector3d1)) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.isInWaterOrRain()) {
			this.clearFire();
		}

		if (this.inGround && !flag) {
			if (this.inBlockState != blockstate && this.level.noCollision(this.getBoundingBox().inflate(0.06D))) {
				this.inGround = false;
				this.setDeltaMovement(vector3d.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else if (!this.level.isClientSide) {
				this.func_225516_i_();
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			++this.ticksInAir;
			Vec3 Vector3d2 = this.position();
			Vec3 Vector3d3 = Vector3d2.add(vector3d);
			HitResult raytraceresult = this.level.clip(new ClipContext(Vector3d2, Vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (raytraceresult.getType() != HitResult.Type.MISS) {
				Vector3d3 = raytraceresult.getLocation();
			}

			while(!this.isRemoved()) {
				EntityHitResult entityhitresult = this.findHitEntity(Vector3d2, Vector3d3);
				if (entityhitresult != null) {
					raytraceresult = entityhitresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult)raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
						raytraceresult = null;
						entityhitresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.hasImpulse = true;
				}

				if (entityhitresult == null || this.getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
			}

			vector3d = this.getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (this.getIsCritical()) {
				for(int i = 0; i < 4; ++i) {
					this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = this.getX() + d3;
			double d1 = this.getY() + d4;
			double d2 = this.getZ() + d0;

			double d41 = vector3d.horizontalDistance();
			if (flag) {
				this.setYRot((float)(Mth.atan2(-d5, -d1) * (double)(180F / (float)Math.PI)));
			} else {
				this.setYRot((float)(Mth.atan2(d5, d1) * (double)(180F / (float)Math.PI)));
			}

			this.setXRot((float)(Mth.atan2(d4, d41) * (double)(180F / (float)Math.PI)));
			this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO, this.getYRot()));

			float f2 = 0.99F;
			if (this.isInWater()) {
				for(int j = 0; j < 4; ++j) {
					this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = this.getWaterDrag();
			}

			this.setDeltaMovement(vector3d.scale((double)f2));
			if (!this.isNoGravity() && !flag) {
				Vec3 Vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(Vector3d4.x, Vector3d4.y - (double)0.0125F, Vector3d4.z);
			}

			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}
	}

	public boolean spinMidAir() {
		return entityData.get(SPIN_MIDAIR);
	}

	public void setSpinMidAir(boolean value) {
		entityData.set(SPIN_MIDAIR, value);
	}

	protected void func_225516_i_() {
		++this.ticksInGround;
		if (this.ticksInGround >= 1) {
			this.discard();
		}

	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(HitResult raytraceResultIn) {
		HitResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == HitResult.Type.ENTITY) {
			this.onEntityHit((EntityHitResult)raytraceResultIn);
		} else if (raytraceresult$type == HitResult.Type.BLOCK) {
			BlockHitResult blockraytraceresult = (BlockHitResult)raytraceResultIn;
			BlockState blockstate = this.level.getBlockState(blockraytraceresult.getBlockPos());
			this.inBlockState = blockstate;
			Vec3 Vec3 = blockraytraceresult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
			this.setDeltaMovement(Vec3);
			Vec3 Vector3d1 = Vec3.normalize().scale((double)0.05F);
			this.setPosRaw(this.getX() - Vector3d1.x, this.getY() - Vector3d1.y, this.getZ() - Vector3d1.z);
			this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			this.setIsCritical(false);
			this.setPierceLevel((byte)0);
			this.setHitSound(SoundEvents.ARROW_HIT);
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

		if (this.piercingIgnoreEntityIds != null) {
			this.piercingIgnoreEntityIds.clear();
		}

	}

	/**
	 * Called when the arrow hits an entity
	 */
	protected void onEntityHit(EntityHitResult result) {
		Entity entity = result.getEntity();
		if (entity != this.getOwner()) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity)entity;
				this.magicHit(livingentity);
			}
			this.discard();
		}
	}

	abstract void magicHit(LivingEntity living);

	/**
	 * The sound made when an entity is hit by this projectile
	 */
	protected abstract SoundEvent getHitSound();

	/**
	 * Gets the EntityHitResult representing the entity hit
	 */
	@Nullable
	protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
		return ProjectileUtil.getEntityHitResult(this.level, this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);

	}

	@Override
	protected boolean canHitEntity(Entity p_36743_) {
		return super.canHitEntity(p_36743_) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(p_36743_.getId()));
	}

	@SuppressWarnings("deprecation")
	public void writeAdditional(CompoundTag compound) {
		compound.putShort("life", (short)this.ticksInGround);
		if (this.inBlockState != null) {
			compound.put("inBlockState", NbtUtils.writeBlockState(this.inBlockState));
		}

		compound.putByte("shake", (byte)this.arrowShake);
		compound.putBoolean("inGround", this.inGround);
		compound.putDouble("damage", this.damage);
		compound.putBoolean("crit", this.getIsCritical());
		compound.putByte("PierceLevel", this.getPierceLevel());
		if (this.shootingEntity != null) {
			compound.putUUID("OwnerUUID", this.shootingEntity);
		}

		compound.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
		compound.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@SuppressWarnings("deprecation")
	public void readAdditional(CompoundTag compound) {
		this.ticksInGround = compound.getShort("life");
		if (compound.contains("inBlockState", 10)) {
			this.inBlockState = NbtUtils.readBlockState(compound.getCompound("inBlockState"));
		}

		this.arrowShake = compound.getByte("shake") & 255;
		this.inGround = compound.getBoolean("inGround");
		if (compound.contains("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		this.setIsCritical(compound.getBoolean("crit"));
		this.setPierceLevel(compound.getByte("PierceLevel"));
		if (compound.hasUUID("OwnerUUID")) {
			this.shootingEntity = compound.getUUID("OwnerUUID");
		}

		if (compound.contains("SoundEvent", 8)) {
			this.hitSound = Registry.SOUND_EVENT.getOptional(new ResourceLocation(compound.getString("SoundEvent"))).orElse(this.getHitSound());
		}

		this.setShotFromCrossbow(compound.getBoolean("ShotFromCrossbow"));
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

	protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.0F;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean critical) {
		this.setArrowFlag(1, critical);
	}

	public void setPierceLevel(byte level) {
		this.entityData.set(PIERCE_LEVEL, level);
	}

	private void setArrowFlag(int p_203049_1_, boolean p_203049_2_) {
		byte b0 = this.entityData.get(CRITICAL);
		if (p_203049_2_) {
			this.entityData.set(CRITICAL, (byte)(b0 | p_203049_1_));
		} else {
			this.entityData.set(CRITICAL, (byte)(b0 & ~p_203049_1_));
		}

	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.entityData.get(CRITICAL);
		return (b0 & 1) != 0;
	}

	/**
	 * Whether the arrow was shot from a crossbow.
	 */
	public boolean getShotFromCrossbow() {
		byte b0 = this.entityData.get(CRITICAL);
		return (b0 & 4) != 0;
	}

	public byte getPierceLevel() {
		return this.entityData.get(PIERCE_LEVEL);
	}

	public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, p_190547_1_);
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_190547_1_);
		this.setDamage((double)(p_190547_2_ * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.level.getDifficulty().getId() * 0.11F));
		if (i > 0) {
			this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
		}

		if (j > 0) {
			this.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_190547_1_) > 0) {
			this.setSecondsOnFire(100);
		}

	}

	protected float getWaterDrag() {
		return 0.001F;
	}

	/**
	 * Sets if this arrow can noClip
	 */
	public void setNoClip(boolean noClipIn) {
		this.noPhysics = noClipIn;
		this.setArrowFlag(2, noClipIn);
	}

	/**
	 * Whether the arrow can noClip
	 */
	public boolean getNoClip() {
		if (!this.level.isClientSide) {
			return this.noPhysics;
		} else {
			return (this.entityData.get(CRITICAL) & 2) != 0;
		}
	}

	/**
	 * Sets data about if this arrow entity was shot from a crossbow
	 */
	public void setShotFromCrossbow(boolean fromCrossbow) {
		this.setArrowFlag(4, fromCrossbow);
	}
}