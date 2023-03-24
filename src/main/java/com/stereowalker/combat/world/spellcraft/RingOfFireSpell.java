package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractSurroundSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RingOfFireSpell extends AbstractSurroundSpell {

	protected RingOfFireSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, effectInterval, isBeneficialSpell, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		if (target instanceof LivingEntity) {
			if (!caster.level.isClientSide) {
				SpellUtil.fireAttack((LivingEntity)target, caster, (float) (2.0F * strength));
			}
			return true;
		}
		return false;
	}

}
