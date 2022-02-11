package com.stereowalker.combat.world.level.biome;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.biome.CombatBiomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class CBiomes {
	public static final List<Pair<ResourceKey<Biome>,Biome>> BIOMES = new ArrayList<Pair<ResourceKey<Biome>,Biome>>();
//	public static final Biome HISOV_SANDS = registerAc("hisov_sands", new HisovSandsBiome(), BiomeType.ICY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD );

	public static final Type ACROTLEST = Type.getType("ACROTLEST");

	public static final ResourceKey<Biome> DEAD_FOREST = addToList(makeKey("dead_forest"), CombatBiomes.makeDeadForestBiome(0.1f, false), BiomeType.WARM, 1/*50*/ , BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD , BiomeDictionary.Type.OVERWORLD);
	public static final ResourceKey<Biome> DEAD_PLAINS = addToList(makeKey("dead_plains"), CombatBiomes.makeDeadForestBiome(0.05f, true), BiomeType.WARM, 1 , BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD , BiomeDictionary.Type.OVERWORLD);
	public static final ResourceKey<Biome> MAGIC_FOREST = addToList(makeKey("magic_forest"), CombatBiomes.makeMagicalBiome(0.2f, false), BiomeType.COOL, 1, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.MAGICAL , BiomeDictionary.Type.OVERWORLD);
	public static final ResourceKey<Biome> MAGIC_PLAINS = addToList(makeKey("magic_plains"), CombatBiomes.makeMagicalBiome(0.125f, true), BiomeType.COOL, 1, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD , BiomeDictionary.Type.MAGICAL , BiomeDictionary.Type.OVERWORLD);
	
	public static final ResourceKey<Biome> ACROTLEST_FOREST = addToList(makeKey("acrotlest_forest"), CombatBiomes.makeAcrotlestForestBiome(4.0f), BiomeType.ICY, 0, ACROTLEST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final ResourceKey<Biome> ACROTLEST_MOUNTAINS = addToList(makeKey("acrotlest_mountains"), CombatBiomes.makeAcrotlestMountainsBiome(5.0f), BiomeType.ICY, 0, ACROTLEST,BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final ResourceKey<Biome> ACROTLEST_RIVER = addToList(makeKey("acrotlest_river"), CombatBiomes.makeAcrotlestRiverBiome(2.7f, 0.0f, 0.0f, 0x55aaff, true), BiomeType.ICY, 0, ACROTLEST,BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
	public static final ResourceKey<Biome> HISOV_SANDS = addToList(makeKey("hisov_sands"), CombatBiomes.makeHisovSandsBiome(4.5f), BiomeType.ICY, 0, ACROTLEST, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD);

	private static ResourceKey<Biome> makeKey(String name) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, Combat.getInstance().location(name));
	}
	
	public static List<ResourceLocation> getAcrotlestBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(ACROTLEST).forEach((biome) -> {
			list.add(biome.location());
		});
		return list;
	}
	
	public static List<ResourceLocation> getMagicBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(Type.MAGICAL).forEach((biome) -> {
			list.add(biome.location());
		});
		return list;
//		return Lists.newArrayList(CBiomes.MAGIC_FOREST.getLocation(), CBiomes.MAGIC_PLAINS.getLocation());
	}
	
	public static List<ResourceLocation> getDeadBiomes(){
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		BiomeDictionary.getBiomes(Type.DEAD).forEach((biome) -> {
			list.add(biome.location());
		});
		return list;
//		return Lists.newArrayList(CBiomes.DEAD_FOREST.getLocation(), CBiomes.DEAD_PLAINS.getLocation());
	}
	
	private static ResourceKey<Biome> addToList(ResourceKey<Biome> key, Biome biome, BiomeType type, int weight, BiomeDictionary.Type... types) {
		BiomeManager.addBiome(type, new BiomeEntry(key, weight));
		Pair<ResourceKey<Biome>,Biome> pair = Pair.of(key, biome);
		BiomeDictionary.addTypes(key, types);
		BIOMES.add(pair);
		return key;
	}
}
