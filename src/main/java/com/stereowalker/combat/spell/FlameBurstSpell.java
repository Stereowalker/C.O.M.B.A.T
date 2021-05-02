package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractTrapSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class FlameBurstSpell extends AbstractTrapSpell {

	protected FlameBurstSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		boolean flag = target instanceof LivingEntity;
		if (!caster.world.isRemote && flag) {
			SpellUtil.fireAttack((LivingEntity)target, caster, (float) (20.0F*strength));
		}
		return flag;
	}

}
