package com.stereowalker.combat.core.particles;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CParticleTypes {
	public static final Map<ResourceLocation,ParticleType<?>> PARTICLES = new HashMap<ResourceLocation,ParticleType<?>>();
	public static final SimpleParticleType ACROTLEST_PORTAL = register("acrotlest_portal", false);
	public static final SimpleParticleType PYRANITE_FLAME = register("pyranite_flame", false);
	public static final SimpleParticleType DRIPPING_OIL = register("dripping_oil", false);
	public static final SimpleParticleType FALLING_OIL = register("falling_oil", false);
	public static final SimpleParticleType DRIPPING_BIABLE = register("dripping_biable", false);
	public static final SimpleParticleType FALLING_BIABLE = register("falling_biable", false);
	public static final SimpleParticleType OIL_SPLASH = register("oil_splash", false);
	public static final SimpleParticleType BIABLE_SPLASH = register("biable_splash", false);

	private static SimpleParticleType register(String key, boolean alwaysShow) {
		SimpleParticleType particle = new SimpleParticleType(alwaysShow);
		PARTICLES.put(Combat.getInstance().location(key), particle);
		return particle;
		//		return (SimpleParticleType)Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, );
	}

	//	private static <T extends IParticleData> ParticleType<T> register(String key, IParticleData.IDeserializer<T> deserializer) {
	//		return Registry.register(Registry.PARTICLE_TYPE, key, new ParticleType<>(false, deserializer));
	//	}

	public static void registerAll(RegisterHelper<ParticleType<?>> registry) {
		for(Entry<ResourceLocation, ParticleType<?>> particle: PARTICLES.entrySet()) {
			registry.register(particle.getKey(), particle.getValue());
			Combat.debug("Particle: \""+particle.getKey().toString()+"\" registered");
		}
		Combat.debug("All Particles Registered");
	}

}
