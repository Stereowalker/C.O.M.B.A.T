package com.stereowalker.combat.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class CustomArrow extends AbstractArrow {
	protected CustomArrow(EntityType<? extends AbstractArrow> type, Level worldIn) {
		super(type, worldIn);
	}

	protected CustomArrow(EntityType<? extends AbstractArrow> type, double x, double y, double z, Level worldIn) {
		this(type, worldIn);
		this.setPos(x, y, z);
	}

	protected CustomArrow(EntityType<? extends AbstractArrow> type, LivingEntity shooter, Level worldIn) {
		this(type, shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ(), worldIn);
		this.setOwner(shooter);
		if (shooter instanceof Player) {
			this.pickup = AbstractArrow.Pickup.ALLOWED;
		}

	}

	public abstract double flightDrop();

	@Override
	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		if (!this.level.isClientSide) {
			this.setSharedFlag(6, this.isCurrentlyGlowing());
		}

		this.baseTick();
		boolean flag = this.isNoPhysics();
		Vec3 vec3 = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double f = vec3.horizontalDistance();
			this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
			this.setXRot((float)(Mth.atan2(vec3.y, (double)f) * (double)(180F / (float)Math.PI)));
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

		if (this.shakeTime > 0) {
			--this.shakeTime;
		}

		if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW)) {
			this.clearFire();
		}

		if (this.inGround && !flag) {
			if (this.lastState != blockstate && this.shouldFall()) {
				this.startFalling();
			} else if (!this.level.isClientSide) {
				this.tickDespawn();
			}

			++this.inGroundTime;
		} else {
			this.inGroundTime = 0;
			Vec3 Vector3d2 = this.position();
			Vec3 Vector3d3 = Vector3d2.add(vec3);
			HitResult raytraceresult = this.level.clip(new ClipContext(Vector3d2, Vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (raytraceresult.getType() != HitResult.Type.MISS) {
				Vector3d3 = raytraceresult.getLocation();
			}

			while(!this.isRemoved()) {
				EntityHitResult entityraytraceresult = this.findHitEntity(Vector3d2, Vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult)raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.hasImpulse = true;
				}

				if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
			}

			vec3 = this.getDeltaMovement();
			double d3 = vec3.x;
			double d4 = vec3.y;
			double d0 = vec3.z;
			if (this.isCritArrow()) {
				for(int i = 0; i < 4; ++i) {
					this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = this.getX() + d3;
			double d1 = this.getY() + d4;
			double d2 = this.getZ() + d0;
			double f = vec3.horizontalDistance();
			if (flag) {
				this.setYRot((float)(Mth.atan2(-d3, -d0) * (double)(180F / (float)Math.PI)));
			} else {
				this.setYRot((float)(Mth.atan2(d3, d0) * (double)(180F / (float)Math.PI)));
			}

			this.setXRot((float)(Mth.atan2(d4, f) * (double)(180F / (float)Math.PI)));
			this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
			float f2 = 0.99F;
			float f3 = 0.05F;
			if (this.isInWater()) {
				for(int j = 0; j < 4; ++j) {
					float f4 = 0.25F;
					this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * f4, d1 - d4 * f4, d2 - d0 * f4, d3, d4, d0);
				}

				f2 = this.getWaterInertia();
			}

			this.setDeltaMovement(vec3.scale((double)f2));
			if (!this.isNoGravity() && !flag) {
				Vec3 Vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(Vector3d4.x, Vector3d4.y - ((double)f3 - flightDrop()), Vector3d4.z);
			}

			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}
	}
}
