package com.stereowalker.combat.entity.projectile;

import javax.annotation.Nullable;

import com.stereowalker.combat.enchantment.CEnchantmentHelper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractThrowableItemEntity extends CustomArrowEntity {
	private static final DataParameter<Boolean> HAS_EFFECT = EntityDataManager.createKey(AbstractThrowableItemEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Byte> BOOMERANG_LEVEL = EntityDataManager.createKey(AbstractThrowableItemEntity.class, DataSerializers.BYTE);
	private static final DataParameter<ItemStack> THROWN_STACK = EntityDataManager.createKey(AbstractThrowableItemEntity.class, DataSerializers.ITEMSTACK);
	private boolean dealtDamage;
	public int returningTicks;

	public AbstractThrowableItemEntity(EntityType<? extends AbstractThrowableItemEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public AbstractThrowableItemEntity(EntityType<? extends AbstractThrowableItemEntity> p_i50148_1_, World p_i48790_1_, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(p_i50148_1_, entityIn, p_i48790_1_);
		this.dataManager.set(THROWN_STACK, thrownStackIn.copy());
		this.dataManager.set(BOOMERANG_LEVEL, (byte)CEnchantmentHelper.getBoomerangModifier(thrownStackIn));
		this.dataManager.set(HAS_EFFECT, thrownStackIn.hasEffect());
	}

	@OnlyIn(Dist.CLIENT)
	public AbstractThrowableItemEntity(EntityType<? extends AbstractThrowableItemEntity> p_i50148_1_,World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
		super(p_i50148_1_, p_i48791_2_, p_i48791_4_, p_i48791_6_, p_i48791_1_);
	}
	
	@Override
	public double flightDrop() {
		return CEnchantmentHelper.getHollowedFLightModifier(this.dataManager.get(THROWN_STACK))*0.01D;
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(HAS_EFFECT, false);
		this.dataManager.register(THROWN_STACK, ItemStack.EMPTY);
		this.dataManager.register(BOOMERANG_LEVEL, (byte)0);
	}
	
	public int boomerangAlternative(Entity entity) {
		return 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		if (this.timeInGround > 4) {
			this.dealtDamage = true;
		}

		Entity entity = this./*getShooter*/getShooter();
		if ((this.dealtDamage || this.getNoClip()) && entity != null) {
			int i = this.dataManager.get(BOOMERANG_LEVEL) > boomerangAlternative(entity) ? this.dataManager.get(BOOMERANG_LEVEL) : boomerangAlternative(entity);
			if (i > 0 && !this.shouldReturnToThrower()) {
				if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
					this.entityDropItem(this.getArrowStack(), 0.1F);
				}

				this.remove();
			} else if (i > 0) {
				this.setNoClip(true);
				Vector3d Vector3d = new Vector3d(entity.getPosX() - this.getPosX(), entity.getPosYEye() - this.getPosY(), entity.getPosZ() - this.getPosZ());
				this.setRawPosition(this.getPosX(), this.getPosY() + Vector3d.y * 0.015D * (double)i, this.getPosZ());
				if (this.world.isRemote) {
					this.lastTickPosY = this.getPosY();
				}

				double d0 = 0.05D * (double)i;
				this.setMotion(this.getMotion().scale(0.95D).add(Vector3d.normalize().scale(d0)));
				if (this.returningTicks == 0) {
					//TODO: Custom boomerang return sound
					this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 10.0F, 1.0F);
				}

				++this.returningTicks;
			}
		}

		super.tick();
	}

	private boolean shouldReturnToThrower() {
		Entity entity = this./*getShooter*/getShooter();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	protected ItemStack getArrowStack() {
		return this.dataManager.get(THROWN_STACK);
	}

	@Nullable
	protected EntityRayTraceResult rayTraceEntities(Vector3d p_213866_1_, Vector3d p_213866_2_) {
		return this.dealtDamage ? null : super.rayTraceEntities(p_213866_1_, p_213866_2_);
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
		Entity entity = p_213868_1_.getEntity();
		float f;
		if (this.dataManager.get(THROWN_STACK).getItem() instanceof TieredItem) {
			f = 4.0F + ((TieredItem)this.dataManager.get(THROWN_STACK).getItem()).getTier().getAttackDamage() + (float)(CEnchantmentHelper.getPenetrationModifier(this.dataManager.get(THROWN_STACK))/2);
		} else {
			f = 4.0F;
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity;
			f += EnchantmentHelper.getModifierForCreature(this.dataManager.get(THROWN_STACK), livingentity.getCreatureAttribute());
		}

		Entity entity1 = this./*getShooter*/getShooter();
		DamageSource damagesource = DamageSource.causeArrowDamage(this, (Entity)(entity1 == null ? this : entity1));
		this.dealtDamage = true;
		SoundEvent soundevent = SoundEvents.ENTITY_ARROW_HIT;
		if (entity.attackEntityFrom(damagesource, f) && entity instanceof LivingEntity) {
			LivingEntity livingentity1 = (LivingEntity)entity;
			if (entity1 instanceof LivingEntity) {
				EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
				EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity1);
			}

			this.arrowHit(livingentity1);
		}

		this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
		float f1 = 1.0F;
		this.playSound(soundevent, f1, 1.0F);
	}

	@Override
	protected SoundEvent getHitEntitySound() {
		return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(PlayerEntity entityIn) {
		Entity entity = this.getShooter();
		if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
			super.onCollideWithPlayer(entityIn);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Throwable", 10)) {
			this.dataManager.set(THROWN_STACK, ItemStack.read(compound.getCompound("Throwable")));
		}
		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.dataManager.set(BOOMERANG_LEVEL, (byte)CEnchantmentHelper.getBoomerangModifier(ItemStack.read(compound.getCompound("Throwable"))));
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("Throwable", this.dataManager.get(THROWN_STACK).write(new CompoundNBT()));
		compound.putBoolean("DealtDamage", this.dealtDamage);
	}

	@Override
	public void func_225516_i_() {
		int i = this.dataManager.get(BOOMERANG_LEVEL);
		if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
			super.func_225516_i_();
		}

	}

	protected float getWaterDrag() {
		return 0.99F;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_226572_w_() {
		return this.dataManager.get(HAS_EFFECT);
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return this.dataManager.get(THROWN_STACK);
	}

	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (THROWN_STACK.equals(key))
		{
			this.dataManager.set(THROWN_STACK, this.dataManager.get(THROWN_STACK));
		}
		super.notifyDataManagerChange(key);
	}
}