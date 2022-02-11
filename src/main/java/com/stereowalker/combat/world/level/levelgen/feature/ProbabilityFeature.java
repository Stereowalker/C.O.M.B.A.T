package com.stereowalker.combat.world.level.levelgen.feature;

import javax.annotation.Nonnull;

import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public abstract class ProbabilityFeature extends CStructureFeature<ProbabilityStructureConfiguration> {
	public ProbabilityFeature() {
		super(ProbabilityStructureConfiguration.CODEC);
	}

//	@Override
//	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
//		((WorldgenRandom)randIn).setLargeFeatureSeed(generatorIn.getSeed(), chunkX, chunkZ);
//		if (generatorIn.hasStructure(biomeIn, this)) {
//			StructureProbabilityConfig StructureProbabilityConfig = (StructureProbabilityConfig)generatorIn.getStructureConfig(biomeIn, this);
//			double d0 = StructureProbabilityConfig.probability;
//			return chunkX == chunkpos.x && chunkZ == chunkpos.z && generatorIn.hasStructure(biomeIn, this) && randIn.nextDouble() < d0;
//		} else {
//			return false;
//		}
//	}
	
	@Override
	protected boolean isFeatureChunk(ChunkGenerator pGenerator, BiomeSource pBiomeSource, long pSeed,
			WorldgenRandom pRandom, ChunkPos pChunkPos, Biome pBiome, ChunkPos pPotentialPos,
			ProbabilityStructureConfiguration pConfig, LevelHeightAccessor pLevel) {
		for(Biome biome : pBiomeSource.getBiomesWithin(pChunkPos.x * 16 + 9, pGenerator.getSeaLevel(), pChunkPos.z * 16 + 9, 32)) {
			if (!biome.getGenerationSettings().isValidStart(this)) {
				return false;
			}
		}
//		int radii = 0;
//		for(int k = chunkX - radii; k <= chunkX + radii; ++k) {
//            for(int l = chunkZ - radii; l <= chunkZ + radii; ++l) {
//               ChunkPos chunkpos = this.func_236392_a_(generatorIn.func_235957_b_().func_236197_a_(this), seed, sharedSeed, k, l);
//               if (k == chunkpos.x && l == chunkpos.z) {
//                  return false;
//               }
//            }
//         }
//		return true;
		pRandom.setLargeFeatureSeed(pSeed, pChunkPos.x, pChunkPos.z);
		double d0 = pConfig.probability;
		return  pRandom.nextInt(Mth.floor(1/d0)) == 0;
	}
	
	protected boolean isSurfaceFlat(@Nonnull ChunkGenerator generator, int chunkX, int chunkZ, LevelHeightAccessor pLevel) {
		int offset = getSize() * 16;
		
		int xStart = (chunkX << 4);
		int zStart = (chunkZ << 4);
		int i1 = generator.getFirstFreeHeight(xStart, zStart, Heightmap.Types.WORLD_SURFACE_WG, pLevel);
		int j1 = generator.getFirstFreeHeight(xStart, zStart + offset, Heightmap.Types.WORLD_SURFACE_WG, pLevel);
		int k1 = generator.getFirstFreeHeight(xStart + offset, zStart, Heightmap.Types.WORLD_SURFACE_WG, pLevel);
		int l1 = generator.getFirstFreeHeight(xStart + offset, zStart + offset, Heightmap.Types.WORLD_SURFACE_WG, pLevel);
		
		int minHeight = Math.min(Math.min(i1, j1), Math.min(k1, l1));
		int maxHeight = Math.max(Math.max(i1, j1), Math.max(k1, l1));
		
		return Math.abs(maxHeight - minHeight) <= 16/*StructureGenConfig.FLATNESS_DELTA.get()*/;
	}
	
	public abstract int getSize();
	
	
	/**
	 * The amount of space between structures of the same type
	 * @return
	 */
	public abstract int getBiomeFeatureSeparation();
	/**
	 * The amount of distance between this structure and another one
	 * @return
	 */
	public abstract int getBiomeFeatureDistance();
	public abstract int getSeedModifier();
//	public abstract int getSpawnChance();
	
	public StructureFeatureConfiguration getStructureSeperationSettings() {
		return new StructureFeatureConfiguration(getBiomeFeatureDistance(), getBiomeFeatureSeparation(), getSeedModifier());
	}
	
	@Override
	public BlockPos getNearestGeneratedFeature(LevelReader world, StructureFeatureManager structureManager, BlockPos startPos, int searchRadius, boolean skipExistingChunks, long seed, StructureFeatureConfiguration settings) {
		return super.getNearestGeneratedFeature(world, structureManager, startPos, searchRadius, skipExistingChunks, seed, getStructureSeperationSettings());
	}
	
	@Override
	public  ChunkPos getPotentialFeatureChunk(StructureFeatureConfiguration settings, long seed, WorldgenRandom sharedSeedRandom, int chunkX, int chunkZ) {
		int spacing = this.getBiomeFeatureDistance();
		int gridX = ((chunkX / spacing) * spacing);
		int gridZ = ((chunkZ / spacing) * spacing);
		
		int offset = this.getBiomeFeatureSeparation() + 1;
		sharedSeedRandom.setLargeFeatureWithSalt(seed, gridX, gridZ, this.getSeedModifier());
		int offsetX = sharedSeedRandom.nextInt(offset);
		int offsetZ = sharedSeedRandom.nextInt(offset);
		
		int gridOffsetX = gridX + offsetX;
		int gridOffsetZ = gridZ + offsetZ;
		
		return new ChunkPos(gridOffsetX, gridOffsetZ);
	}
}