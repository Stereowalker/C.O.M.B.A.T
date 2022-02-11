package com.stereowalker.combat.world.level.levelgen.feature;

import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.EtherionTowerPieces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class EtherionTowerFeature extends ProbabilityFeature {
	public EtherionTowerFeature() {
		super();
	}

//	public String getStructureName() {
//		return Combat.MOD_ID+":etherion_tower";
//	}

	@Override
	public int getSize() {
		return 20;
	}

	@Override
	public CStructureFeature.StructureStartFactory<ProbabilityStructureConfiguration> getStartFactory() {
		return EtherionTowerFeature.Start::new;
	}

	@Override
	public int getBiomeFeatureDistance() {
		return 200;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 100;
	}

	@Override
	public int getSeedModifier() {
		return 53060063;
	}

	public static class Start extends StructureStart<ProbabilityStructureConfiguration> {
		public Start(StructureFeature<ProbabilityStructureConfiguration> p_160031_, ChunkPos p_160032_, int p_160033_, long p_160034_) {
			super(p_160031_, p_160032_, p_160033_, p_160034_);
		}

		@Override
		public void generatePieces(RegistryAccess pRegistryAccess, ChunkGenerator pChunkGenerator,
				StructureManager pStructureManager, ChunkPos pChunkPos, Biome pBiome,
				ProbabilityStructureConfiguration pConfig, LevelHeightAccessor pLevel) {
			BlockPos blockpos = new BlockPos(pChunkPos.getMinBlockX(), 90, pChunkPos.getMinBlockZ());
			Rotation rotation = Rotation.getRandom(this.random);
			EtherionTowerPieces.addPieces(pStructureManager, blockpos, rotation, this, this.random, pConfig.dimensionVariant.equals(ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD));
			
		}
	}
}