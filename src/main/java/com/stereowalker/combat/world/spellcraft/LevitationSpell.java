package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LevitationSpell extends Spell {


	protected LevitationSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		caster.push(0, 1.5D * strength, 0);
		return true;
	}
	
	@Override
	public boolean isClientSpell() {
		return true;
	}

}
