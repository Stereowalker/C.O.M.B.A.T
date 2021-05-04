package com.stereowalker.combat.compat.origins;

import com.stereowalker.combat.Combat;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerTypeReference;
import net.minecraft.entity.LivingEntity;

public class OriginsCompat {
	
	public static boolean hasNoManaAbsorbers(LivingEntity entity) {
		return new PowerTypeReference<Power>(Combat.getInstance().location("no_mana_absorbers")).isActive(entity);
	}
	
	public static boolean hasAlteredRegeneration(LivingEntity entity) {
		return new PowerTypeReference<Power>(Combat.getInstance().location("altered_regeneration")).isActive(entity);
	}
	
	public static boolean hasMagicStomach(LivingEntity entity) {
		return new PowerTypeReference<Power>(Combat.getInstance().location("magic_stomach")).isActive(entity);
	}
}
