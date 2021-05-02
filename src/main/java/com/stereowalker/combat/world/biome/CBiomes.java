package com.stereowalker.combat.world.biome;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class CBiomes {
	public static final List<Pair<RegistryKey<Biome>,Biome>> BIOMES = new ArrayList<Pair<RegistryKey<Biome>,Biome>>();
//	public static final Biome HISOV_SANDS = registerAc("hisov_sands", new HisovSandsBiome(), BiomeType.ICY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD );

	public static final Type ACROTLEST = Type.getType("ACROTLEST");

	public static final RegistryKey<Biome> DEAD_FOREST = addToList(makeKey("dead_forest"), CBiomeMaker.makeDeadForestBiome(0.1f, false), BiomeType.WARM, 1/*50*/ , BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD , BiomeDictionary.Type.OVERWORLD);
	public static final RegistryKey<Biome> DEAD_PLAINS = addToList(makeKey("dead_plains"), CBiomeMaker.makeDeadForestBiome(0.05f, true), BiomeType.WARM, 1 , BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD , BiomeDictionary.Type.OVERWORLD);
	public static final RegistryKey<Biome> MAGIC_FOREST = addToList(makeKey("magic_forest"), CBiomeMaker.makeMagicalBiome(0.2f, false), BiomeType.COOL, 1, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.MAGICAL , BiomeDictionary.Type.OVERWORLD);
	public static final RegistryKey<Biome> MAGIC_PLAINS = addToList(makeKey("magic_plains"), CBiomeMaker.makeMagicalBiome(0.125f, true), BiomeType.COOL, 1, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD , BiomeDictionary.Type.MAGICAL , BiomeDictionary.Type.OVERWORLD);
	
	public static final RegistryKey<Biome> ACROTLEST_FOREST = addToList(makeKey("acrotlest_forest"), CBiomeMaker.makeAcrotlestForestBiome(), BiomeType.ICY, 0, ACROTLEST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final RegistryKey<Biome> ACROTLEST_MOUNTAINS = addToList(makeKey("acrotlest_mountains"), CBiomeMaker.makeAcrotlestMountainsBiome(), BiomeType.ICY, 0, ACROTLEST,BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final RegistryKey<Biome> ACROTLEST_RIVER = addToList(makeKey("acrotlest_river"), CBiomeMaker.makeAcrotlestRiverBiome(-0.6f, 0.0f, 0.0f, 0x55aaff, true), BiomeType.ICY, 0, ACROTLEST,BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final RegistryKey<Biome> HISOV_SANDS = addToList(makeKey("hisov_sands"), CBiomeMaker.makeHisovSandsBiome(), BiomeType.ICY, 0, ACROTLEST, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD);

	private static RegistryKey<Biome> makeKey(String name) {
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, Combat.getInstance().location(name));
	}
	
	public static List<ResourceLocation> getAcrotlestBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(ACROTLEST).forEach((biome) -> {
			list.add(biome.getLocation());
		});
		return list;
	}
	
	public static List<ResourceLocation> getMagicBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(Type.MAGICAL).forEach((biome) -> {
			list.add(biome.getLocation());
		});
		return list;
//		return Lists.newArrayList(CBiomes.MAGIC_FOREST.getLocation(), CBiomes.MAGIC_PLAINS.getLocation());
	}
	
	public static List<ResourceLocation> getDeadBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(Type.DEAD).forEach((biome) -> {
			list.add(biome.getLocation());
		});
		return list;
//		return Lists.newArrayList(CBiomes.DEAD_FOREST.getLocation(), CBiomes.DEAD_PLAINS.getLocation());
	}
	
	private static RegistryKey<Biome> addToList(RegistryKey<Biome> key, Biome biome, BiomeType type, int weight, BiomeDictionary.Type... types) {
		BiomeManager.addBiome(type, new BiomeEntry(key, weight));
		Pair<RegistryKey<Biome>,Biome> pair = Pair.of(key, biome);
		BiomeDictionary.addTypes(key, types);
		BIOMES.add(pair);
		return key;
	}
}
