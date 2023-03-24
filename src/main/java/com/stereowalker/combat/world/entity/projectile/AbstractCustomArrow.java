package com.stereowalker.combat.world.entity.projectile;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AbstractCustomArrow extends AbstractArrow {
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Arrow.class, EntityDataSerializers.INT);
	private Potion potion = Potions.EMPTY;
	private final Set<MobEffectInstance> customPotionEffects = Sets.newHashSet();
	private boolean fixedColor;
	public abstract Item arrowItem();
	public abstract Item tippedArrowItem();

	public AbstractCustomArrow(EntityType<? extends AbstractCustomArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public AbstractCustomArrow(EntityType<? extends AbstractCustomArrow> entityIn, Level worldIn, double x, double y, double z) {
		super(entityIn, x, y, z, worldIn);
	}

	public AbstractCustomArrow(EntityType<? extends AbstractCustomArrow> entityIn, Level worldIn, LivingEntity shooter) {
		super(entityIn, shooter, worldIn);
	}
	

	public void setPotionEffect(ItemStack stack) {
		if (stack.getItem() == tippedArrowItem()) {
			this.potion = PotionUtils.getPotion(stack);
			Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(stack);
			if (!collection.isEmpty()) {
				for(MobEffectInstance effectinstance : collection) {
					this.customPotionEffects.add(new MobEffectInstance(effectinstance));
				}
			}

			int i = getCustomColor(stack);
			if (i == -1) {
				this.refreshColor();
			} else {
				this.setFixedColor(i);
			}
		} else if (stack.getItem() == arrowItem()) {
			this.potion = Potions.EMPTY;
			this.customPotionEffects.clear();
			this.entityData.set(COLOR, -1);
		}

	}

	public static int getCustomColor(ItemStack p_191508_0_) {
		CompoundTag compoundnbt = p_191508_0_.getTag();
		return compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99) ? compoundnbt.getInt("CustomPotionColor") : -1;
	}

	private void refreshColor() {
		this.fixedColor = false;
		if (this.potion == Potions.EMPTY && this.customPotionEffects.isEmpty()) {
			this.entityData.set(COLOR, -1);
		} else {
			this.entityData.set(COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
		}

	}

	public void addEffect(MobEffectInstance effect) {
		this.customPotionEffects.add(effect);
		this.getEntityData().set(COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(COLOR, -1);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			if (this.inGround) {
				if (this.inGroundTime % 5 == 0) {
					this.spawnPotionParticles(1);
				}
			} else {
				this.spawnPotionParticles(2);
			}
		} else if (this.inGround && this.inGroundTime != 0 && !this.customPotionEffects.isEmpty() && this.inGroundTime >= 600) {
			this.level.broadcastEntityEvent(this, (byte)0);
			this.potion = Potions.EMPTY;
			this.customPotionEffects.clear();
			this.entityData.set(COLOR, -1);
		}

	}

	private void spawnPotionParticles(int particleCount) {
		int i = this.getColor();
		if (i != -1 && particleCount > 0) {
			double d0 = (double)(i >> 16 & 255) / 255.0D;
			double d1 = (double)(i >> 8 & 255) / 255.0D;
			double d2 = (double)(i >> 0 & 255) / 255.0D;

			for(int j = 0; j < particleCount; ++j) {
				this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
			}

		}
	}

	public int getColor() {
		return this.entityData.get(COLOR);
	}

	private void setFixedColor(int p_191507_1_) {
		this.fixedColor = true;
		this.entityData.set(COLOR, p_191507_1_);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.potion != Potions.EMPTY && this.potion != null) {
			compound.putString("Potion", ForgeRegistries.POTIONS.getKey(this.potion).toString());
		}

		if (this.fixedColor) {
			compound.putInt("Color", this.getColor());
		}

		if (!this.customPotionEffects.isEmpty()) {
			ListTag listnbt = new ListTag();

			for(MobEffectInstance effectinstance : this.customPotionEffects) {
				listnbt.add(effectinstance.save(new CompoundTag()));
			}

			compound.put("CustomPotionEffects", listnbt);
		}

	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Potion", 8)) {
			this.potion = PotionUtils.getPotion(compound);
		}

		for(MobEffectInstance effectinstance : PotionUtils.getCustomEffects(compound)) {
			this.addEffect(effectinstance);
		}

		if (compound.contains("Color", 99)) {
			this.setFixedColor(compound.getInt("Color"));
		} else {
			this.refreshColor();
		}

	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);

		for(MobEffectInstance effectinstance : this.potion.getEffects()) {
			living.addEffect(new MobEffectInstance(effectinstance.getEffect(), Math.max(effectinstance.getDuration() / 8, 1), effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
		}

		if (!this.customPotionEffects.isEmpty()) {
			for(MobEffectInstance effectinstance1 : this.customPotionEffects) {
				living.addEffect(effectinstance1);
			}
		}

	}

	@Override
	protected ItemStack getPickupItem() {
		if (this.customPotionEffects.isEmpty() && this.potion == Potions.EMPTY) {
			return new ItemStack(arrowItem());
		} else {
			ItemStack itemstack = new ItemStack(tippedArrowItem());
			PotionUtils.setPotion(itemstack, this.potion);
			PotionUtils.setCustomEffects(itemstack, this.customPotionEffects);
			if (this.fixedColor) {
				itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
			}

			return itemstack;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 0) {
			int i = this.getColor();
			if (i != -1) {
				double d0 = (double)(i >> 16 & 255) / 255.0D;
				double d1 = (double)(i >> 8 & 255) / 255.0D;
				double d2 = (double)(i >> 0 & 255) / 255.0D;

				for(int j = 0; j < 20; ++j) {
					this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
				}
			}
		} else {
			super.handleEntityEvent(id);
		}

	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
