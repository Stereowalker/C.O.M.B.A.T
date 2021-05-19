package com.stereowalker.combat.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.item.ItemFilters;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.EquipmentSlotType.Group;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	private long lastDamageStamp;
	@Shadow
	private DamageSource lastDamageSource;
	@Shadow
	public float attackedAtYaw;
	@Shadow
	@Nullable
	protected PlayerEntity attackingPlayer;
	@Shadow
	protected int recentlyHit;
	@Shadow
	public int hurtTime;
	@Shadow
	public int maxHurtTime;
	/** Damage taken in the last hit. Mobs are resistant to damage less than this for a short time after taking damage. */
	@Shadow
	protected float lastDamage;
	@Shadow
	public float limbSwingAmount;
	@Shadow
	protected int idleTime;

	public LivingEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Shadow
	public abstract ItemStack getItemStackFromSlot(EquipmentSlotType slotIn);

	@Shadow
	public boolean isPotionActive(Effect potionIn) {return true;}

	@Shadow
	public double getAttributeValue(Attribute attribute) {return 0;}

	/**
	 * returns the PotionEffect for the supplied Potion if it is active, null otherwise.
	 */
	@Nullable
	@Shadow
	public EffectInstance getActivePotionEffect(Effect potionIn) {return null;}

	@Shadow
	public boolean getShouldBeDead() {return true;}

	@Shadow
	/**
	 * Returns whether player is sleeping or not
	 */
	public boolean isSleeping() {return true;}

	@Shadow
	protected void playHurtSound(DamageSource source) {};

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Shadow
	public void onDeath(DamageSource cause) {}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Shadow
	protected float getSoundPitch() {return 0;}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Shadow
	protected float getSoundVolume() {return 0;}

	@Nullable
	@Shadow
	protected SoundEvent getDeathSound() {return null;}

	@Shadow
	private boolean checkTotemDeathProtection(DamageSource damageSourceIn) {return true;}

	@Shadow
	public void applyKnockback(float strength, double ratioX, double ratioZ) {}

	/**
	 * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
	 * change our actual active target (for example if we are currently busy attacking someone else)
	 */
	@Shadow
	public void setRevengeTarget(@Nullable LivingEntity livingBase) {}

	/**
	 * Deals damage to the entity. This will take the armor of the entity into consideration before damaging the health
	 * bar.
	 */
	@Shadow
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {}

	@Shadow
	protected void blockUsingShield(LivingEntity entityIn) {}

	@Shadow
	protected void damageShield(float damage) {}

	/**
	 * Determines whether the entity can block the damage source based on the damage source's location, whether the
	 * damage source is blockable, and whether the entity is blocking.
	 */
	@Shadow
	private boolean canBlockDamageSource(DamageSource damageSourceIn) {return true;}
	
	@Shadow
	public void wakeUp() {}

	/**
	 * Reduces damage, depending on potions
	 * @reason To use attributes for resistance
	 * @author Stereowalker
	 */
	@Overwrite
	protected float applyPotionDamageCalculations(DamageSource source, float damage) {
		boolean useVanilla = false;
		if (source.isDamageAbsolute()) {
			return damage;
		} else {
			//Not Vanilla
			float percentageAbsorption = 0;
			for (EquipmentSlotType type : EquipmentSlotType.values()) {
				if (type.getSlotType() == Group.ARMOR && CEnchantmentHelper.getAbsorptionModifier(this.getItemStackFromSlot(type)) > 0) {
					percentageAbsorption += (float)CEnchantmentHelper.getAbsorptionModifier(this.getItemStackFromSlot(type))*0.05f;
				}
			}
			float absorbedDamage = damage*percentageAbsorption;
			float alteredDamage = damage - absorbedDamage;
			//
			if ((this.isPotionActive(Effects.RESISTANCE) || !useVanilla) && this.getAttributeValue(CAttributes.PHYSICAL_RESISTANCE) > 0 && source != DamageSource.OUT_OF_WORLD) {
				int i;
				if (useVanilla)
					i = (this.getActivePotionEffect(Effects.RESISTANCE).getAmplifier() + 1) * 5;
				else
					i = MathHelper.ceil(this.getAttributeValue(CAttributes.PHYSICAL_RESISTANCE)) + (Config.RPG_COMMON.enableLevelingSystem.get() ? -10 : 0);
				int j = 25 - i;
				float f = alteredDamage * (float)j;
				float f1 = alteredDamage;
				alteredDamage = Math.max(f / 25.0F, 0.0F);
				float f2 = f1 - alteredDamage;
				if (f2 > 0.0F && f2 < 3.4028235E37F && this.isPotionActive(Effects.RESISTANCE)) {
					if ((Object)this instanceof ServerPlayerEntity) {
						((ServerPlayerEntity)(Object)this).addStat(Stats.DAMAGE_RESISTED, Math.round(f2 * 10.0F));
					} else if (source.getTrueSource() instanceof ServerPlayerEntity) {
						((ServerPlayerEntity)source.getTrueSource()).addStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(f2 * 10.0F));
					}
				}
			}

			if (alteredDamage <= 0.0F) {
				return 0.0F;
			} else {
				int k = EnchantmentHelper.getEnchantmentModifierDamage(this.getArmorInventoryList(), source);
				if (k > 0) {
					alteredDamage = CombatRules.getDamageAfterMagicAbsorb(alteredDamage, (float)k);
				}
				//Not Vanilla
				for (EquipmentSlotType type : EquipmentSlotType.values()) {
					if (type.getSlotType() == Group.ARMOR && CEnchantmentHelper.getAbsorptionModifier(this.getItemStackFromSlot(type)) > 0)
						this.getItemStackFromSlot(type).damageItem((int)Math.ceil((CEnchantmentHelper.getAbsorptionModifier(this.getItemStackFromSlot(type))*0.05f)*damage), (LivingEntity)(Object)this, (e) -> {
							e.sendBreakAnimation(type);
						});
				}
				//
				return alteredDamage;
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 * @reason To apply sword blocking
	 * @author Stereowalker
	 */
	@Overwrite
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!net.minecraftforge.common.ForgeHooks.onLivingAttack((LivingEntity)(Object)this, source, amount)) return false;
		if (this.isInvulnerableTo(source)) {
			return false;
		} else if (this.world.isRemote) {
			return false;
		} else if (this.getShouldBeDead()) {
			return false;
		} else if (source.isFireDamage() && this.isPotionActive(Effects.FIRE_RESISTANCE)) {
			return false;
		} else {
			if (this.isSleeping() && !this.world.isRemote) {
				this.wakeUp();
			}

			this.idleTime = 0;
			float f = amount;
			if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
				this.getItemStackFromSlot(EquipmentSlotType.HEAD).damageItem((int)(amount * 4.0F + this.rand.nextFloat() * amount * 2.0F), (LivingEntity)(Object)this, (p_233653_0_) -> {
					p_233653_0_.sendBreakAnimation(EquipmentSlotType.HEAD);
				});
				amount *= 0.75F;
			}

			boolean flag = false;
			float f1 = 0.0F;
			float swordAbs = 0.0F;
			// Handle Sword Blocking
			if (amount > 0.0F && this.canBlockDamageSource(source) && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)))) {
				this.damageShield(amount);
				swordAbs = amount/2.0F;
				amount = amount/2.0F;
				if (!source.isProjectile()) {
					Entity entity = source.getImmediateSource();
					if (entity instanceof LivingEntity) {
						this.blockUsingSword((LivingEntity)entity);
					}
				}

				flag = true;
			} 
			// Handle Shield Blocking (Vanilla Style)
			else if (amount > 0.0F && this.canBlockDamageSource(source)){
				this.damageShield(amount);
				f1 = amount;
				amount = 0.0F;
				if (!source.isProjectile()) {
					Entity entity = source.getImmediateSource();
					if (entity instanceof LivingEntity) {
						this.blockUsingShield((LivingEntity)entity);
					}
				}

				flag = true;
			}

			this.limbSwingAmount = 1.5F;
			boolean flag1 = true;
			if ((float)this.hurtResistantTime > 10.0F) {
				if (amount <= this.lastDamage) {
					return false;
				}

				this.damageEntity(source, amount - this.lastDamage);
				this.lastDamage = amount;
				flag1 = false;
			} else {
				this.lastDamage = amount;
				this.hurtResistantTime = 20;
				this.damageEntity(source, amount);
				this.maxHurtTime = 10;
				this.hurtTime = this.maxHurtTime;
			}

			this.attackedAtYaw = 0.0F;
			Entity entity1 = source.getTrueSource();
			if (entity1 != null) {
				if (entity1 instanceof LivingEntity) {
					this.setRevengeTarget((LivingEntity)entity1);
				}

				if (entity1 instanceof PlayerEntity) {
					this.recentlyHit = 100;
					this.attackingPlayer = (PlayerEntity)entity1;
				} else if (entity1 instanceof net.minecraft.entity.passive.TameableEntity) {
					net.minecraft.entity.passive.TameableEntity wolfentity = (net.minecraft.entity.passive.TameableEntity)entity1;
					if (wolfentity.isTamed()) {
						this.recentlyHit = 100;
						LivingEntity livingentity = wolfentity.getOwner();
						if (livingentity != null && livingentity.getType() == EntityType.PLAYER) {
							this.attackingPlayer = (PlayerEntity)livingentity;
						} else {
							this.attackingPlayer = null;
						}
					}
				}
			}

			if (flag1) {
				if (flag) {
					this.world.setEntityState(this, (byte)29);
				} else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage()) {
					this.world.setEntityState(this, (byte)33);
				} else {
					byte b0;
					if (source == DamageSource.DROWN) {
						b0 = 36;
					} else if (source.isFireDamage()) {
						b0 = 37;
					} else if (source == DamageSource.SWEET_BERRY_BUSH) {
						b0 = 44;
					} else {
						b0 = 2;
					}

					this.world.setEntityState(this, b0);
				}

				if (source != DamageSource.DROWN && (!flag || amount > 0.0F)) {
					this.markVelocityChanged();
				}

				if (entity1 != null) {
					double d1 = entity1.getPosX() - this.getPosX();

					double d0;
					for(d0 = entity1.getPosZ() - this.getPosZ(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
						d1 = (Math.random() - Math.random()) * 0.01D;
					}

					this.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.rotationYaw);
					this.applyKnockback(0.4F, d1, d0);
				} else {
					this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
				}
			}

			if (this.getShouldBeDead()) {
				if (!this.checkTotemDeathProtection(source)) {
					SoundEvent soundevent = this.getDeathSound();
					if (flag1 && soundevent != null) {
						this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
					}

					this.onDeath(source);
				}
			} else if (flag1) {
				this.playHurtSound(source);
			}

			boolean flag2 = !flag || amount > 0.0F;
			if (flag2) {
				this.lastDamageSource = source;
				this.lastDamageStamp = this.world.getGameTime();
			}

			if ((Object)this instanceof ServerPlayerEntity) {
				CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)(Object)this, source, f, amount, flag);
				if (f1 > 0.0F && f1 < 3.4028235E37F) {
					((ServerPlayerEntity)(Object)this).addStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(f1 * 10.0F));
				}
				if (swordAbs > 0.0F && swordAbs < 3.4028235E37F) {
					((ServerPlayerEntity)(Object)this).addStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(swordAbs * 10.0F));
				}
			}


			if (entity1 instanceof ServerPlayerEntity) {
				CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)entity1, this, source, f, amount, flag);
			}

			return flag2;
		}
	}

	protected void blockUsingSword(LivingEntity entityIn) {
		entityIn.applyKnockback(0.75F, entityIn.getPosX() - this.getPosX(), entityIn.getPosZ() - this.getPosZ());
	}
}
