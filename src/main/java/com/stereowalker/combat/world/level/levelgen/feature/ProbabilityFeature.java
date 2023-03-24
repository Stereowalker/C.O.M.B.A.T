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
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public abstract class ProbabilityFeature extends CStructureFeature<ProbabilityStructureConfiguration> {
	public ProbabilityFeature(PieceGeneratorSupplier<ProbabilityStructureConfiguration> p_197166_) {
		super(ProbabilityStructureConfiguration.CODEC, p_197166_);
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
}