package com.stereowalker.combat.compat.origins;

import com.stereowalker.combat.Combat;

//import io.github.apace100.apoli.power.Power;
//import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.PowerTypeRegistry;
import net.minecraft.world.entity.LivingEntity;

public class OriginsCompat {
	
	public static boolean hasNoManaAbsorbers(LivingEntity entity) {
		return PowerTypeRegistry.get(Combat.getInstance().location("no_mana_absorbers")).isActive(entity);
//		return new PowerTypeReference<Power>(Combat.getInstance().location("no_mana_absorbers")).isActive(entity);
	}
	
	public static boolean hasAlteredRegeneration(LivingEntity entity) {
		return PowerTypeRegistry.get(Combat.getInstance().location("altered_regeneration")).isActive(entity);
	}
	
	public static boolean hasMagicStomach(LivingEntity entity) {
		return PowerTypeRegistry.get(Combat.getInstance().location("magic_stomach")).isActive(entity);
	}
}
