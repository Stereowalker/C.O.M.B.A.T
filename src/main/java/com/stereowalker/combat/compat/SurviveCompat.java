package com.stereowalker.combat.compat;

import com.stereowalker.survive.potion.SEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

public class SurviveCompat {
	
	public static void giveColdResistance(LivingEntity living, int ticks, int amplifier) {
		living.addPotionEffect(new EffectInstance(SEffects.COLD_RESISTANCE, ticks, amplifier));
	}
	
	public static boolean isThirstEnabled() {
		return com.stereowalker.survive.config.Config.enable_thirst;
	}
	
	public static boolean isStaminaEnabled() {
		return com.stereowalker.survive.config.Config.enable_stamina;
	}
}
