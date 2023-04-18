package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class AcrotlestPortalStructure extends ProbabilityStructure {
	public static final Codec<AcrotlestPortalStructure> CODEC = RecordCodecBuilder.create((p_227971_) -> {
		return p_227971_.group(settingsCodec(p_227971_), DimensionVariant.CODEC.fieldOf("variant").forGetter((p_227969_) -> {
			return p_227969_.variant;
		})).apply(p_227971_, AcrotlestPortalStructure::new);
	});

	private final DimensionVariant variant;
	public AcrotlestPortalStructure(Structure.StructureSettings p_227593_, DimensionVariant variant) {
		super(p_227593_);
		this.variant = variant;
	}

	@Override
	public int getSize() {
		return 4;
	}

	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext) {
		return onTopOfChunkCenter(pContext, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
			this.generatePieces(p_227598_, pContext);
		});
	}

	private void generatePieces(StructurePiecesBuilder pBuilder, Structure.GenerationContext pContext) {
		ChunkPos chunkpos = pContext.chunkPos();
		WorldgenRandom worldgenrandom = pContext.random();
		BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), 90, chunkpos.getMinBlockZ());
		Rotation rotation = Rotation.getRandom(worldgenrandom);
		if (this.variant == DimensionVariant.ACROLEST) {
			AcrotlestPortalPieces.addOverworldPieces(pContext.structureTemplateManager(), blockpos, rotation, pBuilder, worldgenrandom);
		} else if (this.variant == DimensionVariant.OVERWORLD) {
			AcrotlestPortalPieces.addAcrotlestPieces(pContext.structureTemplateManager(), blockpos, rotation, pBuilder, worldgenrandom);
		}
	}

	public StructureType<?> type() {
		return CStructureType.ACROSTLEST_PORTAL;
	}
}