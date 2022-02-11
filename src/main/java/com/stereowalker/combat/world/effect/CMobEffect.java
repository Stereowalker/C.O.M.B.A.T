package com.stereowalker.combat.world.effect;

import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CMobEffect extends MobEffect {

	public CMobEffect(MobEffectCategory effectType, int liquidColorIn) {
		super(effectType, liquidColorIn);
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
		if (this == CMobEffects.MANA_REGENERATION) {
			if (CombatEntityStats.getMana(entityLivingBaseIn) < entityLivingBaseIn.getAttributeValue(CAttributes.MAX_MANA)) {
				CombatEntityStats.addMana(entityLivingBaseIn, 1);
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		if (this == CMobEffects.MANA_REGENERATION) {
			int k = 50 >> amplifier;
			if (k > 0) {
				return duration % k == 0;
			} else {
				return true;
			}
		}
		else return super.isDurationEffectTick(duration, amplifier);
	}
}
