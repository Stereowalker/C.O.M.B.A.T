package com.stereowalker.combat.world.level.biome;

import com.stereowalker.combat.Combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class CBiomes {
	public static final ResourceKey<Biome> DEAD_FOREST = makeKey("dead_forest");
	public static final ResourceKey<Biome> DEAD_PLAINS = makeKey("dead_plains");
	public static final ResourceKey<Biome> MAGIC_FOREST = makeKey("magic_forest");
	public static final ResourceKey<Biome> MAGIC_PLAINS = makeKey("magic_plains");
	
	public static final ResourceKey<Biome> ACROTLEST_FOREST = makeKey("acrotlest_forest");
	public static final ResourceKey<Biome> ACROTLEST_MOUNTAINS = makeKey("acrotlest_mountains");
	public static final ResourceKey<Biome> ACROTLEST_RIVER = makeKey("acrotlest_river");
	public static final ResourceKey<Biome> HISOV_SANDS = makeKey("hisov_sands");

	private static ResourceKey<Biome> makeKey(String name) {
		return ResourceKey.create(Registries.BIOME, Combat.getInstance().location(name));
	}
}
