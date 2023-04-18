package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class ExplosionSpell extends Spell {

	protected ExplosionSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (location != null) {
			if(!caster.level.isClientSide) {
				caster.level.explode((Entity)null, location.x, location.y, location.z, Mth.ceil(strength), false, ExplosionInteraction.TNT);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return Component.translatable("No location was selected");
	}
}
