package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class MagicStoneDepositStructure extends ProbabilityStructure {
	public static final Codec<MagicStoneDepositStructure> CODEC = simpleCodec(MagicStoneDepositStructure::new);

	   public MagicStoneDepositStructure(Structure.StructureSettings p_227593_) {
	      super(p_227593_);
	   }

	   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext) {
	      return onTopOfChunkCenter(pContext, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
	         this.generatePieces(p_227598_, pContext);
	      });
	   }

	   private void generatePieces(StructurePiecesBuilder pBuilder, Structure.GenerationContext pContext) {
	      ChunkPos chunkpos = pContext.chunkPos();
	      WorldgenRandom worldgenrandom = pContext.random();
	      BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), 40, chunkpos.getMinBlockZ());
	      Rotation rotation = Rotation.getRandom(worldgenrandom);
	      MagicStoneDepositPieces.addPieces(pContext.structureTemplateManager(), blockpos, rotation, pBuilder, worldgenrandom);
	   }

	   public StructureType<?> type() {
	      return CStructureType.MAGIC_STONE_DEPOSIT;
	   }

	@Override
	public int getSize() {
		return 4;
	}
}