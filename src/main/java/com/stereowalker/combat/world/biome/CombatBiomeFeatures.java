package com.stereowalker.combat.world.biome;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.world.gen.carver.CConfiguredCarvers;
import com.stereowalker.combat.world.gen.feature.CFeatures;
import com.stereowalker.combat.world.gen.feature.structure.CStructureFeatures;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class CombatBiomeFeatures {
	public static void withLimestone(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_limestone.get()) builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_LIMESTONE);
	}
	
	public static void withRubies(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_ruby_ore.get()) builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_RUBY);
	}
	
	public static void withCassiterite(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_cassiterite.get()) builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_CASSITERITE);
	}
	
	public static void withTridox(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_TRIDOX_MAGIC);
		}
		else {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_TRIDOX);
		}
	}
	
	public static void withPasquem(BiomeGenerationSettings.Builder builder, boolean isDeadBiome) {
		if (isDeadBiome) {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PASQUEM_DEAD);
		}
		else {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PASQUEM);
		}
	}

	public static void withDeadOres(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_COAL_DEAD);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_IRON_DEAD);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_GOLD_DEAD);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_REDSTONE_DEAD);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_DIAMOND_DEAD);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_LAPIS_DEAD);
	}
	
	public static void withYellowMagicClusters(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_YELLOW_CLUSTER_MAGIC);
		}
		else {
			builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_YELLOW_CLUSTER);
		}
	}
	
	public static void withCopper(BiomeGenerationSettings.Builder builder, boolean isDeadBiome) {
		if (Config.SERVER.generate_copper_ore.get()) {
			if (isDeadBiome) {
				builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_COPPER_DEAD);
			}
			else {
				builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_COPPER);
			}
		}
	}
	
	public static void withMagicStoneDeposits(BiomeGenerationSettings.Builder builder) {
		builder.withStructure(CStructureFeatures.MAGIC_STONE_DEPOSIT);
	}
	
	public static void withAcrotlestPortal(BiomeGenerationSettings.Builder builder, boolean isAcrotlestBiome) {
		if (isAcrotlestBiome) {
			builder.withStructure(CStructureFeatures.ACROTLEST_PORTAL);
		}
		else {
			builder.withStructure(CStructureFeatures.OVERWORLD_PORTAL);
		}
	}
	
	public static void withEtherionTower(BiomeGenerationSettings.Builder builder, boolean isAcrotlestBiome) {
		if (isAcrotlestBiome) {
			builder.withStructure(CStructureFeatures.ETHERION_TOWER_ACROTLEST);
		}
		else {
			builder.withStructure(CStructureFeatures.ETHERION_TOWER);
		}
	}

	public static void withVampires(MobSpawnInfo.Builder builder) {
		builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.VAMPIRE, 1, 1, 1));
	}

	public static void withAcrotlestCavesAndCanyons(BiomeGenerationSettings.Builder builder) {
		builder.withCarver(GenerationStage.Carving.AIR, CConfiguredCarvers.ACROTLEST_CANYON);
		builder.withCarver(GenerationStage.Carving.AIR, CConfiguredCarvers.ACROTLEST_CAVE);
	}

	public static void withAcrotlestOres(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PELGAN);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_LOZYNE);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_SERABLE);
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CFeatures.ORE_PYRANITE);
	}
	
	public static void withAcrotlestStructures(BiomeGenerationSettings.Builder builder) {
		builder.withStructure(CStructureFeatures.ACROTLEST_MINESHAFT_NORMAL);
		//builder.withStructure(CStructureFeatures.BLUE_ICE_TOWER);
		builder.withStructure(StructureFeatures.VILLAGE_SNOWY);
	}
//
//	//	public static void addAcrotlestStructures(Biome biomeIn) {
//	//		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, CFeature.ACROTLEST_MINESHAFT.withConfiguration(new AcrotlestMineshaftConfig((double)0.004F, AcrotlestMineshaftStructure.Type.NORMAL)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeature.BLUE_ICE_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeature.OVERWORLD_PORTAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.withConfiguration(new VillageConfig("village/plains/town_centers", 6)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//		biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeature.ETHERION_TOWER.withConfiguration(new ProbabilityStructureConfig(Config.etherionTowerProbability.get().floatValue())).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//	//	}
//
//	public static void addAcrotlestLakes(Biome biomeIn) {
//		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(BIABLE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
//		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(WHITE_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(YELLOW_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(RED_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(PURPLE_TSUNE)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(50))));
//	}

	public static void withAcrotlestLakes(BiomeGenerationSettings.Builder builder) {
		builder.withFeature(GenerationStage.Decoration.LAKES, CFeatures.LAKE_BIABLE);
		builder.withFeature(GenerationStage.Decoration.LAKES, CFeatures.LAKE_WHITE_TSUNE);
		builder.withFeature(GenerationStage.Decoration.LAKES, CFeatures.LAKE_YELLOW_TSUNE);
		builder.withFeature(GenerationStage.Decoration.LAKES, CFeatures.LAKE_RED_TSUNE);
		builder.withFeature(GenerationStage.Decoration.LAKES, CFeatures.LAKE_PURPLE_TSUNE);
	}
}
