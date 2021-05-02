package com.stereowalker.combat.world.oldbiome;
//package com.stereowalker.combat.world.biome;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.stereowalker.combat.Combat;
//import com.stereowalker.combat.world.biome.acrotlest.AcrotlestBiome;
//import com.stereowalker.combat.world.biome.acrotlest.AcrotlestForestBiome;
//import com.stereowalker.combat.world.biome.acrotlest.AcrotlestMountainsBiome;
//import com.stereowalker.combat.world.biome.acrotlest.HisovSandsBiome;
//
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeManager;
//import net.minecraftforge.common.BiomeManager.BiomeEntry;
//import net.minecraftforge.common.BiomeManager.BiomeType;
//import net.minecraftforge.registries.IForgeRegistry;
//
//public class OldCBiomes {
//	public static final Map<Biome, BiomeDictionary.Type[]> TYPES = new HashMap<Biome, BiomeDictionary.Type[]>();
//	public static final Biome DEAD_FOREST = register("dead_forest", new DeadForestBiome(), BiomeType.WARM, false, 1 , BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD , BiomeDictionary.Type.OVERWORLD);
//	public static final Biome NAN = register("nan", new NanBiome(), BiomeType.COOL, false, 1, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.MAGICAL , BiomeDictionary.Type.OVERWORLD);
//	
//	public static final Biome ACROTLEST_FOREST = registerAc("acrotlest_forest", new AcrotlestForestBiome(), BiomeType.ICY, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY );
//	public static final Biome ACROTLEST_MOUNTAINS = registerAc("acrotlest_mountains", new AcrotlestMountainsBiome(), BiomeType.ICY, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
//	public static final Biome ACROTLEST_RIVER= registerAc("acrotlest_river", new AcrotlestRiverBiome(), BiomeType.ICY, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY);
//	public static final Biome HISOV_SANDS = registerAc("hisov_sands", new HisovSandsBiome(), BiomeType.ICY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.COLD , BiomeDictionary.Type.DEAD );
//	
//	private static Biome register(String name, Biome biome, BiomeType biometype, boolean shouldPlayerSpawnIn, int weight , BiomeDictionary.Type... types ) {
//		biome.setRegistryName(Combat.location(name));
//		for (int i = 0; i < types.length; i++) {
//			if (types[i] == BiomeDictionary.Type.OVERWORLD) {
//				BiomeManager.addBiome(biometype, new BiomeEntry(biome, weight));
//			}
//			//TODO: Undo this once you can create dimensions
//			if (biome instanceof AcrotlestBiome) {
//				BiomeManager.addBiome(biometype, new BiomeEntry(biome, weight));
//			}
//		}
//		if(shouldPlayerSpawnIn)BiomeManager.addSpawnBiome(biome);
//		TYPES.put(biome, types);
//		return biome;
//	}
//	
//	public static Biome register (String name, Biome biome, BiomeType biometype, BiomeDictionary.Type... types) {
//		return register(name, biome, biometype, false, 0, types);
//	}
//	
//	public static Biome registerAc(String name, Biome biome, BiomeType biometype, BiomeDictionary.Type... types) {
//		return register(name, biome, biometype, false, 1, types);
//	}
//
//	public static void registerAll(IForgeRegistry<Biome> registry) {
//		for(Biome biome: TYPES.keySet()) {
//			registry.register(biome);
//			Combat.debug("Biome: \""+biome.getRegistryName().toString()+"\" registered");
//			BiomeDictionary.addTypes(biome, TYPES.get(biome));
//		}
//		Combat.debug("All Biomes Registered");
//	}
//
//}
