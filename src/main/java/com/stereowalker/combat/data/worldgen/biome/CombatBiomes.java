package com.stereowalker.combat.data.worldgen.biome;

import com.stereowalker.combat.data.worldgen.BiomeCombatFeatures;
//import com.stereowalker.combat.data.worldgen.CSurfaceBuilders;
import com.stereowalker.combat.data.worldgen.placement.CVegetationPlacements;
import com.stereowalker.combat.data.worldgen.placement.MiscAcrotlestPlacements;
import com.stereowalker.combat.world.entity.CEntityType;
//import com.stereowalker.combat.world.level.levelgen.surfacebuilders.CSurfaceBuilder;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
//import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
//import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;

public class CombatBiomes {
	public static Biome makeDeadForestBiome(float depth, boolean isPlains) {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 16, 16));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.ZOMBIE_COW, 800, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 200, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 190, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 10, 2, 2));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 200, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 200, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 200, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 20, 2, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 10, 2, 2));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * new
																													 * ConfiguredSurfaceBuilder
																													 * <
																													 * >
																													 * (
																													 * SurfaceBuilder
																													 * .
																													 * DEFAULT,
																													 * SurfaceBuilder
																													 * .
																													 * CONFIG_GRASS
																													 * )
																													 * )
																													 */;
		//		BiomeDefaultFeatures.func_235196_b_(this);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
		BiomeCombatFeatures.addDeadOres(biomegenerationsettings$builder);
		if (!isPlains) biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CVegetationPlacements.TREES_DEAD_OAK);
		BiomeDefaultFeatures.addShatteredSavannaGrass(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addFossilDecoration(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		//		      BiomeDefaultFeatures.func_222311_aa(this);
		BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
		//		      BiomeDefaultFeatures.func_222297_ap(this);

		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN)
				/* .scale(0.2F) */.temperature(0.7F).downfall(0.8F).biomeCategory(isPlains?Biome.BiomeCategory.PLAINS:Biome.BiomeCategory.FOREST)/**/.specialEffects(getDeadAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

	}

	public static Biome makeAcrotlestRiverBiome(float depth, float scale, float temperature, int waterColor, boolean isSnowy) {
//		super((new Biome.BiomeBuilder()).surfaceBuilder(SurfaceBuilder.DEFAULT, CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG)
//	      this.func_235063_a_(CombatBiomeFeatures.ACROTLEST_MINESHAFT_NORMAL);
//	      CombatBiomeFeatures.addAcrotlestCarvers(this);
//	      CombatBiomeFeatures.addAcrotlestStructures(this);
//	      CombatBiomeFeatures.addAcrotlestLakes(this);
//	      CombatBiomeFeatures.addAcrotlestOres(this);
//	      CombatBiomeFeatures.addMonorisTrees(this);
//	      BiomeDefaultFeatures.addSparseGrass(this);


		MobSpawnSettings.Builder mobspawninfo$builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.LICHU, 50, 6, 6));
		BiomeDefaultFeatures.caveSpawns(mobspawninfo$builder);
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * CSurfaceBuilders
																													 * .
																													 * PURIFIED_GRASS)
																													 */;
//		biomegenerationsettings$builder.addStructureStart(StructureFeatures.field_244136_b);
//		biomegenerationsettings$builder.addStructureStart(StructureFeatures.field_244159_y);
//		BiomeDefaultFeatures.addDefaultCarvers(biomegenerationsettings$builder);
//		BiomeDefaultFeatures.addDefaultLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
//		BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
//		BiomeDefaultFeatures.withTreesInWater(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
//		BiomeDefaultFeatures.withBadlandsGrass(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
//		if (!isSnowy) {
//			biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
//		}

		BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
		return (new Biome.BiomeBuilder()).precipitation(isSnowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.RIVER).temperature(temperature).downfall(0.5F).specialEffects(getAcrotlestAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static Biome makeMagicalBiome(float depth, boolean isPlains) {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		mobspawninfo$builder.creatureGenerationProbability(0.5f);
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 10, 5, 10));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 1, 2, 6));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 1));
		mobspawninfo$builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * new
																													 * ConfiguredSurfaceBuilder
																													 * <
																													 * >
																													 * (
																													 * SurfaceBuilder
																													 * .
																													 * DEFAULT,
																													 * CSurfaceBuilder
																													 * .
																													 * ELYCEN_CALTAS_GRAVEL_CONFIG
																													 * )
																													 * )
																													 */;
		BiomeDefaultFeatures.addDefaultCarversAndLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addShatteredSavannaGrass(biomegenerationsettings$builder);
		if (!isPlains) biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CVegetationPlacements.TREES_MAGICAL);
		BiomeDefaultFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
		//DE BiomeDefaultFeatures.addSparseGrass(this);

		/*
		 * BiomeDefaultFeatures.addDefaultOverworldLandStructures(
		 * biomegenerationsettings$builder);
		 */
		//		BiomeDefaultFeatures.withForestRocks(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.withLargeFern(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.withGiantTaigaGrassVegetation(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
		//		BiomeDefaultFeatures.withSparseBerries(biomegenerationsettings$builder);


		return (new Biome.BiomeBuilder()).downfall(50F).precipitation(Biome.Precipitation.RAIN)
				/* .scale(0.10F) */.temperature(0.1F).biomeCategory(isPlains?Biome.BiomeCategory.PLAINS:Biome.BiomeCategory.FOREST)/**/.specialEffects(getMagicAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeAcrotlestForestBiome() {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.BIOG, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.LICHU, 50, 6, 6));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * new
																													 * ConfiguredSurfaceBuilder
																													 * <
																													 * >
																													 * (
																													 * SurfaceBuilder
																													 * .
																													 * DEFAULT,
																													 * CSurfaceBuilder
																													 * .
																													 * PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG
																													 * )
																													 * )
																													 */;
		
		BiomeCombatFeatures.addAcrotlestCarvers(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestStructures(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CVegetationPlacements.TREES_MONORIS);
		BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addShatteredSavannaGrass(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscAcrotlestPlacements.TSUNE_SPIKE);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
		BiomeCombatFeatures.addAcrotlestSprings(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);


		return (new Biome.BiomeBuilder()).downfall(0.8F).precipitation(Biome.Precipitation.SNOW)
				/* .scale(0.2F) */.temperature(0.1F).biomeCategory(Biome.BiomeCategory.FOREST).specialEffects(getAcrotlestAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeAcrotlestMountainsBiome(float depth) {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.BIOG, 100, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.LICHU, 50, 6, 6));
		
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * new
																													 * ConfiguredSurfaceBuilder
																													 * <
																													 * >
																													 * (
																													 * SurfaceBuilder
																													 * .
																													 * DEFAULT,
																													 * CSurfaceBuilder
																													 * .
																													 * PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG
																													 * )
																													 * )
																													 */;
		BiomeCombatFeatures.addAcrotlestStructures(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestCarvers(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscAcrotlestPlacements.TSUNE_SPIKE);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
		BiomeDefaultFeatures.addShatteredSavannaGrass(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);

		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.ICY)
				/* .scale(0.3F) */.temperature(0.0F).downfall(0.8F).specialEffects(getAcrotlestAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeHisovSandsBiome(float depth) {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.RED_BIOG, 25, 1, 1));
		mobspawninfo$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CEntityType.LICHU, 50, 6, 6));
		
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())/*
																													 * .surfaceBuilder
																													 * (
																													 * new
																													 * ConfiguredSurfaceBuilder
																													 * <
																													 * >
																													 * (
																													 * SurfaceBuilder
																													 * .
																													 * DEFAULT,
																													 * CSurfaceBuilder
																													 * .
																													 * HOMSE_HOMSE_GRAVEL_CONFIG
																													 * )
																													 * )
																													 */;
		BiomeCombatFeatures.addAcrotlestCarvers(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestStructures(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestLakes(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
//		BiomeDefaultFeatures.addDoubleFlowers(this);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
		BiomeCombatFeatures.addAcrotlestOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
		biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscAcrotlestPlacements.MAGENTA_TSUNE_COLUMN);
		BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);

		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.SNOW)
				.biomeCategory(Biome.BiomeCategory.DESERT)
				/* .scale(0.05F) */.temperature(1.0F).downfall(0.1F).specialEffects(getAcrotlestAmbience()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static BiomeSpecialEffects getDeadAmbience() {		//R+0,G+1,B+3			<-----				//R+0,G+2,B+3				<----				//R+0,G+3,B+1				<------
		return new BiomeSpecialEffects.Builder().waterFogColor(0x000103).waterColor(0x000103).fogColor(0x000203).skyColor(0x000203).grassColorOverride(0x283132).foliageColorOverride(0x283132).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
	}

	public static BiomeSpecialEffects getMagicAmbience() {		//R-2,G+1,B+0			<-----				//R-2,G+1,B+1				<----				//R-2,G-2,B+1				<------
		return new BiomeSpecialEffects.Builder().waterFogColor(0xf903ff).waterColor(0xf903ff).fogColor(0x3633D0).skyColor(0x3633D0).grassColorOverride(0x3629D0).foliageColorOverride(0x3629D0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
	}

	public static BiomeSpecialEffects getAcrotlestAmbience() {
		return new BiomeSpecialEffects.Builder().waterFogColor(329011).waterColor(4159204).fogColor(12638463).skyColor(11487769).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();
	}
}
