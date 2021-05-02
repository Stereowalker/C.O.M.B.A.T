package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class StormSpell extends Spell {

	public StormSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (caster.world.isRaining()) {
			if(caster.world instanceof ServerWorld) {
				((ServerWorld)caster.world).setWeather(0, (int) (3000 * strength), true, true);
			}
			return true;
		}
		return false;
	}

	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		return new TranslationTextComponent("This spell only works when its raining");
	}

}
