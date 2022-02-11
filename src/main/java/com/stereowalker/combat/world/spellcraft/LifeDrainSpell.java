package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractRaySpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.damagesource.CDamageSource;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LifeDrainSpell extends AbstractRaySpell {

	protected LifeDrainSpell(SpellCategory category, Rank tier, float cost, int castTime) {
		super(category, tier, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		if (target instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)target;
			caster.setHealth((float) (caster.getHealth()+0.5F * strength));
			if(living.getHealth() >= 1.0F) {
				living.setHealth((float) (living.getHealth()-0.5F * strength));
				return true;
			}
			else return living.hurt(CDamageSource.causeLifeDrainDamage(caster), 0.5F);
		}
		return false;
	}

	@Override
	public ParticleOptions rayParticles() {
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
