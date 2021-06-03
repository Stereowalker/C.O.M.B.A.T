package com.stereowalker.combat.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.item.ItemFilters;
import com.stereowalker.combat.stats.CStats;

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
	
	private float storedDamage = 0.0f;
	/**
	 * Stores the amount of damage the player takes into a variable since @ModifyVariable doesn't use locals
	 * @param source
	 * @param amount
	 * @param cir
	 * @param f
	 * @param flag
	 * @param f1
	 */
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;isProjectile()Z", shift = Shift.BY, by = -4), method = "attackEntityFrom", locals = LocalCapture.CAPTURE_FAILHARD)
	public void blockWithSword(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean flag, float f1) {
		storedDamage = f1;
	}
	
	/**
	 * If we sword block, divide the damage we stored recently by 2
	 * @param amount
	 * @return
	 */
	@ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;isProjectile()Z", shift = Shift.BY, by = -3), method = "attackEntityFrom", /*name = "amount"*/ ordinal = 0, print = false)
	public float blockWithSword(float amount) {
		if (ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
			return storedDamage/2;
		} else {
			return amount;
		}
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;blockUsingShield(Lnet/minecraft/entity/LivingEntity;)V"), method = "attackEntityFrom")
	public void applyKnockback(LivingEntity us, LivingEntity entity) {
		if (ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
			this.blockUsingSword(entity);
		} else {
			this.blockUsingShield(entity);
		}
	}
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/criterion/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/entity/player/ServerPlayerEntity;Lnet/minecraft/util/DamageSource;FFZ)V", shift = Shift.AFTER), method = "attackEntityFrom", locals = LocalCapture.CAPTURE_FAILHARD)
	public void blockStats(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean flag, float f1, boolean flag1, Entity entity1, boolean flag2) {
		if (amount > 0.0F && amount < 3.4028235E37F && ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
			((ServerPlayerEntity)(Object)this).addStat(CStats.DAMAGE_BLOCKED_BY_WEAPON, Math.round(amount * 10.0F));
		}
	}

	protected void blockUsingSword(LivingEntity entityIn) {
		this.applyKnockback(0.75F, entityIn.getPosX() - this.getPosX(), entityIn.getPosZ() - this.getPosZ());
	}
}
