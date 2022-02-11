package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeCombatFeatures {
	public static void addLimestone(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_limestone.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_LIMESTONE);
	}
	
	public static void addRubies(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_ruby_ore.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_RUBY);
	}
	
	public static void addCassiterite(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_cassiterite.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_CASSITERITE);
	}
	
	public static void addTridox(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_TRIDOX_MAGIC);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_TRIDOX);
		}
	}
	
	public static void addPasquem(BiomeGenerationSettings.Builder builder, boolean isDeadBiome) {
		if (isDeadBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_PASQUEM);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PASQUEM);
		}
	}

	public static void addDeadOres(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_COAL);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_IRON);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_COPPER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_GOLD);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_REDSTONE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_DIAMOND);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_LAPIS);
	}
	
	public static void addYellowMagicClusters(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_YELLOW_CLUSTER_MAGIC);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_YELLOW_CLUSTER);
		}
	}
	
	//TODO: Re-purpose for something else
//	public static void addCopper(BiomeGenerationSettings.Builder builder, boolean isDeadBiome) {
//		if (Config.SERVER.generate_copper_ore.get()) {
//			if (isDeadBiome) {
//				builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.DEAD_ORE_COPPER);
//			}
//			else {
//				builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_COPPER);
//			}
//		}
//	}
	
	public static void addMagicStoneDeposits(BiomeGenerationSettings.Builder builder) {
		builder.addStructureStart(CStructureFeatures.MAGIC_STONE_DEPOSIT);
	}
	
	public static void addAcrotlestPortal(BiomeGenerationSettings.Builder builder, boolean isAcrotlestBiome) {
		if (isAcrotlestBiome) {
			builder.addStructureStart(CStructureFeatures.ACROTLEST_PORTAL);
		}
		else {
			builder.addStructureStart(CStructureFeatures.OVERWORLD_PORTAL);
		}
	}
	
	public static void addEtherionTower(BiomeGenerationSettings.Builder builder, boolean isAcrotlestBiome) {
		if (isAcrotlestBiome) {
			builder.addStructureStart(CStructureFeatures.ETHERION_TOWER_ACROTLEST);
		}
		else {
			builder.addStructureStart(CStructureFeatures.ETHERION_TOWER);
		}
	}

	public static void addVampires(MobSpawnSettings.Builder builder) {
		builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.VAMPIRE, 1, 1, 1));
	}

	public static void addAcrotlestCarvers(BiomeGenerationSettings.Builder builder) {
		builder.addCarver(GenerationStep.Carving.AIR, CCarvers.ACROTLEST_CANYON);
		builder.addCarver(GenerationStep.Carving.AIR, CCarvers.ACROTLEST_CAVE);
	}

	public static void addAcrotlestOres(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PELGAN);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_LOZYNE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_SERABLE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PYRANITE);
	}
	
	public static void addAcrotlestStructures(BiomeGenerationSettings.Builder builder) {
		builder.addStructureStart(CStructureFeatures.ACROTLEST_MINESHAFT_NORMAL);
		//builder.addStructureStart(CStructureFeatures.BLUE_ICE_TOWER);
		builder.addStructureStart(StructureFeatures.VILLAGE_SNOWY);
	}
//
//	//	public static void addAcrotlestStructures(Biome biomeIn) {
//	//		biomeIn.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CFeature.ACROTLEST_MINESHAFT.withConfiguration(new AcrotlestMineshaftConfig((double)0.004F, AcrotlestMineshaftStructure.Type.NORMAL)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CFeature.BLUE_ICE_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CFeature.OVERWORLD_PORTAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.withConfiguration(new VillageConfig("village/plains/town_centers", 6)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CFeature.ETHERION_TOWER.withConfiguration(new ProbabilityStructureConfig(Config.etherionTowerProbability.get().floatValue())).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//	}
//
//	public static void addAcrotlestLakes(Biome biomeIn) {
//		biomeIn.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(BIABLE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
//		biomeIn.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(WHITE_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(YELLOW_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(RED_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(PURPLE_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//	}

	public static void addAcrotlestLakes(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.LAKES, CFeatures.LAKE_BIABLE);
		builder.addFeature(GenerationStep.Decoration.LAKES, CFeatures.LAKE_WHITE_TSUNE);
		builder.addFeature(GenerationStep.Decoration.LAKES, CFeatures.LAKE_YELLOW_TSUNE);
		builder.addFeature(GenerationStep.Decoration.LAKES, CFeatures.LAKE_RED_TSUNE);
		builder.addFeature(GenerationStep.Decoration.LAKES, CFeatures.LAKE_PURPLE_TSUNE);
	}
}
