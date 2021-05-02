package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractSurroundSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WitchesGardenSpell extends AbstractSurroundSpell {

	protected WitchesGardenSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, effectInterval, isBeneficialSpell, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			if (!caster.world.isRemote) {
				((LivingEntity)target).addPotionEffect(new EffectInstance(Effects.POISON, MathHelper.ceil(40 * strength), 1));
			}
			return true;
		}
		return false;
	}

}
