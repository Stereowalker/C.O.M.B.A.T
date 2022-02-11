package com.stereowalker.combat.core.particles;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.IForgeRegistry;

public class CParticleTypes {
	public static final List<ParticleType<?>> PARTICLES = new ArrayList<ParticleType<?>>();
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
		particle.setRegistryName(Combat.getInstance().location(key));
		PARTICLES.add(particle);
		return particle;
		//		return (SimpleParticleType)Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, );
	}

	//	private static <T extends IParticleData> ParticleType<T> register(String key, IParticleData.IDeserializer<T> deserializer) {
	//		return Registry.register(Registry.PARTICLE_TYPE, key, new ParticleType<>(false, deserializer));
	//	}

	public static void registerAll(IForgeRegistry<ParticleType<?>> registry) {
		for(ParticleType<?> particle: PARTICLES) {
			registry.register(particle);
			Combat.debug("Particle: \""+particle.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Particles Registered");
	}

}
