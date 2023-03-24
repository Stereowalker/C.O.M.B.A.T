package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.sounds.CSoundEvents;
import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class Bullet extends AbstractArrow {
	private int duration = 200;

	public Bullet(EntityType<? extends Bullet> type, Level worldIn) {
		super(type, worldIn);
	}

	public Bullet(Level worldIn, LivingEntity shooter) {
		super(CEntityType.BULLET, shooter, worldIn);
	}

	public Bullet(Level worldIn, double x, double y, double z) {
		super(CEntityType.BULLET, x, y, z, worldIn);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide && !this.inGround) {
			this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}

	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return CSoundEvents.ENTITY_BULLET_HIT;
	}

	@Override
	protected void tickDespawn() {
		if (this.inGround) {
			this.discard();
		}
	}

	@Override
	protected void onHit(HitResult raytraceResultIn) {
		HitResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == HitResult.Type.BLOCK) {
			BlockHitResult blockraytraceresult = (BlockHitResult)raytraceResultIn;
			BlockState blockstate = this.level.getBlockState(blockraytraceresult.getBlockPos());
			blockstate.onProjectileHit(this.level, blockstate, blockraytraceresult, this);
			if (blockstate.getBlock() instanceof GlassBlock || blockstate.getBlock() instanceof IceBlock) {
				if(blockstate.getBlock() instanceof GlassBlock)this.level.setBlockAndUpdate(blockraytraceresult.getBlockPos(), Blocks.AIR.defaultBlockState());
				if(blockstate.getBlock() instanceof IceBlock)this.level.setBlockAndUpdate(blockraytraceresult.getBlockPos(), Blocks.WATER.defaultBlockState());
				this.level.playSound((Player)null, blockraytraceresult.getBlockPos(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 3.0F, 1.0F);
			}
		}
		super.onHit(raytraceResultIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}

	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Duration", this.duration);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}