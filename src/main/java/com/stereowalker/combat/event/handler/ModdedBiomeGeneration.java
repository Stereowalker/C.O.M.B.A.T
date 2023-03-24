package com.stereowalker.combat.event.handler;

import com.stereowalker.combat.data.worldgen.BiomeCombatFeatures;
import com.stereowalker.combat.tags.BiomeCTags;
import com.stereowalker.combat.world.level.biome.CBiomes;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.Tags;
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
		if ((event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) || hasType(event, BiomeDictionary.Type.OVERWORLD)) {
			if (BuiltinRegistries.BIOME.getHolderOrThrow(ResourceKey.create(Registry.BIOME_REGISTRY, event.getName())).is(BiomeCTags.IS_WARM_OCEAN)) {
				BiomeCombatFeatures.addLimestone(event.getGeneration());
			}
			BiomeCombatFeatures.addPasquem(event.getGeneration(), CBiomes.getDeadBiomes().contains(event.getName()));
			BiomeCombatFeatures.addTridox(event.getGeneration(), CBiomes.getMagicBiomes().contains(event.getName()));
			BiomeCombatFeatures.addYellowMagicClusters(event.getGeneration(), CBiomes.getMagicBiomes().contains(event.getName()));
			BiomeCombatFeatures.addCassiterite(event.getGeneration());
			
			if (hasTypeWithCategory(event, BiomeDictionary.Type.FOREST, Biome.BiomeCategory.FOREST)) 
				BiomeCombatFeatures.addVampires(event.getSpawns());
			
			if (hasTypeWithCategory(event, BiomeDictionary.Type.PLAINS, Biome.BiomeCategory.PLAINS) || hasTypeWithCategory(event, BiomeDictionary.Type.FOREST, Biome.BiomeCategory.FOREST) || 
					hasTypeWithCategory(event, BiomeDictionary.Type.SANDY, Biome.BiomeCategory.DESERT) || hasTypeWithCategory(event, BiomeDictionary.Type.CONIFEROUS, Biome.BiomeCategory.TAIGA)) 
				BiomeCombatFeatures.addRubies(event.getGeneration());
			
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
		return BiomeDictionary.hasType(ResourceKey.create(Registry.BIOME_REGISTRY, biome.getName()), type);
	}
	
	public static boolean hasTypeWithCategory(BiomeLoadingEvent biome, BiomeDictionary.Type type, Biome.BiomeCategory cat) {
		return hasType(biome, type) || biome.getCategory() == cat;
	}
}
