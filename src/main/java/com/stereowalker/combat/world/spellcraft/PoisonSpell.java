package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.AbstractProjectileSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PoisonSpell extends AbstractProjectileSpell {

	protected PoisonSpell(SpellCategory category, Rank tier, float cost, int castTime) {
		super(category, tier, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		if (target instanceof LivingEntity) {
			if(!target.level.isClientSide && target instanceof LivingEntity) {
				MobEffectInstance potioneffect = new MobEffectInstance(MobEffects.POISON, Mth.ceil(20 * 20 * strength), 1);
				((LivingEntity)target).addEffect(potioneffect);
			}
			return true;
		}
		return false;
	}

	@Override
	public ParticleOptions trailParticles() {
		return ParticleTypes.HAPPY_VILLAGER;
	}

	@Override
	public ResourceLocation projectileTexture() {
		return Combat.getInstance().location("textures/entity/projectiles/spells/default_projectile.png");
	}

	@Override
	public SoundEvent projectileHitSound() {
		return SoundEvents.GRASS_BREAK;
	}
}