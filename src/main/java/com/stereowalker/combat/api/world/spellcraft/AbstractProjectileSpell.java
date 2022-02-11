package com.stereowalker.combat.api.world.spellcraft;

import com.stereowalker.combat.world.entity.projectile.ProjectileSpell;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractProjectileSpell extends TargetedSpell implements IExtensionSpell {
	
	protected AbstractProjectileSpell(SpellCategory category, Rank tier, float cost, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.PROJECTILE, cost, 0, isBeneficialSpell, castTime);
	}
	
	public abstract ParticleOptions trailParticles();
	public abstract ResourceLocation projectileTexture();
	public abstract SoundEvent projectileHitSound();
	
	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		ProjectileSpell spellEntity = new ProjectileSpell(caster.level, caster);
		spellEntity.setSpell(new SpellInstance(this, strength, location, hand, caster.getUUID()));
		spellEntity.shoot(caster, caster.getXRot(), caster.getYRot(), 0.0F, 2.0F, 0);
		return caster.level.addFreshEntity(spellEntity);
	}

}
