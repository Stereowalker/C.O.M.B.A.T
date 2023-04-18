package com.stereowalker.combat.sounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CSoundEvents {
	public static final Map<ResourceLocation,SoundEvent> SOUNDEVENTS = new HashMap<ResourceLocation,SoundEvent>();
	
	public static final SoundEvent GLOCK_17_RELOAD = register("glock_17_reload");
	public static final SoundEvent GLOCK_17_FIRE = register("glock_17_fire");
	public static final SoundEvent ENTITY_BULLET_HIT = register("entity_bullet_hit");
	public static final SoundEvent BERETTA_92_RELOAD = register("beretta_92_reload");
	public static final SoundEvent BERETTA_92_FIRE = register("beretta_92_fire");
	
	public static SoundEvent register(String name) {
		SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(Combat.getInstance().location(name));
		CSoundEvents.SOUNDEVENTS.put(Combat.getInstance().location(name), soundEvent);
		return soundEvent;
	}
	
	public static void registerAll(RegisterHelper<SoundEvent> registry) {
		for(Entry<ResourceLocation, SoundEvent> soundEvent : SOUNDEVENTS.entrySet()) {
			registry.register(soundEvent.getKey(), soundEvent.getValue());
			Combat.debug("SoundEvent: \""+soundEvent.getKey().toString()+"\" registered");
		}
		Combat.debug("All SoundEvents Registered");
	}
}
