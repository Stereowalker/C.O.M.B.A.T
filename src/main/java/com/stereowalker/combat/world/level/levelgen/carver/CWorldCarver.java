package com.stereowalker.combat.world.level.levelgen.carver;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CCarvers;

import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CWorldCarver {
	public static final List<WorldCarver<?>> WORLD_CARVERS = new ArrayList<WorldCarver<?>>();
	public static final WorldCarver<CaveCarverConfiguration> ACROTLEST_CAVE = register("acrotlest_cave", new AcrotlestCaveWorldCarver(CaveCarverConfiguration.CODEC));
	public static final WorldCarver<CanyonCarverConfiguration> ACROTLEST_CANYON = register("acrotlest_canyon", new AcrotlestCanyonWorldCarver(CanyonCarverConfiguration.CODEC));
	
	public static <T extends CarverConfiguration, F extends WorldCarver<T>>F register(String name, F worldCarver){
		worldCarver.setRegistryName(Combat.getInstance().location(name));
		WORLD_CARVERS.add(worldCarver);
		return worldCarver;
	}
	
	public static void registerAll(IForgeRegistry<WorldCarver<?>> registry) {
		for(WorldCarver<?> feature: WORLD_CARVERS) {
			registry.register(feature);
			Combat.debug("World Carver: \""+feature.getRegistryName().toString()+"\" registered");
		}
		
		new CCarvers();
		Combat.debug("All World Carvers Registered");
	}
}
