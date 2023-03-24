package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractTrapSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

public class ExplosionTrapSpell extends AbstractTrapSpell {

	protected ExplosionTrapSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		if (!caster.level.isClientSide) {
			caster.level.explode(caster, location.x, location.y, location.z, Mth.ceil(strength), false, Explosion.BlockInteraction.BREAK);
		}
		return true;
	}

}
