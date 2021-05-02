package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractSurroundSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class RingOfIceSpell extends AbstractSurroundSpell {

	protected RingOfIceSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, int castTime) {
		super(category, tier, cost, cooldown, effectInterval, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			if (!caster.world.isRemote) {
				SpellUtil.iceAttack((LivingEntity)target, caster, (float) (2.0F * strength));
			}
			return true;
		}
		return false;
	}

}
