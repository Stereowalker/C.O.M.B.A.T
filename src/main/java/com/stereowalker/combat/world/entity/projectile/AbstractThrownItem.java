package com.stereowalker.combat.world.entity.projectile;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public abstract class AbstractThrownItem extends CustomArrow {
	private static final EntityDataAccessor<Boolean> HAS_EFFECT = SynchedEntityData.defineId(AbstractThrownItem.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> BOOMERANG_LEVEL = SynchedEntityData.defineId(AbstractThrownItem.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<ItemStack> THROWN_STACK = SynchedEntityData.defineId(AbstractThrownItem.class, EntityDataSerializers.ITEM_STACK);
	private boolean dealtDamage;
	public int returningTicks;

	public AbstractThrownItem(EntityType<? extends AbstractThrownItem> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	public AbstractThrownItem(EntityType<? extends AbstractThrownItem> p_i50148_1_, Level p_i48790_1_, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(p_i50148_1_, entityIn, p_i48790_1_);
		this.entityData.set(THROWN_STACK, thrownStackIn.copy());
		this.entityData.set(BOOMERANG_LEVEL, (byte)CEnchantmentHelper.getBoomerangModifier(thrownStackIn));
		this.entityData.set(HAS_EFFECT, thrownStackIn.hasFoil());
	}

	@OnlyIn(Dist.CLIENT)
	public AbstractThrownItem(EntityType<? extends AbstractThrownItem> p_i50148_1_,Level p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
		super(p_i50148_1_, p_i48791_2_, p_i48791_4_, p_i48791_6_, p_i48791_1_);
	}
	
	@Override
	public double flightDrop() {
		return CEnchantmentHelper.getHollowedFLightModifier(this.entityData.get(THROWN_STACK))*0.01D;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_EFFECT, false);
		this.entityData.define(THROWN_STACK, ItemStack.EMPTY);
		this.entityData.define(BOOMERANG_LEVEL, (byte)0);
	}
	
	public int boomerangAlternative(Entity entity) {
		return 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}

		Entity entity = this.getOwner();
		if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
			int i = this.entityData.get(BOOMERANG_LEVEL) > boomerangAlternative(entity) ? this.entityData.get(BOOMERANG_LEVEL) : boomerangAlternative(entity);
			if (i > 0 && !this.isAcceptibleReturnOwner()) {
				if (!this.level.isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}
				this.discard();
			} else if (i > 0) {
				this.setNoPhysics(true);
				Vec3 Vec3 = new Vec3(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
				this.setPosRaw(this.getX(), this.getY() + Vec3.y * 0.015D * (double)i, this.getZ());
				if (this.level.isClientSide) {
					this.yOld = this.getY();
				}

				double d0 = 0.05D * (double)i;
				this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(Vec3.normalize().scale(d0)));
				if (this.returningTicks == 0) {
					//TODO: Custom boomerang return sound
					this.playSound(SoundEvents.CROSSBOW_SHOOT, 10.0F, 1.0F);
				}

				++this.returningTicks;
			}
		}

		super.tick();
	}

	private boolean isAcceptibleReturnOwner() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayer) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return this.entityData.get(THROWN_STACK);
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 p_213866_1_, Vec3 p_213866_2_) {
		return this.dealtDamage ? null : super.findHitEntity(p_213866_1_, p_213866_2_);
	}

	@Override
	protected void onHitEntity(EntityHitResult p_213868_1_) {
		Entity entity = p_213868_1_.getEntity();
		float f;
		if (this.entityData.get(THROWN_STACK).getItem() instanceof TieredItem) {
			f = 4.0F + ((TieredItem)this.entityData.get(THROWN_STACK).getItem()).getTier().getAttackDamageBonus() + (float)(CEnchantmentHelper.getPenetrationModifier(this.entityData.get(THROWN_STACK))/2);
		} else {
			f = 4.0F;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity;
			f += EnchantmentHelper.getDamageBonus(this.entityData.get(THROWN_STACK), livingentity.getMobType());
		}

		Entity entity1 = this./*getOwner*/getOwner();
		DamageSource damagesource = DamageSource.arrow(this, (Entity)(entity1 == null ? this : entity1));
		this.dealtDamage = true;
		SoundEvent soundevent = SoundEvents.ARROW_HIT;
		if (entity.hurt(damagesource, f) && entity instanceof LivingEntity) {
			LivingEntity livingentity1 = (LivingEntity)entity;
			if (entity1 instanceof LivingEntity) {
				EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
				EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
			}

			this.doPostHurtEffects(livingentity1);
		}

		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
		float f1 = 1.0F;
		this.playSound(soundevent, f1, 1.0F);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void playerTouch(Player entityIn) {
		Entity entity = this.getOwner();
		if (entity == null || entity.getUUID() == entityIn.getUUID()) {
			super.playerTouch(entityIn);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Throwable", 10)) {
			this.entityData.set(THROWN_STACK, ItemStack.of(compound.getCompound("Throwable")));
		}
		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.entityData.set(BOOMERANG_LEVEL, (byte)CEnchantmentHelper.getBoomerangModifier(ItemStack.of(compound.getCompound("Throwable"))));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("Throwable", this.entityData.get(THROWN_STACK).save(new CompoundTag()));
		compound.putBoolean("DealtDamage", this.dealtDamage);
	}

	@Override
	public void tickDespawn() {
		int i = this.entityData.get(BOOMERANG_LEVEL);
		if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
			super.tickDespawn();
		}

	}

	@Override
	protected float getWaterInertia() {
		return 0.99F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isFoil() {
		return this.entityData.get(HAS_EFFECT);
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return this.entityData.get(THROWN_STACK);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key)
	{
		if (THROWN_STACK.equals(key))
		{
			this.entityData.set(THROWN_STACK, this.entityData.get(THROWN_STACK));
		}
		super.onSyncedDataUpdated(key);
	}
}