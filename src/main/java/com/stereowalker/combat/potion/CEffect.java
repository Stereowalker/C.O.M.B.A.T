package com.stereowalker.combat.potion;

import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class CEffect extends Effect{

	public CEffect(EffectType effectType, int liquidColorIn) {
		super(effectType, liquidColorIn);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		if (this == CEffects.MANA_REGENERATION) {
			if (CombatEntityStats.getMana(entityLivingBaseIn) < entityLivingBaseIn.getAttributeValue(CAttributes.MAX_MANA)) {
				CombatEntityStats.addMana(entityLivingBaseIn, 1);
			}
		}
	}


	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		if (this == CEffects.MANA_REGENERATION) {
			int k = 50 >> amplifier;
			if (k > 0) {
				return duration % k == 0;
			} else {
				return true;
			}
		}
		else return super.isReady(duration, amplifier);
	}
}
