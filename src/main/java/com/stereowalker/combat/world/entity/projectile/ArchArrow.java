package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.ArchSourceItem.ArchType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class ArchArrow extends AbstractArrow {
	private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(ArchArrow.class, EntityDataSerializers.INT);
	private int duration = 10;
	private int fuse = 20;

	public ArchArrow(EntityType<? extends ArchArrow> type, Level worldIn) {
		super(type, worldIn);
	}

	public ArchArrow(Level worldIn, LivingEntity shooter, ArchType type) {
		super(CEntityType.ARCH_ARROW, shooter, worldIn);
		this.setArchType(type);
	}

	public ArchArrow(Level worldIn, double x, double y, double z, ArchType type) {
		super(CEntityType.ARCH_ARROW, x, y, z, worldIn);
		this.setArchType(type);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TYPE, ArchType.FLAME.ordinal());
	}

	public void setArchType(ArchType type) {
		this.entityData.set(TYPE, type.ordinal());
	}

	public ArchType getArchType() {
		int id = this.entityData.get(TYPE);
		ArchType[] type = ArchType.values();
		if (id < 0 || id >= type.length) {
			id = 0;
		}
		return type[id];
	}

	@Override
	public void tick() {
		super.tick();
		LivingEntity livingentity = (LivingEntity) this.getOwner();
		if (this.level.isClientSide && !this.inGround) {
			this.level.addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
		if (this.getArchType() == ArchType.EXPLOSIVE) {
			if (this.inGround) {
				--this.fuse;
				if (this.fuse <= 0) {
					this.discard();
					if (!this.level.isClientSide) {
						this.explode();
					}
				} else {
//					this.handleWaterMovement();
				}
			}
		} 
		if (this.getArchType() == ArchType.FLAME) {
			if (this.inGround) {
//				this.handleWaterMovement();
				BlockPos blockpos = this.blockPosition();
//				if (BaseFireBlock.canLightBlock(this.world, blockpos, null)) {
//					BlockState iblockstate = FireBlock.getState(this.world, blockpos);
//					this.world.setBlockState(blockpos, iblockstate, 11);
//				}
				BlockState blockstate = BaseFireBlock.getState(this.level, blockpos);
				if (this.level.getBlockState(blockpos).isAir() && blockstate.canSurvive(this.level, blockpos)) {
					this.level.setBlockAndUpdate(blockpos, blockstate);
				}
			}
		} 
		if (this.getArchType() == ArchType.FREEZE) {
			if (this.inGround) {
//				this.handleWaterMovement();
			}
			if(this.wasTouchingWater) {
				BlockPos blockpos = new BlockPos(this.xo, this.yo, this.zo);
				BlockState iblockstate = Blocks.ICE.defaultBlockState();
				this.level.setBlock(blockpos, iblockstate, 11);
			}
		} 
		if (this.getArchType() == ArchType.TELEPORT) {
			if (this.inGround) {
//				this.handleWaterMovement();
				if (livingentity != null) {
					livingentity.teleportTo(this.getX(), this.getY(), this.getZ());
					livingentity.fallDistance = 0.0F;
					this.discard();
				}
			}
		}
	}

	private void explode() {
		this.level.explode(this, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), 2.5F, true, Explosion.BlockInteraction.DESTROY);
	}
	private void explodeTarget(LivingEntity target) {
		this.level.explode(target, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), 2.5F, true, Explosion.BlockInteraction.DESTROY);
	}

//	public static boolean canIgnite(IWorld p_201825_0_, BlockPos p_201825_1_) {
//		BlockState iblockstate = FireBlock.getState(p_201825_0_, p_201825_1_);
//		boolean flag = false;
//
//		for(Direction enumfacing : Direction.Plane.HORIZONTAL) {
//			if (p_201825_0_.getBlockState(p_201825_1_.offset(enumfacing)).getBlock() == Blocks.OBSIDIAN && NetherPortalBlock.isPortal(p_201825_0_, p_201825_1_) != null) {
//				flag = true;
//			}
//		}
//
//		return p_201825_0_.isAirBlock(p_201825_1_) && (iblockstate.canSurvive(p_201825_0_, p_201825_1_) || flag);
//	}

	@Override
	protected ItemStack getPickupItem() {
		return null;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		LivingEntity livingentity = (LivingEntity) this.getOwner();
		if (this.getArchType() == ArchType.EXPLOSIVE) {
			if(!(living instanceof EnderDragon) || !(living instanceof WitherBoss)) {
				living.setHealth(living.getHealth() - 5.0F);
			}
			this.explodeTarget(living);
		}
		if (this.getArchType() == ArchType.FLAME) {
			living.setSecondsOnFire(duration);
		} 
		if (this.getArchType() == ArchType.FREEZE) {
			MobEffectInstance potioneffect = new MobEffectInstance(CMobEffects.PARALYSIS, this.duration * 20, 0);
			living.addEffect(potioneffect);
		}
		if (this.getArchType() == ArchType.TELEPORT) {
//			this.handleWaterMovement();
			if (livingentity != null) {
				livingentity.teleportTo(living.xo, living.yo, living.zo);
				livingentity.fallDistance = 0.0F;
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}
		if (compound.contains("Type")) {
			for (ArchType type : ArchType.values()) {
				if (type.ordinal() == this.entityData.get(TYPE)) {
					this.setArchType(type);
				}
			}

		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Duration", this.duration);
		compound.putInt("Type", this.getArchType().ordinal());	
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}