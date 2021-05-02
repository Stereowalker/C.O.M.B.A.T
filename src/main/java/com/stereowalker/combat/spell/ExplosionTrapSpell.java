package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractTrapSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion.Mode;

public class ExplosionTrapSpell extends AbstractTrapSpell {

	protected ExplosionTrapSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (!caster.world.isRemote) {
			caster.world.createExplosion(caster, location.x, location.y, location.z, MathHelper.ceil(strength), false, Mode.BREAK);
		}
		return true;
	}

}
