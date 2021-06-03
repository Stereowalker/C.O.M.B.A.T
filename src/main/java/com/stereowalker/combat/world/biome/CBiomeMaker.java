package com.stereowalker.combat.world.biome;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.world.gen.feature.CFeatures;
import com.stereowalker.combat.world.gen.surfacebuilders.CConfiguredSurfaceBuilders;
import com.stereowalker.combat.world.gen.surfacebuilders.CSurfaceBuilder;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class CBiomeMaker {
	public static Biome makeDeadForestBiome(float depth, boolean isPlains) {
		MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 16, 16));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.ZOMBIE_COW, 800, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 200, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 190, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 10, 2, 2));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 200, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 200, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 200, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 20, 2, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 10, 2, 2));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
		//		DefaultBiomeFeatures.func_235196_b_(this);
		biomegenerationsettings$builder.withStructure(StructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.withCavesAndCanyons(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.addStructures(this);
		DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
		CombatBiomeFeatures.withDeadOres(biomegenerationsettings$builder);
		if (!isPlains) biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CFeatures.TREES_DEAD_OAK);
		DefaultBiomeFeatures.withNormalGrassPatch(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withFossils(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
		//		      DefaultBiomeFeatures.func_222311_aa(this);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
		//		      DefaultBiomeFeatures.func_222297_ap(this);

		return (new Biome.Builder())/**/.depth(depth).precipitation(RainType.RAIN).scale(0.2F).temperature(0.7F).downfall(0.8F).category(isPlains?Category.PLAINS:Category.FOREST)/**/.setEffects(getDeadAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();

	}

	public static Biome makeAcrotlestRiverBiome(float depth, float scale, float temperature, int waterColor, boolean isSnowy) {
//		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG)
//	      this.func_235063_a_(CombatBiomeFeatures.ACROTLEST_MINESHAFT_NORMAL);
//	      CombatBiomeFeatures.addAcrotlestCarvers(this);
//	      CombatBiomeFeatures.addAcrotlestStructures(this);
//	      CombatBiomeFeatures.addAcrotlestLakes(this);
//	      CombatBiomeFeatures.addAcrotlestOres(this);
//	      CombatBiomeFeatures.addMonorisTrees(this);
//	      DefaultBiomeFeatures.addSparseGrass(this);


		MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 2, 1, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.LICHU, 50, 6, 6));
		DefaultBiomeFeatures.withBats(mobspawninfo$builder);
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(CConfiguredSurfaceBuilders.PURIFIED_GRASS);
//		biomegenerationsettings$builder.withStructure(StructureFeatures.field_244136_b);
//		biomegenerationsettings$builder.withStructure(StructureFeatures.field_244159_y);
//		DefaultBiomeFeatures.withCavesAndCanyons(biomegenerationsettings$builder);
//		DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
//		DefaultBiomeFeatures.withOverworldOres(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
//		DefaultBiomeFeatures.withTreesInWater(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
//		DefaultBiomeFeatures.withBadlandsGrass(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
//		if (!isSnowy) {
//			biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
//		}

		DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);
		return (new Biome.Builder()).precipitation(isSnowy ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(Biome.Category.RIVER).depth(depth).scale(scale).temperature(temperature).downfall(0.5F).setEffects(getAcrotlestAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static Biome makeMagicalBiome(float depth, boolean isPlains) {
		MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
		mobspawninfo$builder.withCreatureSpawnProbability(0.5f);
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.MOOSHROOM, 10, 5, 10));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 1, 2, 6));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 1));
		mobspawninfo$builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, CSurfaceBuilder.ELYCEN_CALTAS_GRAVEL_CONFIG));
		DefaultBiomeFeatures.withCavesAndCanyons(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);
		biomegenerationsettings$builder.withStructure(StructureFeatures.RUINED_PORTAL);
		DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withOverworldOres(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withNormalGrassPatch(biomegenerationsettings$builder);
		if (!isPlains) biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CFeatures.TREES_MAGICAL);
		DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
		//DE DefaultBiomeFeatures.addSparseGrass(this);

		DefaultBiomeFeatures.withStrongholdAndMineshaft(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withForestRocks(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withLargeFern(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withGiantTaigaGrassVegetation(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
		//		DefaultBiomeFeatures.withSparseBerries(biomegenerationsettings$builder);


		return (new Biome.Builder())/**/.depth(depth).downfall(50F).precipitation(RainType.SNOW).scale(0.10F).temperature(0.001F).category(isPlains?Category.PLAINS:Category.FOREST)/**/.setEffects(getMagicAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeAcrotlestForestBiome() {
		MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.BIOG, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.LICHU, 50, 6, 6));

		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG));
		
		CombatBiomeFeatures.withAcrotlestCavesAndCanyons(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestStructures(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestPortal(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withEtherionTower(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withAcrotlestLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestOres(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CFeatures.TREES_MONORIS);
		DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withNormalGrassPatch(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeatures.TSUNE_SPIKE);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);


		return (new Biome.Builder()).depth(0.1F).downfall(0.8F).precipitation(Biome.RainType.SNOW).scale(0.2F).temperature(0.1F).category(Biome.Category.FOREST).setEffects(getAcrotlestAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeAcrotlestMountainsBiome() {
		MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.BIOG, 100, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.LICHU, 50, 6, 6));
		
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, CSurfaceBuilder.PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG));
		CombatBiomeFeatures.withAcrotlestStructures(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestPortal(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withEtherionTower(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withAcrotlestCavesAndCanyons(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestOres(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeatures.TSUNE_SPIKE);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
		DefaultBiomeFeatures.withNormalGrassPatch(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);

		return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(0.45F).scale(0.3F).temperature(0.0F).downfall(0.8F).setEffects(getAcrotlestAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	}
	
	public static Biome makeHisovSandsBiome() {
		MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4));
		mobspawninfo$builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.RED_BIOG, 25, 1, 1));
		mobspawninfo$builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(CEntityType.LICHU, 50, 6, 6));
		
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, CSurfaceBuilder.HOMSE_HOMSE_GRAVEL_CONFIG));
		CombatBiomeFeatures.withAcrotlestCavesAndCanyons(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestStructures(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestPortal(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withEtherionTower(biomegenerationsettings$builder, true);
		CombatBiomeFeatures.withAcrotlestLakes(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
//		DefaultBiomeFeatures.addDoubleFlowers(this);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
		CombatBiomeFeatures.withAcrotlestOres(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeatures.TSUNE_SPIKE);
		biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CFeatures.MAGENTA_TSUNE_COLUMN);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
		DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);

		return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.DESERT).depth(0.225F).scale(0.05F).temperature(1.0F).downfall(0.1F).setEffects(getAcrotlestAmbience()).withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	}

	public static BiomeAmbience getDeadAmbience() {		//R+0,G+1,B+3			<-----				//R+0,G+2,B+3				<----				//R+0,G+3,B+1				<------
		return new BiomeAmbience.Builder().setWaterFogColor(0x000103).setWaterColor(0x000103).setFogColor(0x000203).withSkyColor(0x000203).withGrassColor(0x283132).withFoliageColor(0x283132).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
	}

	public static BiomeAmbience getMagicAmbience() {		//R-2,G+1,B+0			<-----				//R-2,G+1,B+1				<----				//R-2,G-2,B+1				<------
		return new BiomeAmbience.Builder().setWaterFogColor(0xf903ff).setWaterColor(0xf903ff).setFogColor(0x3633D0).withSkyColor(0x3633D0).withGrassColor(0x3629D0).withFoliageColor(0x3629D0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
	}

	public static BiomeAmbience getAcrotlestAmbience() {
		return new BiomeAmbience.Builder().setWaterFogColor(329011).setWaterColor(4159204).setFogColor(12638463).withSkyColor(11487769).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build();
	}
}
