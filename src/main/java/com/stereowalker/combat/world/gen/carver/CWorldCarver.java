package com.stereowalker.combat.world.gen.carver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CWorldCarver {
	public static final List<WorldCarver<?>> WORLD_CARVERS = new ArrayList<WorldCarver<?>>();
	public static final WorldCarver<ProbabilityConfig> ACROTLEST_CAVE = register("acrotlest_cave", new AcrotlestCaveWorldCarver(ProbabilityConfig.CODEC));
	public static final WorldCarver<ProbabilityConfig> ACROTLEST_CANYON = register("acrotlest_canyon", new AcrotlestCanyonWorldCarver(ProbabilityConfig.CODEC));
	
	public static <T extends ICarverConfig, F extends WorldCarver<T>>F register(String name, F worldCarver){
		worldCarver.setRegistryName(Combat.getInstance().location(name));
		WORLD_CARVERS.add(worldCarver);
		return worldCarver;
	}
	
	public static void registerAll(IForgeRegistry<WorldCarver<?>> registry) {
		for(WorldCarver<?> feature: WORLD_CARVERS) {
			registry.register(feature);
			Combat.debug("World Carver: \""+feature.getRegistryName().toString()+"\" registered");
		}
		
		for(Pair<String,ConfiguredCarver<?>> configuredCarver: CConfiguredCarvers.CONFIGURED_CARVERS) {
			WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, Combat.getInstance().locationString(configuredCarver.getKey()), configuredCarver.getValue());
		}
		Combat.debug("All World Carvers Registered");
	}
}
