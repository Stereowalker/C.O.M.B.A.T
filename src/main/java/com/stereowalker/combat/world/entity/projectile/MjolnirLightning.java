package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MjolnirLightning extends AbstractMagicProjectile {
	public MjolnirLightning(EntityType<? extends MjolnirLightning> type, Level worldIn) {
		super(type, worldIn);
	}

	public MjolnirLightning(Level worldIn, LivingEntity throwerIn) {
		super(CEntityType.MJOLNIR_LIGHTNING, throwerIn, worldIn);
	}

	public MjolnirLightning(Level worldIn, double x, double y, double z) {
		super(CEntityType.MJOLNIR_LIGHTNING, x, y, z, worldIn);
	}

	/**
	 * Handler for {@link Level#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for(int i = 0; i < 8; ++i) {
				this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	public void tick() {
		super.tick();
		if(this.inGround) {
			if (this.level instanceof ServerLevel) {
				if (this.level.canSeeSky(this.blockPosition())) {
					LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.level);
					lightningboltentity.moveTo(Vec3.atBottomCenterOf(this.blockPosition()));
					lightningboltentity.setCause(this.getOwner() instanceof ServerPlayer ? (ServerPlayer)this.getOwner() : null);
					this.level.addFreshEntity(lightningboltentity);
				}
			}
			this.discard();
		}
	}

	@Override
	protected void magicHit(LivingEntity living) {
		if (this.level instanceof ServerLevel) {
			living.hurt(DamageSource.thrown(this, this.getOwner()), 1);
			if (this.level.canSeeSky(living.blockPosition())) {
				LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.level);
				lightningboltentity.moveTo(Vec3.atBottomCenterOf(living.blockPosition()));
				lightningboltentity.setCause(this.getOwner() instanceof ServerPlayer ? (ServerPlayer)this.getOwner() : null);
				this.level.addFreshEntity(lightningboltentity);
			}
		}
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.LIGHTNING_BOLT_IMPACT;
	}
}