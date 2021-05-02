package com.stereowalker.combat.world.gen.feature.structure;

import javax.annotation.Nonnull;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public abstract class ProbabilityStructure extends CStructure<ProbabilityStructureConfig> {
	public ProbabilityStructure() {
		super(ProbabilityStructureConfig.CODEC);
	}

//	@Override
//	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
//		((SharedSeedRandom)randIn).setLargeFeatureSeed(generatorIn.getSeed(), chunkX, chunkZ);
//		if (generatorIn.hasStructure(biomeIn, this)) {
//			StructureProbabilityConfig StructureProbabilityConfig = (StructureProbabilityConfig)generatorIn.getStructureConfig(biomeIn, this);
//			double d0 = StructureProbabilityConfig.probability;
//			return chunkX == chunkpos.x && chunkZ == chunkpos.z && generatorIn.hasStructure(biomeIn, this) && randIn.nextDouble() < d0;
//		} else {
//			return false;
//		}
//	}
	

	@Override
	protected boolean /* canBeGenerated */func_230363_a_(ChunkGenerator generatorIn, BiomeProvider p_230363_2_, long seed, SharedSeedRandom sharedSeed, int chunkX, int chunkZ, Biome biomeIn, ChunkPos chunkPos, ProbabilityStructureConfig structureProbabilityConfig) {
		for(Biome biome : p_230363_2_.getBiomes(chunkX * 16 + 9, generatorIn.getSeaLevel(), chunkZ * 16 + 9, 32)) {
			if (!biome.getGenerationSettings().hasStructure(this)) {
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
		sharedSeed.setLargeFeatureSeed(seed, chunkX, chunkZ);
		double d0 = structureProbabilityConfig.probability;
		return  sharedSeed.nextInt(MathHelper.floor(1/d0)) == 0;
	}
	
	protected boolean isSurfaceFlat(@Nonnull ChunkGenerator generator, int chunkX, int chunkZ) {
		int offset = getSize() * 16;
		
		int xStart = (chunkX << 4);
		int zStart = (chunkZ << 4);
		int i1 = generator.getNoiseHeight(xStart, zStart, Type.WORLD_SURFACE_WG);
		int j1 = generator.getNoiseHeight(xStart, zStart + offset, Type.WORLD_SURFACE_WG);
		int k1 = generator.getNoiseHeight(xStart + offset, zStart, Type.WORLD_SURFACE_WG);
		int l1 = generator.getNoiseHeight(xStart + offset, zStart + offset, Type.WORLD_SURFACE_WG);
		
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
	
	public StructureSeparationSettings getStructureSeperationSettings() {
		return new StructureSeparationSettings(getBiomeFeatureDistance(), getBiomeFeatureSeparation(), getSeedModifier());
	}
	
	@Override
	public BlockPos func_236388_a_(IWorldReader world, StructureManager structureManager, BlockPos startPos, int searchRadius, boolean skipExistingChunks, long seed, StructureSeparationSettings settings) {
		return super.func_236388_a_(world, structureManager, startPos, searchRadius, skipExistingChunks, seed, getStructureSeperationSettings());
	}
	
	@Override
	public  ChunkPos getChunkPosForStructure(StructureSeparationSettings settings, long seed, SharedSeedRandom sharedSeedRandom, int chunkX, int chunkZ) {
		int spacing = this.getBiomeFeatureDistance();
		int gridX = ((chunkX / spacing) * spacing);
		int gridZ = ((chunkZ / spacing) * spacing);
		
		int offset = this.getBiomeFeatureSeparation() + 1;
		sharedSeedRandom.setLargeFeatureSeedWithSalt(seed, gridX, gridZ, this.getSeedModifier());
		int offsetX = sharedSeedRandom.nextInt(offset);
		int offsetZ = sharedSeedRandom.nextInt(offset);
		
		int gridOffsetX = gridX + offsetX;
		int gridOffsetZ = gridZ + offsetZ;
		
		return new ChunkPos(gridOffsetX, gridOffsetZ);
	}
}