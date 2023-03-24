package com.stereowalker.combat.compat;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SurviveCompat {
	
	public static void giveColdResistance(LivingEntity living, int ticks, int amplifier) {
//		living.addEffect(new MobEffectInstance(com.stereowalker.old.survive.potion.SEffects.COLD_RESISTANCE, ticks, amplifier));
	}
	
	public static boolean isThirstEnabled() {
		return /* com.stereowalker.old.survive.config.Config.enable_thirst */false;
	}
	
	public static boolean isStaminaEnabled() {
		return /* com.stereowalker.old.survive.config.Config.enable_stamina */false;
	}
}
