package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractSurroundSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class LightningFieldSpell extends AbstractSurroundSpell {

	protected LightningFieldSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, effectInterval, isBeneficialSpell, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			if (!caster.world.isRemote) {
				SpellUtil.lightningAttack((LivingEntity)target, caster, (float) (2.0F * strength), true, 5, 5);
			}
			return true;
		}
		return false;
	}

}
