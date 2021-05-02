package com.stereowalker.combat.spell;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.AbstractProjectileSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.potion.CEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ParalysisSpell extends AbstractProjectileSpell {

	protected ParalysisSpell(SpellCategory category, Rank tier, float cost, int castTime) {
		super(category, tier, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			if(!target.world.isRemote) {
				EffectInstance potioneffect = new EffectInstance(CEffects.PARALYSIS, MathHelper.ceil(20 * 20 * strength), 2);
				((LivingEntity)target).addPotionEffect(potioneffect);
			}
			return true;
		}
		return false;
	}

	@Override
	public IParticleData trailParticles() {
		return ParticleTypes.HAPPY_VILLAGER;
	}

	@Override
	public ResourceLocation projectileTexture() {
		return Combat.getInstance().location("textures/entity/projectiles/spells/default_projectile.png");
	}

	@Override
	public SoundEvent projectileHitSound() {
		return SoundEvents.BLOCK_GRASS_BREAK;
	}
}