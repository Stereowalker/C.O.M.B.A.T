package com.stereowalker.combat.world.biome;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.biome.provider.AcrotlestBiomeProvider;
import com.stereowalker.combat.world.gen.surfacebuilders.CConfiguredSurfaceBuilders;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class CBiomeRegistry {

	private static final Int2ObjectMap<RegistryKey<Biome>> idToKeyMap = new Int2ObjectArrayMap<>();

//	@SuppressWarnings("deprecation")
	private static Biome register(int id, RegistryKey<Biome> key, Biome biome) {
		idToKeyMap.put(id, key);
		biome.setRegistryName(key.getLocation());
		return biome;
	}

	public static void registerAll(IForgeRegistry<Biome> registry) {
		int i = 300;
		for(Pair<RegistryKey<Biome>,Biome> biome: CBiomes.BIOMES) {
			registry.register(register(i, biome.getLeft(), biome.getRight()));
			Combat.debug("Biome: \""+biome.getLeft().getRegistryName().toString()+"\" registered");
			i++;
		}
		Combat.debug("All Biomes Registered");
		
		Registry.register(Registry.BIOME_PROVIDER_CODEC, "combat:acrotlest", AcrotlestBiomeProvider.CODEC);
		
		for(Pair<String,ConfiguredSurfaceBuilder<?>> configuredSurfaceBuilder: CConfiguredSurfaceBuilders.CONFIGURED_SURFACE_BUILDERS) {
			WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, Combat.getInstance().locationString(configuredSurfaceBuilder.getKey()), configuredSurfaceBuilder.getValue());
		}
	}
	
	public static int getBiomeIndex(RegistryKey<Biome> key) {
		int i = 0;
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (biome.getRegistryName().equals(key.getLocation())) {
				return i;
			}
			i++;
		}
		return -1;
	}
}
