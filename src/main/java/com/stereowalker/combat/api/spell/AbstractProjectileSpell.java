package com.stereowalker.combat.api.spell;

import com.stereowalker.combat.entity.projectile.SpellEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractProjectileSpell extends TargetedSpell implements IExtensionSpell {
	
	protected AbstractProjectileSpell(SpellCategory category, Rank tier, float cost, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.PROJECTILE, cost, 0, isBeneficialSpell, castTime);
	}
	
	public abstract IParticleData trailParticles();
	public abstract ResourceLocation projectileTexture();
	public abstract SoundEvent projectileHitSound();
	
	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		SpellEntity spellEntity = new SpellEntity(caster.world, caster);
		spellEntity.setSpell(new SpellInstance(this, strength, location, hand, caster.getUniqueID()));
		spellEntity.shoot(caster, caster.rotationPitch, caster.rotationYaw, 0.0F, 2.0F, 0);
		return caster.world.addEntity(spellEntity);
	}

}
