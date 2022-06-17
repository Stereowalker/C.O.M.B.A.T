package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.data.worldgen.placement.COrePlacements;
import com.stereowalker.combat.data.worldgen.placement.MiscAcrotlestPlacements;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeCombatFeatures {
	public static void addLimestone(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_limestone.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_LIMESTONE);
	}
	
	public static void addRubies(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_ruby_ore.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_RUBY);
	}
	
	public static void addCassiterite(BiomeGenerationSettings.Builder builder) {
		if (Config.SERVER.generate_cassiterite.get()) builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_CASSITERITE);
	}
	
	public static void addTridox(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_TRIDOX_MAGIC);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_TRIDOX);
		}
	}
	
	public static void addPasquem(BiomeGenerationSettings.Builder builder, boolean isDeadBiome) {
		if (isDeadBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_PASQUEM);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_PASQUEM);
		}
	}

	public static void addDeadOres(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_COAL);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_IRON);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_COPPER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_GOLD);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_REDSTONE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_DIAMOND);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.DEAD_ORE_LAPIS);
	}
	
	public static void addYellowMagicClusters(BiomeGenerationSettings.Builder builder, boolean isMagicBiome) {
		if (isMagicBiome) {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_YELLOW_CLUSTER_MAGIC);
		}
		else {
			builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_YELLOW_CLUSTER);
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
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_PELGAN);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_LOZYNE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_SERABLE);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, COrePlacements.ORE_PYRANITE);
	}
	
	public static void addAcrotlestStructures(BiomeGenerationSettings.Builder builder) {
	}
	
	public static void addAcrotlestSprings(BiomeGenerationSettings.Builder pBuilder) {
	      pBuilder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscAcrotlestPlacements.SPRING_BIABLE);
	}

	public static void addAcrotlestLakes(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.LAKES, MiscAcrotlestPlacements.LAKE_WHITE_TSUNE_UNDERGROUND);
		builder.addFeature(GenerationStep.Decoration.LAKES, MiscAcrotlestPlacements.LAKE_YELLOW_TSUNE_UNDERGROUND);
		builder.addFeature(GenerationStep.Decoration.LAKES, MiscAcrotlestPlacements.LAKE_RED_TSUNE_SURFACE);
		builder.addFeature(GenerationStep.Decoration.LAKES, MiscAcrotlestPlacements.LAKE_PURPLE_TSUNE_SURFACE);
	}
}
