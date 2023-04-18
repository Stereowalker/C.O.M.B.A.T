package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Optional;
import java.util.function.IntFunction;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestMineShaftPieces;
import com.stereowalker.combat.world.level.levelgen.structure.CStructureType;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;

public class AcrotlestMineshaftStructure extends CStructureFeature {
	public static final Codec<AcrotlestMineshaftStructure> CODEC = RecordCodecBuilder.create((p_227971_) -> {
		return p_227971_.group(settingsCodec(p_227971_), AcrotlestMineshaftStructure.Type.CODEC.fieldOf("mineshaft_type").forGetter((p_227969_) -> {
			return p_227969_.type;
		})).apply(p_227971_, AcrotlestMineshaftStructure::new);
	});
	private final AcrotlestMineshaftStructure.Type type;

	public AcrotlestMineshaftStructure(Structure.StructureSettings p_227961_, AcrotlestMineshaftStructure.Type p_227962_) {
		super(p_227961_);
		this.type = p_227962_;
	}

	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext) {
		pContext.random().nextDouble();
		ChunkPos chunkpos = pContext.chunkPos();
		BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), 50, chunkpos.getMinBlockZ());
		StructurePiecesBuilder structurepiecesbuilder = new StructurePiecesBuilder();
		int i = this.generatePiecesAndAdjust(structurepiecesbuilder, pContext);
		return Optional.of(new Structure.GenerationStub(blockpos.offset(0, i, 0), Either.right(structurepiecesbuilder)));
	}

	private int generatePiecesAndAdjust(StructurePiecesBuilder pBuilder, Structure.GenerationContext pContext) {
		ChunkPos chunkpos = pContext.chunkPos();
		WorldgenRandom worldgenrandom = pContext.random();
		ChunkGenerator chunkgenerator = pContext.chunkGenerator();
		AcrotlestMineShaftPieces.AcrotlestMineShaftRoom mineshaftpieces$mineshaftroom = new AcrotlestMineShaftPieces.AcrotlestMineShaftRoom(0, worldgenrandom, chunkpos.getBlockX(2), chunkpos.getBlockZ(2), this.type);
		pBuilder.addPiece(mineshaftpieces$mineshaftroom);
		mineshaftpieces$mineshaftroom.addChildren(mineshaftpieces$mineshaftroom, pBuilder, worldgenrandom);
		int i = chunkgenerator.getSeaLevel();
		if (this.type == AcrotlestMineshaftStructure.Type.MESA) {
			BlockPos blockpos = pBuilder.getBoundingBox().getCenter();
			int j = chunkgenerator.getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, pContext.heightAccessor(), pContext.randomState());
			int k = j <= i ? i : Mth.randomBetweenInclusive(worldgenrandom, i, j);
			int l = k - blockpos.getY();
			pBuilder.offsetPiecesVertically(l);
			return l;
		} else {
			return pBuilder.moveBelowSeaLevel(i, chunkgenerator.getMinY(), worldgenrandom, 10);
		}
	}

	public StructureType<?> type() {
		return CStructureType.MINESHAFT;
	}

	public static enum Type implements StringRepresentable {
		NORMAL("normal", CBlocks.MONORIS_LOG, CBlocks.MONORIS_PLANKS, CBlocks.MONORIS_FENCE),
		MESA("mesa", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE);

		public static final Codec<AcrotlestMineshaftStructure.Type> CODEC = StringRepresentable.fromEnum(AcrotlestMineshaftStructure.Type::values);
		private static final IntFunction<AcrotlestMineshaftStructure.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		private final String name;
		private final BlockState woodState;
		private final BlockState planksState;
		private final BlockState fenceState;

		private Type(String p_160057_, Block p_160058_, Block p_160059_, Block p_160060_) {
			this.name = p_160057_;
			this.woodState = p_160058_.defaultBlockState();
			this.planksState = p_160059_.defaultBlockState();
			this.fenceState = p_160060_.defaultBlockState();
		}

		public String getName() {
			return this.name;
		}

		public static AcrotlestMineshaftStructure.Type byId(int pId) {
			return BY_ID.apply(pId);
		}

		public BlockState getWoodState() {
			return this.woodState;
		}

		public BlockState getPlanksState() {
			return this.planksState;
		}

		public BlockState getFenceState() {
			return this.fenceState;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}