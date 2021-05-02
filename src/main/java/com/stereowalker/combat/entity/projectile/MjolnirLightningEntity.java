package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class MjolnirLightningEntity extends AbstractMagicProjectileEntity {
	public MjolnirLightningEntity(EntityType<? extends MjolnirLightningEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public MjolnirLightningEntity(World worldIn, LivingEntity throwerIn) {
		super(CEntityType.MJOLNIR_LIGHTNING, throwerIn, worldIn);
	}

	public MjolnirLightningEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.MJOLNIR_LIGHTNING, x, y, z, worldIn);
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	public void tick() {
		super.tick();
		if(this.inGround) {
			if (this.world instanceof ServerWorld) {
				if (this.world.canSeeSky(this.getPosition())) {
					LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
					lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(this.getPosition()));
					lightningboltentity.setCaster(this.getShooter() instanceof ServerPlayerEntity ? (ServerPlayerEntity)this.getShooter() : null);
					this.world.addEntity(lightningboltentity);
				}
			}
			this.remove();
		}
	}

	@Override
	protected void magicHit(LivingEntity living) {
		if (this.world instanceof ServerWorld) {
			living.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getShooter()), 1);
			if (this.world.canSeeSky(living.getPosition())) {
				LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
				lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(living.getPosition()));
				lightningboltentity.setCaster(this.getShooter() instanceof ServerPlayerEntity ? (ServerPlayerEntity)this.getShooter() : null);
				this.world.addEntity(lightningboltentity);
			}
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT;
	}
}