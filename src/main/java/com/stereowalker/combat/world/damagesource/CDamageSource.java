package com.stereowalker.combat.world.damagesource;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.entity.projectile.AbstractMagicProjectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CDamageSource extends DamageSource {
	private boolean iceDamage;

	public CDamageSource(String damageTypeIn) {
		super(damageTypeIn);
	}

	public static final DamageSource FROSTBITE = (new DamageSource("frostbite")).bypassArmor().setScalesWithDifficulty();
	public static final DamageSource STAKED = (new DamageSource("staked")).bypassArmor().setScalesWithDifficulty();
	public static final DamageSource SUNBURNED = (new DamageSource("sunburned")).bypassArmor().setScalesWithDifficulty();
	public static final DamageSource BLOOD_MAGIC = (new DamageSource("blood_magic")).bypassArmor().bypassMagic().setMagic();
	public static final DamageSource MANA_DRAIN = (new DamageSource("mana_drain")).bypassArmor().bypassMagic();

	public static DamageSource causeLightningDamage(LivingEntity mob) {
		return new EntityDamageSource("lightning", mob).setMagic();
	}

	public static DamageSource causeFireDamage(LivingEntity mob) {
		return new EntityDamageSource("fire", mob).setIsFire().setMagic();
	}

	public static DamageSource causeIceDamage(LivingEntity mob) {
		return new CEntityDamageSource("ice", mob).setIceDamage().setMagic();
	}

	public static DamageSource causeWaterDamage(LivingEntity mob) {
		return new EntityDamageSource("water", mob).setMagic();
	}

	public static DamageSource causeEarthDamage(LivingEntity mob) {
		return new EntityDamageSource("earth", mob).setMagic();
	}

	public static DamageSource causeBlackHoleDamage(LivingEntity mob) {
		return new EntityDamageSource("blackHole", mob).setMagic();
	}

	public static DamageSource causeAirDamage(LivingEntity mob) {
		return new EntityDamageSource("air", mob).setMagic();
	}

	public static DamageSource causeLifeDrainDamage(LivingEntity mob) {
		return new EntityDamageSource("lifeDrain", mob).setMagic();
	}

	public static DamageSource causeSpearDamage(Entity source, @Nullable Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("spear", source, indirectEntityIn)).setProjectile();
	}

	/**
	 * returns EntityDamageSourceIndirect of an arrow
	 */
	public static DamageSource causeMagicProjectileDamage(AbstractMagicProjectile arrow, @Nullable Entity indirectEntityIn) {
		return (new IndirectEntityDamageSource("magic_projectile", arrow, indirectEntityIn)).setProjectile();
	}

	/**
	 * Returns the EntityDamageSource of the Thorns enchantment
	 */
	public static DamageSource causeBurningThornsDamage(Entity source) {
		return (new EntityDamageSource("burningThorns", source)).setThorns().setMagic().setIsFire();
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
