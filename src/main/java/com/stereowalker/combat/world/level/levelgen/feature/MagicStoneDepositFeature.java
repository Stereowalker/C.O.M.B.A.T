package com.stereowalker.combat.world.level.levelgen.feature;

import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.MagicStoneDepositPieces;

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

public class MagicStoneDepositFeature extends ProbabilityFeature {
	public MagicStoneDepositFeature() {
		super();
	}

//	public String getStructureName() {
//		return Combat.MOD_ID+":magic_stone_deposit";
//	}
//
	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public CStructureFeature.StructureStartFactory<ProbabilityStructureConfiguration> getStartFactory() {
		return MagicStoneDepositFeature.Start::new;
	}

	@Override
	public int getBiomeFeatureDistance() {
		return 10;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 4;
	}

	public int getSeedModifier() {
		return 71509846;
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
			MagicStoneDepositPieces.addPieces(pStructureManager, blockpos, rotation, this, this.random);
		}
	}
}