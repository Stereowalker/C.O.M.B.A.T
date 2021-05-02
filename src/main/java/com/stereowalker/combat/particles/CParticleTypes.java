package com.stereowalker.combat.particles;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.IForgeRegistry;

public class CParticleTypes {
	public static final List<ParticleType<?>> PARTICLES = new ArrayList<ParticleType<?>>();
	public static final BasicParticleType ACROTLEST_PORTAL = register("acrotlest_portal", false);
	public static final BasicParticleType PYRANITE_FLAME = register("pyranite_flame", false);
	public static final BasicParticleType DRIPPING_OIL = register("dripping_oil", false);
	public static final BasicParticleType FALLING_OIL = register("falling_oil", false);
	public static final BasicParticleType DRIPPING_BIABLE = register("dripping_biable", false);
	public static final BasicParticleType FALLING_BIABLE = register("falling_biable", false);
	public static final BasicParticleType OIL_SPLASH = register("oil_splash", false);
	public static final BasicParticleType BIABLE_SPLASH = register("biable_splash", false);

	private static BasicParticleType register(String key, boolean alwaysShow) {
		BasicParticleType particle = new BasicParticleType(alwaysShow);
		particle.setRegistryName(Combat.getInstance().location(key));
		PARTICLES.add(particle);
		return particle;
		//		return (BasicParticleType)Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, );
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
