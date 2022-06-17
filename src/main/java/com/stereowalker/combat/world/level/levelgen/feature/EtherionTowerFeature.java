package com.stereowalker.combat.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.EtherionTowerConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.EtherionTowerPieces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class EtherionTowerFeature extends CStructureFeature<EtherionTowerConfiguration> {
	public EtherionTowerFeature(Codec<EtherionTowerConfiguration> codec) {
		super(codec, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), EtherionTowerFeature::generatePieces));
	}

//	@Override
//	public int getSize() {
//		return 20;
//	}

	private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<EtherionTowerConfiguration> p_197090_) {
		BlockPos blockpos = new BlockPos(p_197090_.chunkPos().getMinBlockX(), 90, p_197090_.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(p_197090_.random());
		EtherionTowerPieces.addPieces(p_197090_.structureManager(), blockpos, rotation, p_197089_, p_197090_.random(), p_197090_.config().variant);
	}
}