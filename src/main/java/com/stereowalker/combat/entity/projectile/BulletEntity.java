package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.util.CSoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BulletEntity extends AbstractArrowEntity {
	private int duration = 200;

	public BulletEntity(EntityType<? extends BulletEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public BulletEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.BULLET, shooter, worldIn);
	}

	public BulletEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.BULLET, x, y, z, worldIn);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		if (this.world.isRemote && !this.inGround) {
			this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
		}

	}

	@Override
	protected SoundEvent getHitEntitySound() {
		return CSoundEvents.ENTITY_BULLET_HIT;
	}

	@Override
	protected void func_225516_i_() {
		if (this.inGround) {
			this.remove();
		}
	}

	@Override
	protected void onImpact(RayTraceResult raytraceResultIn) {
		RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
			BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
			blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
			if (blockstate.getBlock() instanceof GlassBlock || blockstate.getBlock() instanceof IceBlock) {
				if(blockstate.getBlock() instanceof GlassBlock)this.world.setBlockState(blockraytraceresult.getPos(), Blocks.AIR.getDefaultState());
				if(blockstate.getBlock() instanceof IceBlock)this.world.setBlockState(blockraytraceresult.getPos(), Blocks.WATER.getDefaultState());
				this.world.playSound((PlayerEntity)null, blockraytraceresult.getPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 3.0F, 1.0F);
			}
		}
		super.onImpact(raytraceResultIn);
	}

	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}

	protected void arrowHit(LivingEntity living) {
		super.arrowHit(living);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}

	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("Duration", this.duration);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}