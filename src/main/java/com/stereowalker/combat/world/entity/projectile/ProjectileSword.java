package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.world.spellcraft.SpellStats;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProjectileSword extends AbstractMagicProjectile {
	private static final EntityDataAccessor<Boolean> EJECTED_SWORD = SynchedEntityData.defineId(ProjectileSword.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CONNECTED_TO_SHOOTER = SynchedEntityData.defineId(ProjectileSword.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<ItemStack> THROWN_STACK = SynchedEntityData.defineId(ProjectileSword.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<CompoundTag> SPELL = SynchedEntityData.defineId(ProjectileSword.class, EntityDataSerializers.COMPOUND_TAG);
	private static final EntityDataAccessor<Float> POSITION = SynchedEntityData.defineId(ProjectileSword.class, EntityDataSerializers.FLOAT);
	public ProjectileSword(EntityType<? extends ProjectileSword> type, Level worldIn) {
		super(type, worldIn);
		this.entityData.set(CONNECTED_TO_SHOOTER, true);
	}

	public ProjectileSword(Level worldIn, LivingEntity throwerIn) {
		super(CEntityType.PROJECTILE_SWORD, throwerIn, worldIn);
		this.entityData.set(CONNECTED_TO_SHOOTER, true);
	}

	public ProjectileSword(Level worldIn, double x, double y, double z) {
		super(CEntityType.PROJECTILE_SWORD, x, y, z, worldIn);
		this.entityData.set(CONNECTED_TO_SHOOTER, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(EJECTED_SWORD, false);
		this.entityData.define(CONNECTED_TO_SHOOTER, true);
		this.entityData.define(SPELL, new CompoundTag());
		this.entityData.define(THROWN_STACK, new ItemStack(Items.AIR));
		this.entityData.define(POSITION, 1.0F);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for(int i = 0; i < 8; ++i) {
				this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	public boolean hasEjectedSword() {
		return this.entityData.get(EJECTED_SWORD);
	}

	public void setEjectedSword(boolean value) {
		this.entityData.set(EJECTED_SWORD, value);
	}

	public boolean isConnectedToCaster() {
		return this.entityData.get(CONNECTED_TO_SHOOTER);
	}

	public void setConnectedToShooter(boolean value) {
		this.entityData.set(CONNECTED_TO_SHOOTER, value);
	}

	public float getSwordPosition() {
		return this.entityData.get(POSITION);
	}

	public void setSwordPosition(float value) {
		this.entityData.set(POSITION, value);
	}

	public ItemStack getSword() {
		return this.entityData.get(THROWN_STACK);
	}

	public void setSword(ItemStack value) {
		this.entityData.set(THROWN_STACK, value);
	}

	public SpellInstance getSpellInstance() {
		return SpellInstance.read(this.entityData.get(SPELL));
	}

	public void setSpell(SpellInstance value) {
		this.entityData.set(SPELL, value.write(new CompoundTag()));
	}

	@Override
	public void tick() {
		super.tick();
		Player caster = (Player)this.getOwner();
		if (caster != null) {
			if (SpellStats.getSpellPrimed(caster, this.getSpellInstance().getSpell()) && !this.hasEjectedSword()) {
				double radius = 2.0d;
				double angle = (caster.getYRot() + this.getSwordPosition()) % 360;
				double x = caster.getX() + (radius * Math.cos(Math.toRadians(angle)));
				double z = caster.getZ() + (radius * Math.sin(Math.toRadians(angle)));
				this.setPos(x, caster.getY() + 2.5, z);
				this.setRot(-caster.getYRot(), this.getXRot()+10);
			} else if (this.isConnectedToCaster()) {
				if (!this.level.isClientSide) {
					if (caster.isShiftKeyDown())
						this.shoot(caster, caster.getXRot(), caster.getYRot(), 0.0F, -1.0F, 0);
					else
						this.shoot(caster, caster.getXRot(), caster.getYRot(), 0.0F, 1.0F, 0);
				}
				if (this.position().distanceTo(caster.position()) >= 100) {
					this.setConnectedToShooter(false);
				}
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult result) {
		System.out.println("FLying Sow");
		Entity entity = result.getEntity();
		if (entity != this.getOwner()) {
			float f;
			if (this.getSword().getItem() instanceof TieredItem) {
				f = 4.0F + ((TieredItem)this.getSword().getItem()).getTier().getAttackDamageBonus() + (float)(CEnchantmentHelper.getPenetrationModifier(this.getSword())/2);
			} else {
				f = 4.0F;
			}
			if (this.isConnectedToCaster() && entity instanceof LivingEntity) {
				f*=this.getSpellInstance().getStrength();
			}
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity)entity;
				f += EnchantmentHelper.getDamageBonus(this.getSword(), livingentity.getMobType());
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource = CDamageSource.causeMagicProjectileDamage(this, (Entity)(entity1 == null ? this : entity1));
			//		this.dealtDamage = true;
			SoundEvent soundevent = SoundEvents.ARROW_HIT;
			if (entity.hurt(damagesource, f) && entity instanceof LivingEntity) {
				LivingEntity livingentity1 = (LivingEntity)entity;
				if (entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
				}

				this.magicHit(livingentity1);
			}

			this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
			float f1 = 1.0F;
			this.playSound(soundevent, f1, 1.0F);
		}
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.LIGHTNING_BOLT_IMPACT;
	}

	@Override
	void magicHit(LivingEntity living) {
		// TODO Auto-generated method stub

	}
}