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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;
import com.stereowalker.combat.world.item.enchantment.MagmaWalkerEnchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;

/**
 * @author Stereowalker
 *
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slotIn);
	@Shadow public boolean hasEffect(MobEffect potionIn) {return true;}
	@Shadow public double getAttributeValue(Attribute attribute) {return 0;}
	@Shadow @Nullable public AttributeInstance getAttribute(Attribute pAttribute) {return null;}
	/**
	 * returns the PotionEffect for the supplied Potion if it is active, null otherwise.
	 */
	@Nullable @Shadow public MobEffectInstance getEffect(MobEffect potionIn) {return null;}
	@Shadow public void knockback(double strength, double ratioX, double ratioZ) {}
	@Shadow protected void blockUsingShield(LivingEntity entityIn) {}

	/**
	 * Reduces damage, depending on potions
	 * @reason To use attributes for resistance
	 * @author Stereowalker
	 */
	@Overwrite
	protected float getDamageAfterMagicAbsorb(DamageSource source, float damage) {
		boolean useVanilla = false;
		if (source.isBypassMagic()) {
			return damage;
		} else {
			//Not Vanilla
			float percentageAbsorption = 0;
			for (EquipmentSlot type : EquipmentSlot.values()) {
				if (type.getType() == EquipmentSlot.Type.ARMOR && CEnchantmentHelper.getAbsorptionModifier(this.getItemBySlot(type)) > 0) {
					percentageAbsorption += (float)CEnchantmentHelper.getAbsorptionModifier(this.getItemBySlot(type))*0.05f;
				}
			}
			float absorbedDamage = damage*percentageAbsorption;
			float alteredDamage = damage - absorbedDamage;
			//
			if ((this.hasEffect(MobEffects.DAMAGE_RESISTANCE) || !useVanilla) && this.getAttributeValue(CAttributes.PHYSICAL_RESISTANCE) > 0 && source != DamageSource.OUT_OF_WORLD) {
				int i;
				if (useVanilla)
					i = (this.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
				else
					i = Mth.ceil(this.getAttributeValue(CAttributes.PHYSICAL_RESISTANCE)) + (Combat.RPG_CONFIG.enableLevelingSystem ? -10 : 0);
				int j = 25 - i;
				float f = alteredDamage * (float)j;
				float f1 = alteredDamage;
				alteredDamage = Math.max(f / 25.0F, 0.0F);
				float f2 = f1 - alteredDamage;
				if (f2 > 0.0F && f2 < 3.4028235E37F && this.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
					if ((Object)this instanceof ServerPlayer) {
						((ServerPlayer)(Object)this).awardStat(Stats.DAMAGE_RESISTED, Math.round(f2 * 10.0F));
					} else if (source.getEntity() instanceof ServerPlayer) {
						((ServerPlayer)source.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(f2 * 10.0F));
					}
				}
			}

			if (alteredDamage <= 0.0F) {
				return 0.0F;
			} else {
				int k = EnchantmentHelper.getDamageProtection(this.getArmorSlots(), source);
				if (k > 0) {
					alteredDamage = CombatRules.getDamageAfterMagicAbsorb(alteredDamage, (float)k);
				}
				//Not Vanilla
				for (EquipmentSlot type : EquipmentSlot.values()) {
					if (type.getType() == EquipmentSlot.Type.ARMOR && CEnchantmentHelper.getAbsorptionModifier(this.getItemBySlot(type)) > 0)
						this.getItemBySlot(type).hurtAndBreak((int)Math.ceil((CEnchantmentHelper.getAbsorptionModifier(this.getItemBySlot(type))*0.05f)*damage), (LivingEntity)(Object)this, (e) -> {
							e.broadcastBreakEvent(type);
						});
				}
				//
				return alteredDamage;
			}
		}
	}

	@Inject(method = "createLivingAttributes",at = @At("RETURN"))
	private static void createLivingAttributes_inject(CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
		ci.getReturnValue().add(CAttributes.PHYSICAL_RESISTANCE);
	}

	/**
	 * Used to make the magma walker enchantment work
	 */
	@Inject(method = "onChangedBlock", at = @At("HEAD"))
	public void onChangedBlock_inject(BlockPos pPos, CallbackInfo ci) {
		int i = EnchantmentHelper.getEnchantmentLevel(CEnchantments.MAGMA_WALKER, (LivingEntity)(Object)this);
		if (i > 0) {
			MagmaWalkerEnchantment.onEntityMoved((LivingEntity)(Object)this, this.level, pPos, i);
		}
	}

	private float storedDamage = 0.0f;
	/**
	 * Stores the amount of damage the player takes into a variable since @ModifyVariable doesn't use locals
	 */
	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;isProjectile()Z", shift = Shift.BY, by = -4), locals = LocalCapture.CAPTURE_FAILHARD)
	public void hurt_inject1(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean flag, float f1) {
		storedDamage = f1;
	}

	/**
	 * Awards the damage blocked by weapon stat whenever a sword is used to block damage
	 */
	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/damagesource/DamageSource;FFZ)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	public void hurt_inject2(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean flag, float f1, boolean flag1, Entity entity1, boolean flag2) {
		if (amount > 0.0F && amount < 3.4028235E37F && ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemBySlot(EquipmentSlot.MAINHAND))) {
			((ServerPlayer)(Object)this).awardStat(CStats.DAMAGE_BLOCKED_BY_WEAPON, Math.round(amount * 10.0F));
		}
	}

	/**
	 * If we sword block, divide the damage we stored recently by 2
	 * @param amount
	 * @return
	 */
	@ModifyVariable(method = "hurt", /*name = "amount"*/ at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;isProjectile()Z", shift = Shift.BY, by = -3), ordinal = 0, print = false)
	public float hurt_modifyVariable(float amount) {
		if (ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemBySlot(EquipmentSlot.MAINHAND))) {
			return storedDamage/2;
		} else {
			return amount;
		}
	}

	/**
	 * Either the player
	 */
	@Redirect(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;blockUsingShield(Lnet/minecraft/world/entity/LivingEntity;)V"))
	public void hurt_redirect(LivingEntity us, LivingEntity entity) {
		if (ItemFilters.BLOCKABLE_WEAPONS.test(this.getItemBySlot(EquipmentSlot.MAINHAND)) && !(this.getItemBySlot(EquipmentSlot.MAINHAND).canPerformAction(ToolActions.SHIELD_BLOCK) || this.getItemBySlot(EquipmentSlot.OFFHAND).canPerformAction(ToolActions.SHIELD_BLOCK))) {
			this.blockUsingSword(entity);
		} else {
			this.blockUsingShield(entity);
		}
	}

	protected void blockUsingSword(LivingEntity entityIn) {
		this.knockback(0.75F, entityIn.getX() - this.getX(), entityIn.getZ() - this.getZ());
	}
	
	
	/**
	 * This allows us to use the Jump Strength attribute rather than the mob effect to reduce the fall damage of the player
	 * @param pDistance
	 * @param pDamageMultiplier
	 * @param cir
	 */
	@Inject(method = "calculateFallDamage", at = @At(value = "HEAD"), cancellable = true)
	public void calculateFallDamage_inject(float pDistance, float pDamageMultiplier, CallbackInfoReturnable<Integer> cir) {
		boolean useVanilla = this.getAttribute(CAttributes.JUMP_STRENGTH) == null;
		if (!useVanilla) {
			float f = (((Double)this.getAttributeValue(CAttributes.JUMP_STRENGTH)).floatValue() - 0.2f) * 10f;
			cir.setReturnValue(Mth.ceil((pDistance - 3.0F - f) * pDamageMultiplier));
		}
	}
}
