package com.stereowalker.combat.event.handler;

import com.stereowalker.combat.world.biome.CBiomes;
import com.stereowalker.combat.world.biome.CombatBiomeFeatures;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class ModdedBiomeGeneration 
{
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void setupBiomeGeneration(BiomeLoadingEvent event)
	{	
		//TODO: Use The Helper Method For This Case
		if ((event.getCategory() != Category.NETHER && event.getCategory() != Category.THEEND) || hasType(event, BiomeDictionary.Type.OVERWORLD)) {
			if (event.getName().toString().equalsIgnoreCase(Biomes.LUKEWARM_OCEAN.getLocation().toString()) || 
					event.getName().toString().equalsIgnoreCase(Biomes.WARM_OCEAN.getLocation().toString()) || 
					event.getName().toString().equalsIgnoreCase(Biomes.DEEP_LUKEWARM_OCEAN.getLocation().toString()) || 
					event.getName().toString().equalsIgnoreCase(Biomes.DEEP_WARM_OCEAN.getLocation().toString())) {
				CombatBiomeFeatures.withLimestone(event.getGeneration());
			}
			CombatBiomeFeatures.withPasquem(event.getGeneration(), CBiomes.getDeadBiomes().contains(event.getName()));
			CombatBiomeFeatures.withTridox(event.getGeneration(), CBiomes.getMagicBiomes().contains(event.getName()));
			CombatBiomeFeatures.withYellowMagicClusters(event.getGeneration(), CBiomes.getMagicBiomes().contains(event.getName()));
			CombatBiomeFeatures.withCopper(event.getGeneration(), CBiomes.getDeadBiomes().contains(event.getName()));
			CombatBiomeFeatures.withCassiterite(event.getGeneration());
			CombatBiomeFeatures.withMagicStoneDeposits(event.getGeneration());
			
			if (hasTypeWithCategory(event, BiomeDictionary.Type.FOREST, Category.FOREST)) 
				CombatBiomeFeatures.withVampires(event.getSpawns());
			
			if (hasTypeWithCategory(event, BiomeDictionary.Type.PLAINS, Category.PLAINS) || hasTypeWithCategory(event, BiomeDictionary.Type.FOREST, Category.FOREST) || 
					hasTypeWithCategory(event, BiomeDictionary.Type.SANDY, Category.DESERT) || hasTypeWithCategory(event, BiomeDictionary.Type.CONIFEROUS, Category.TAIGA)) 
				CombatBiomeFeatures.withRubies(event.getGeneration());
			
			if (!hasTypeWithCategory(event, BiomeDictionary.Type.OCEAN, Category.OCEAN) && 
					!hasTypeWithCategory(event, BiomeDictionary.Type.RIVER, Category.RIVER)) 
				CombatBiomeFeatures.withEtherionTower(event.getGeneration(), CBiomes.getAcrotlestBiomes().contains(event.getName()));
			
			if (event.getCategory() != Category.OCEAN && 
					event.getCategory() != Category.RIVER && 
					event.getCategory() == Category.ICY) 
				CombatBiomeFeatures.withAcrotlestPortal(event.getGeneration(), CBiomes.getAcrotlestBiomes().contains(event.getName()));
		}
		//TODO: Re Add Structures
//		for(Biome biome : ForgeRegistries.BIOMES)
//		{
//			CountRangeConfig oil_placement = new CountRangeConfig(Config.oilChance.get(), 10, 10, 11);
//			if (biome == OldCBiomes.DEAD_FOREST) {
//				if(Config.generate_oil.get())biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, CBlocks.OIL.getDefaultState(), 1)).withPlacement(Placement.COUNT_RANGE.configure(oil_placement)));
//			} else if (biome == OldCBiomes.NAN) {
//				if(Config.generate_oil.get())biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, CBlocks.OIL.getDefaultState(), 2)).withPlacement(Placement.COUNT_RANGE.configure(oil_placement)));
//			} else {
//				if(Config.generate_oil.get())biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, CBlocks.OIL.getDefaultState(), 2)).withPlacement(Placement.COUNT_RANGE.configure(oil_placement)));
//			}
//		}
	}
	
	public static boolean hasType(BiomeLoadingEvent biome, BiomeDictionary.Type type) {
		return BiomeDictionary.hasType(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, biome.getName()), type);
	}
	
	public static boolean hasTypeWithCategory(BiomeLoadingEvent biome, BiomeDictionary.Type type, Category cat) {
		return hasType(biome, type) || biome.getCategory() == cat;
	}
}
