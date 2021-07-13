package com.stereowalker.combat.util;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.projectile.AbstractMagicProjectileEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class CDamageSource extends DamageSource {
	private boolean iceDamage;

	public CDamageSource(String damageTypeIn) {
		super(damageTypeIn);
	}

	public static final DamageSource FROSTBITE = (new DamageSource("frostbite")).setDamageBypassesArmor().setDifficultyScaled();
	public static final DamageSource STAKED = (new DamageSource("staked")).setDamageBypassesArmor().setDifficultyScaled();
	public static final DamageSource SUNBURNED = (new DamageSource("sunburned")).setDamageBypassesArmor().setDifficultyScaled();
	public static final DamageSource BLOOD_MAGIC = (new DamageSource("blood_magic")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
	public static final DamageSource MANA_DRAIN = (new DamageSource("mana_drain")).setDamageBypassesArmor().setDamageIsAbsolute();

	public static DamageSource causeLightningDamage(LivingEntity mob) {
		return new EntityDamageSource("lightning", mob).setMagicDamage();
	}

	public static DamageSource causeFireDamage(LivingEntity mob) {
		return new EntityDamageSource("fire", mob).setFireDamage().setMagicDamage();
	}

	public static DamageSource causeIceDamage(LivingEntity mob) {
		return new CEntityDamageSource("ice", mob).setIceDamage().setMagicDamage();
	}

	public static DamageSource causeWaterDamage(LivingEntity mob) {
		return new EntityDamageSource("water", mob).setMagicDamage();
	}

	public static DamageSource causeEarthDamage(LivingEntity mob) {
		return new EntityDamageSource("earth", mob).setMagicDamage();
	}

	public static DamageSource causeBlackHoleDamage(LivingEntity mob) {
		return new EntityDamageSource("blackHole", mob).setMagicDamage();
	}

	public static DamageSource causeAirDamage(LivingEntity mob) {
		return new EntityDamageSource("air", mob).setMagicDamage();
	}

	public static DamageSource causeLifeDrainDamage(LivingEntity mob) {
		return new EntityDamageSource("lifeDrain", mob).setMagicDamage();
	}

	public static DamageSource causeSpearDamage(Entity source, @Nullable Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("spear", source, indirectEntityIn)).setProjectile();
	}

	/**
	 * returns EntityDamageSourceIndirect of an arrow
	 */
	public static DamageSource causeMagicProjectileDamage(AbstractMagicProjectileEntity arrow, @Nullable Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("magic_projectile", arrow, indirectEntityIn)).setProjectile();
	}

	/**
	 * Returns the EntityDamageSource of the Thorns enchantment
	 */
	public static DamageSource causeBurningThornsDamage(Entity source) {
		return (new EntityDamageSource("burningThorns", source)).setIsThornsDamage().setMagicDamage().setFireDamage();
	}

	/**
	 * Define the damage type as fire based.
	 */
	public CDamageSource setIceDamage() {
		this.iceDamage = true;
		return this;
	}

	/**
	 * Returns true if the damage is fire based.
	 */
	public boolean isIceDamage() {
		return this.iceDamage;
	}
}
