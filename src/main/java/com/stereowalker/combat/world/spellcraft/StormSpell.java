package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class StormSpell extends Spell {

	public StormSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (caster.level.isRaining()) {
			if(caster.level instanceof ServerLevel) {
				((ServerLevel)caster.level).setWeatherParameters(0, (int) (3000 * strength), true, true);
			}
			return true;
		}
		return false;
	}

	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return new TranslatableComponent("This spell only works when its raining");
	}

}
