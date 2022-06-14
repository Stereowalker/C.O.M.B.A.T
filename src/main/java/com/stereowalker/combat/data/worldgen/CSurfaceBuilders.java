//package com.stereowalker.combat.data.worldgen;
//TODO: Look at SurfaceRuleData
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang3.tuple.Pair;
//
//import com.stereowalker.combat.world.level.levelgen.surfacebuilders.CSurfaceBuilder;
//
//import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
//import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
//import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
//import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
//
//public class CSurfaceBuilders {
//	public static final List<Pair<String,ConfiguredSurfaceBuilder<?>>> CONFIGURED_SURFACE_BUILDERS = new ArrayList<Pair<String,ConfiguredSurfaceBuilder<?>>>();
//	
//	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> PURIFIED_GRASS = register("purified_grass", SurfaceBuilder.DEFAULT.configured(CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG));
//
//	public static <SC extends SurfaceBuilderConfiguration> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder){
//		CONFIGURED_SURFACE_BUILDERS.add(Pair.of(name,configuredSurfaceBuilder));
//		return configuredSurfaceBuilder;
//	}
//}
