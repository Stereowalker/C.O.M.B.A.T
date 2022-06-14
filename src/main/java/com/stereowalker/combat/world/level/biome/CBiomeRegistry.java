package com.stereowalker.combat.world.level.biome;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;
//import com.stereowalker.combat.data.worldgen.CSurfaceBuilders;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class CBiomeRegistry {

	private static final Int2ObjectMap<ResourceKey<Biome>> idToKeyMap = new Int2ObjectArrayMap<>();

//	@SuppressWarnings("deprecation")
	private static Biome register(int id, ResourceKey<Biome> key, Biome biome) {
		idToKeyMap.put(id, key);
		biome.setRegistryName(key.location());
		return biome;
	}

	public static void registerAll(IForgeRegistry<Biome> registry) {
		int i = 300;
		for(Pair<ResourceKey<Biome>,Biome> biome: CBiomes.BIOMES) {
			registry.register(register(i, biome.getLeft(), biome.getRight()));
			Combat.debug("Biome: \""+biome.getLeft().getRegistryName().toString()+"\" registered");
			i++;
		}
		Combat.debug("All Biomes Registered");
		
//		Registry.register(Registry.BIOME_SOURCE, "combat:acrotlest", AcrotlestBiomeSource.CODEC);
//		
//		for(Pair<String,ConfiguredSurfaceBuilder<?>> configuredSurfaceBuilder: CSurfaceBuilders.CONFIGURED_SURFACE_BUILDERS) {
//			BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, Combat.getInstance().locationString(configuredSurfaceBuilder.getKey()), configuredSurfaceBuilder.getValue());
//		}
	}
	
	public static int getBiomeIndex(ResourceKey<Biome> key) {
		int i = 0;
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (biome.getRegistryName().equals(key.location())) {
				return i;
			}
			i++;
		}
		return -1;
	}
}
