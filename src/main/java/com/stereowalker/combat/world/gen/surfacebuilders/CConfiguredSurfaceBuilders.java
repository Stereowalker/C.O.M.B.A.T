package com.stereowalker.combat.world.gen.surfacebuilders;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CConfiguredSurfaceBuilders {
	public static final List<Pair<String,ConfiguredSurfaceBuilder<?>>> CONFIGURED_SURFACE_BUILDERS = new ArrayList<Pair<String,ConfiguredSurfaceBuilder<?>>>();
	
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> PURIFIED_GRASS = register("purified_grass", SurfaceBuilder.DEFAULT.func_242929_a(CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG));

	public static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder){
		CONFIGURED_SURFACE_BUILDERS.add(Pair.of(name,configuredSurfaceBuilder));
		return configuredSurfaceBuilder;
	}
}
