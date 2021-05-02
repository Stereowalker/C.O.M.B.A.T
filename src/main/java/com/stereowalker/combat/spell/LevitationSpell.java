package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class LevitationSpell extends Spell {


	protected LevitationSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		caster.addVelocity(0, 1.5D * strength, 0);
		caster.fallDistance = (float) (-10.0F * strength * 2);
		return true;
	}
	
	@Override
	public boolean isClientSpell() {
		return true;
	}

}
