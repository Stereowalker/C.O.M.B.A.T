package com.stereowalker.combat.world.level.levelgen.carver;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CCarvers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public abstract class CWorldCarver {
	public static final Map<ResourceLocation,WorldCarver<?>> WORLD_CARVERS = new HashMap<ResourceLocation,WorldCarver<?>>();
	public static final WorldCarver<CaveCarverConfiguration> ACROTLEST_CAVE = register("acrotlest_cave", new AcrotlestCaveWorldCarver(CaveCarverConfiguration.CODEC));
	public static final WorldCarver<CanyonCarverConfiguration> ACROTLEST_CANYON = register("acrotlest_canyon", new AcrotlestCanyonWorldCarver(CanyonCarverConfiguration.CODEC));
	
	public static <T extends CarverConfiguration, F extends WorldCarver<T>>F register(String name, F worldCarver){
		WORLD_CARVERS.put(Combat.getInstance().location(name), worldCarver);
		return worldCarver;
	}
	
	public static void registerAll(RegisterHelper<WorldCarver<?>> registry) {
		for(Entry<ResourceLocation, WorldCarver<?>> feature: WORLD_CARVERS.entrySet()) {
			registry.register(feature.getKey(), feature.getValue());
			Combat.debug("World Carver: \""+feature.getKey().toString()+"\" registered");
		}
		
		new CCarvers();
		Combat.debug("All World Carvers Registered");
	}
}
