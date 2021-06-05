package com.stereowalker.combat.api.spell;

import com.stereowalker.combat.entity.magic.RayEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractRaySpell extends TargetedSpell implements IExtensionSpell {

	protected AbstractRaySpell(SpellCategory category, Rank tier, float cost, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.RAY, cost, 0, isBeneficialSpell, castTime);
	}
	
	public abstract IParticleData rayParticles();
	public abstract SoundEvent rayAmbientSound();
	public abstract ResourceLocation rayTexture();

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		RayEntity spellEntity = new RayEntity(caster, caster.world, caster.getPosition().getX(), caster.getPosition().getY(), caster.getPosition().getZ());
		spellEntity.setSpell(new SpellInstance(this, strength, location, hand, caster.getUniqueID()));
		return caster.world.addEntity(spellEntity);
	}
	
	@Override
	public boolean isHeld() {
		return true;
	}
}
