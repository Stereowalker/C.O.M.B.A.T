package com.stereowalker.combat.util;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class CSoundEvents {
	public static final List<SoundEvent> SOUNDEVENTS = new ArrayList<SoundEvent>();
	
	public static final SoundEvent GLOCK_17_RELOAD = register("glock_17_reload");
	public static final SoundEvent GLOCK_17_FIRE = register("glock_17_fire");
	public static final SoundEvent ENTITY_BULLET_HIT = register("entity_bullet_hit");
	public static final SoundEvent BERETTA_92_RELOAD = register("beretta_92_reload");
	public static final SoundEvent BERETTA_92_FIRE = register("beretta_92_fire");
	
	public static SoundEvent register(String name) {
		SoundEvent soundEvent = new SoundEvent(Combat.getInstance().location(name));
		soundEvent.setRegistryName(Combat.getInstance().location(name));
		CSoundEvents.SOUNDEVENTS.add(soundEvent);
		return soundEvent;
	}
	
	public static void registerAll(IForgeRegistry<SoundEvent> registry) {
		for(SoundEvent soundEvent : SOUNDEVENTS) {
			registry.register(soundEvent);
			Combat.debug("SoundEvent: \""+soundEvent.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All SoundEvents Registered");
	}
}
