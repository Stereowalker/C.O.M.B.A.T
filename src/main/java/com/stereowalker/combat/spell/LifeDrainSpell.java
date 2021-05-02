package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractRaySpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;

public class LifeDrainSpell extends AbstractRaySpell {

	protected LifeDrainSpell(SpellCategory category, Rank tier, float cost, int castTime) {
		super(category, tier, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)target;
			caster.setHealth((float) (caster.getHealth()+0.5F * strength));
			if(living.getHealth() >= 1.0F) {
				living.setHealth((float) (living.getHealth()-0.5F * strength));
				return true;
			}
			else return living.attackEntityFrom(CDamageSource.causeLifeDrainDamage(caster), 0.5F);
		}
		return false;
	}

	@Override
	public IParticleData rayParticles() {
		return null;
	}

	@Override
	public SoundEvent rayAmbientSound() {
		return null;
	}

	@Override
	public ResourceLocation rayTexture() {
		return new ResourceLocation("textures/entity/guardian_beam.png");
	}

}
