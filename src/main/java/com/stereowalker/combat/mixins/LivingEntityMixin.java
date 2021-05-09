package com.stereowalker.combat.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.ai.CAttributes;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
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
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

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

	/**
	 * Reduces damage, depending on potions
	 */
	@Overwrite
	protected float applyPotionDamageCalculations(DamageSource source, float damage) {
		boolean useVanilla = false;
		System.out.println("BOO");
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
}
