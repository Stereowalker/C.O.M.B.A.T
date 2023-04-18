package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Optional;
import java.util.function.IntFunction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class EtherionTowerStructure extends CStructureFeature {
	public static final Codec<EtherionTowerStructure> CODEC = RecordCodecBuilder.create((p_227971_) -> {
		return p_227971_.group(settingsCodec(p_227971_), Variant.CODEC.fieldOf("variant").forGetter((p_227969_) -> {
			return p_227969_.variant;
		})).apply(p_227971_, EtherionTowerStructure::new);
	});

	private final Variant variant;
	public EtherionTowerStructure(Structure.StructureSettings p_227593_, Variant variant) {
		super(p_227593_);
		this.variant = variant;
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
		EtherionTowerPieces.addPieces(pContext.structureTemplateManager(), blockpos, rotation, pBuilder, worldgenrandom, variant);
	}

	public StructureType<?> type() {
		return CStructureType.ETHERION_TOWER;
	}

	public static enum Variant implements StringRepresentable {
		BADLANDS("badlands"),
		OVERWORLD("overworld"),
		DESERT("desert"),
		ACROLEST("acrotlest");


		public static final Codec<EtherionTowerStructure.Variant> CODEC = StringRepresentable.fromEnum(EtherionTowerStructure.Variant::values);
		private static final IntFunction<EtherionTowerStructure.Variant> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		private final String name;

		private Variant(String nameIn) {
			this.name = nameIn;
		}

		public String getName() {
			return this.name;
		}

		public static EtherionTowerStructure.Variant byId(int pId) {
			return BY_ID.apply(pId);
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}