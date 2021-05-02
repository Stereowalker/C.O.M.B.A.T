package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.ArchSourceItem.ArchType;
import com.stereowalker.combat.potion.CEffects;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ArchArrowEntity extends AbstractArrowEntity {
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(ArchArrowEntity.class, DataSerializers.VARINT);
	private int duration = 10;
	private int fuse = 20;

	public ArchArrowEntity(EntityType<? extends ArchArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public ArchArrowEntity(World worldIn, LivingEntity shooter, ArchType type) {
		super(CEntityType.ARCH_ARROW, shooter, worldIn);
		this.setArchType(type);
	}

	public ArchArrowEntity(World worldIn, double x, double y, double z, ArchType type) {
		super(CEntityType.ARCH_ARROW, x, y, z, worldIn);
		this.setArchType(type);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(TYPE, ArchType.FLAME.ordinal());
	}

	public void setArchType(ArchType type) {
		this.dataManager.set(TYPE, type.ordinal());
	}

	public ArchType getArchType() {
		int id = this.dataManager.get(TYPE);
		ArchType[] type = ArchType.values();
		if (id < 0 || id >= type.length) {
			id = 0;
		}
		return type[id];
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		LivingEntity livingentity = (LivingEntity) this.getShooter();
		if (this.world.isRemote && !this.inGround) {
			this.world.addParticle(ParticleTypes.CRIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
		}
		if (this.getArchType() == ArchType.EXPLOSIVE) {
			if (this.inGround) {
				--this.fuse;
				if (this.fuse <= 0) {
					this.remove();
					if (!this.world.isRemote) {
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
				BlockPos blockpos = this.getPosition();
//				if (AbstractFireBlock.canLightBlock(this.world, blockpos, null)) {
//					BlockState iblockstate = FireBlock.getFireForPlacement(this.world, blockpos);
//					this.world.setBlockState(blockpos, iblockstate, 11);
//				}
				BlockState blockstate = AbstractFireBlock.getFireForPlacement(this.world, blockpos);
				if (this.world.getBlockState(blockpos).isAir() && blockstate.isValidPosition(this.world, blockpos)) {
					this.world.setBlockState(blockpos, blockstate);
				}
			}
		} 
		if (this.getArchType() == ArchType.FREEZE) {
			if (this.inGround) {
//				this.handleWaterMovement();
			}
			if(this.inWater) {
				BlockPos blockpos = new BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ);
				BlockState iblockstate = Blocks.ICE.getDefaultState();
				this.world.setBlockState(blockpos, iblockstate, 11);
			}
		} 
		if (this.getArchType() == ArchType.TELEPORT) {
			if (this.inGround) {
//				this.handleWaterMovement();
				if (livingentity != null) {
					livingentity.setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());
					livingentity.fallDistance = 0.0F;
					this.remove();
				}
			}
		}
	}

	private void explode() {
		this.world.createExplosion(this, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 16.0F), this.getPosZ(), 2.5F, true, Mode.DESTROY);
	}
	private void explodeTarget(LivingEntity target) {
		this.world.createExplosion(target, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 16.0F), this.getPosZ(), 2.5F, true, Mode.DESTROY);
	}

//	public static boolean canIgnite(IWorld p_201825_0_, BlockPos p_201825_1_) {
//		BlockState iblockstate = FireBlock.getFireForPlacement(p_201825_0_, p_201825_1_);
//		boolean flag = false;
//
//		for(Direction enumfacing : Direction.Plane.HORIZONTAL) {
//			if (p_201825_0_.getBlockState(p_201825_1_.offset(enumfacing)).getBlock() == Blocks.OBSIDIAN && NetherPortalBlock.isPortal(p_201825_0_, p_201825_1_) != null) {
//				flag = true;
//			}
//		}
//
//		return p_201825_0_.isAirBlock(p_201825_1_) && (iblockstate.isValidPosition(p_201825_0_, p_201825_1_) || flag);
//	}

	protected ItemStack getArrowStack() {
		return null;
	}

	protected void arrowHit(LivingEntity living) {
		super.arrowHit(living);
		LivingEntity livingentity = (LivingEntity) this.getShooter();
		if (this.getArchType() == ArchType.EXPLOSIVE) {
			if(!(living instanceof EnderDragonEntity) || !(living instanceof WitherEntity)) {
				living.setHealth(living.getHealth() - 5.0F);
			}
			this.explodeTarget(living);
		}
		if (this.getArchType() == ArchType.FLAME) {
			living.setFire(duration);
		} 
		if (this.getArchType() == ArchType.FREEZE) {
			EffectInstance potioneffect = new EffectInstance(CEffects.PARALYSIS, this.duration * 20, 0);
			living.addPotionEffect(potioneffect);
		}
		if (this.getArchType() == ArchType.TELEPORT) {
//			this.handleWaterMovement();
			if (livingentity != null) {
				livingentity.setPositionAndUpdate(living.prevPosX, living.prevPosY, living.prevPosZ);
				livingentity.fallDistance = 0.0F;
			}
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}
		if (compound.contains("Type")) {
			for (ArchType type : ArchType.values()) {
				if (type.ordinal() == this.dataManager.get(TYPE)) {
					this.setArchType(type);
				}
			}

		}
	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("Duration", this.duration);
		compound.putInt("Type", this.getArchType().ordinal());	
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}