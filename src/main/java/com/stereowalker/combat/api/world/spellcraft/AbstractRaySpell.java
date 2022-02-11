package com.stereowalker.combat.api.world.spellcraft;

import com.stereowalker.combat.world.entity.magic.Ray;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractRaySpell extends TargetedSpell implements IExtensionSpell {

	protected AbstractRaySpell(SpellCategory category, Rank tier, float cost, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.RAY, cost, 0, isBeneficialSpell, castTime);
	}
	
	public abstract ParticleOptions rayParticles();
	public abstract SoundEvent rayAmbientSound();
	public abstract ResourceLocation rayTexture();

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		Ray spellEntity = new Ray(caster, caster.level, caster.getX(), caster.getY(), caster.getZ());
		spellEntity.setSpell(new SpellInstance(this, strength, location, hand, caster.getUUID()));
		return caster.level.addFreshEntity(spellEntity);
	}
	
	@Override
	public boolean isHeld() {
		return true;
	}
}
