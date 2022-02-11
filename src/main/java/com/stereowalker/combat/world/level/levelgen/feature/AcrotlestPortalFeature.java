package com.stereowalker.combat.world.level.levelgen.feature;

import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestPortalPieces;

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

public class AcrotlestPortalFeature extends ProbabilityFeature {
	public AcrotlestPortalFeature() {
		super();
	}

	//   public String getStructureName() {
	//      return Combat.MOD_ID+":acrotlest_portal";
	//   }
	//
	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public CStructureFeature.StructureStartFactory<ProbabilityStructureConfiguration> getStartFactory() {
		return AcrotlestPortalFeature.Start::new;
	}

	@Override
	public int getSeedModifier() {
		return 65165156;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 100;
	}
	
	@Override
	public int getBiomeFeatureDistance() {
		return 10;
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
			if (pConfig.dimensionVariant == ProbabilityStructureConfiguration.DimensionVariant.ACROLEST) {
				AcrotlestPortalPieces.addOverworldPieces(pStructureManager, blockpos, rotation, this, this.random);
			} else if (pConfig.dimensionVariant == ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD) {
				AcrotlestPortalPieces.addAcrotlestPieces(pStructureManager, blockpos, rotation, this, this.random);
			}
		}
	}

}