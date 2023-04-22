package com.stereowalker.combat.world.level.biome;

//import com.stereowalker.combat.data.worldgen.CSurfaceBuilders;
import com.stereowalker.combat.data.worldgen.biome.CombatBiomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CBiomeRegistry {

	public static void registerAll(RegisterHelper<Biome> registry) {
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(CBiomes.DEAD_FOREST, 1));
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(CBiomes.DEAD_PLAINS, 1));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(CBiomes.MAGIC_FOREST, 1));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(CBiomes.MAGIC_PLAINS, 1));
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(CBiomes.ACROTLEST_FOREST, 0));
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(CBiomes.ACROTLEST_MOUNTAINS, 0));
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(CBiomes.ACROTLEST_RIVER, 0));
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(CBiomes.HISOV_SANDS, 0));
	}
	
	public static void bootstrap(BootstapContext<Biome> pContext) {
	      HolderGetter<PlacedFeature> holdergetter = pContext.lookup(Registries.PLACED_FEATURE);
	      HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = pContext.lookup(Registries.CONFIGURED_CARVER);
	      pContext.register(CBiomes.DEAD_FOREST, CombatBiomes.makeDeadForestBiome(holdergetter, holdergetter1, 0.1f, false));
	      pContext.register(CBiomes.DEAD_PLAINS, CombatBiomes.makeDeadForestBiome(holdergetter, holdergetter1, 0.05f, true));
	      pContext.register(CBiomes.MAGIC_FOREST, CombatBiomes.makeMagicalBiome(holdergetter, holdergetter1, 0.2f, false));
	      pContext.register(CBiomes.MAGIC_PLAINS, CombatBiomes.makeMagicalBiome(holdergetter, holdergetter1, 0.125f, true));
	      pContext.register(CBiomes.ACROTLEST_FOREST, CombatBiomes.makeAcrotlestForestBiome(holdergetter, holdergetter1));
	      pContext.register(CBiomes.ACROTLEST_MOUNTAINS, CombatBiomes.makeAcrotlestMountainsBiome(holdergetter, holdergetter1, 5.0f));
	      pContext.register(CBiomes.ACROTLEST_RIVER, CombatBiomes.makeAcrotlestRiverBiome(holdergetter, holdergetter1, 2.7f, 0.0f, 0.0f, 0x55aaff, true));
	      pContext.register(CBiomes.HISOV_SANDS, CombatBiomes.makeHisovSandsBiome(holdergetter, holdergetter1, 4.5f));
	}
	
//	public static int getBiomeIndex(ResourceKey<Biome> key) {
//		int i = 0;
//		for (Biome biome : ForgeRegistries.BIOMES) {
//			if (biome.getRegistryName().equals(key.location())) {
//				return i;
//			}
//			i++;
//		}
//		return -1;
//	}
}
