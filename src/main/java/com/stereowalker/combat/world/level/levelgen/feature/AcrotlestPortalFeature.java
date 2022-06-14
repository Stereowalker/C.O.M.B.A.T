package com.stereowalker.combat.world.level.levelgen.feature;

import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestPortalPieces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class AcrotlestPortalFeature extends ProbabilityFeature {
	public AcrotlestPortalFeature() {
		super(PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), AcrotlestPortalFeature::generatePieces));
	}
	@Override
	public int getSize() {
		return 4;
	}

//	@Override
//	public int getSeedModifier() {
//		return 65165156;
//	}
//
//	@Override
//	public int getBiomeFeatureSeparation() {
//		return 100;
//	}
//	
//	@Override
//	public int getBiomeFeatureDistance() {
//		return 10;
//	}

	private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<ProbabilityStructureConfiguration> p_197090_) {
		BlockPos blockpos = new BlockPos(p_197090_.chunkPos().getMinBlockX(), 90, p_197090_.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(p_197090_.random());
		if (p_197090_.config().dimensionVariant == ProbabilityStructureConfiguration.DimensionVariant.ACROLEST) {
			AcrotlestPortalPieces.addOverworldPieces(p_197090_.structureManager(), blockpos, rotation, p_197089_, p_197090_.random());
		} else if (p_197090_.config().dimensionVariant == ProbabilityStructureConfiguration.DimensionVariant.OVERWORLD) {
			AcrotlestPortalPieces.addAcrotlestPieces(p_197090_.structureManager(), blockpos, rotation, p_197089_, p_197090_.random());
		}
	}
}