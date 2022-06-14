package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestMineShaftPieces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;


public class AcrotlestMineshaftFeature extends CStructureFeature<AcrotlestMineshaftConfiguration> {
	public AcrotlestMineshaftFeature(Codec<AcrotlestMineshaftConfiguration> p_66273_) {
		super(p_66273_, PieceGeneratorSupplier.simple(AcrotlestMineshaftFeature::checkLocation, AcrotlestMineshaftFeature::generatePieces));
	}

	private static boolean checkLocation(PieceGeneratorSupplier.Context<AcrotlestMineshaftConfiguration> p_197122_) {
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
		worldgenrandom.setLargeFeatureSeed(p_197122_.seed(), p_197122_.chunkPos().x, p_197122_.chunkPos().z);
		double d0 = (double)(p_197122_.config()).probability;
		return worldgenrandom.nextDouble() >= d0 ? false : p_197122_.validBiome().test(p_197122_.chunkGenerator().getNoiseBiome(QuartPos.fromBlock(p_197122_.chunkPos().getMiddleBlockX()), QuartPos.fromBlock(50), QuartPos.fromBlock(p_197122_.chunkPos().getMiddleBlockZ())));
	}

	private static void generatePieces(StructurePiecesBuilder p_197124_, PieceGenerator.Context<AcrotlestMineshaftConfiguration> p_197125_) {
		AcrotlestMineShaftPieces.AcrotlestMineShaftRoom mineshaftpieces$mineshaftroom = new AcrotlestMineShaftPieces.AcrotlestMineShaftRoom(0, p_197125_.random(), p_197125_.chunkPos().getBlockX(2), p_197125_.chunkPos().getBlockZ(2), (p_197125_.config()).type);
		p_197124_.addPiece(mineshaftpieces$mineshaftroom);
		mineshaftpieces$mineshaftroom.addChildren(mineshaftpieces$mineshaftroom, p_197124_, p_197125_.random());
		int i = p_197125_.chunkGenerator().getSeaLevel();
		if ((p_197125_.config()).type == AcrotlestMineshaftFeature.Type.MESA) {
			BlockPos blockpos = p_197124_.getBoundingBox().getCenter();
			int j = p_197125_.chunkGenerator().getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, p_197125_.heightAccessor());
			int k = j <= i ? i : Mth.randomBetweenInclusive(p_197125_.random(), i, j);
			int l = k - blockpos.getY();
			p_197124_.offsetPiecesVertically(l);
		} else {
			p_197124_.moveBelowSeaLevel(i, p_197125_.chunkGenerator().getMinY(), p_197125_.random(), 10);
		}

	}

	public static enum Type implements StringRepresentable {
		NORMAL("normal", CBlocks.MONORIS_LOG, CBlocks.MONORIS_PLANKS, CBlocks.MONORIS_FENCE),
		MESA("mesa", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE);

		public static final Codec<AcrotlestMineshaftFeature.Type> CODEC = StringRepresentable.fromEnum(AcrotlestMineshaftFeature.Type::values, AcrotlestMineshaftFeature.Type::byName);
		private static final Map<String, AcrotlestMineshaftFeature.Type> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(AcrotlestMineshaftFeature.Type::getName, (p_66333_) -> {
			return p_66333_;
		}));
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

		private static AcrotlestMineshaftFeature.Type byName(String p_66335_) {
			return BY_NAME.get(p_66335_);
		}

		public static AcrotlestMineshaftFeature.Type byId(int pId) {
			return pId >= 0 && pId < values().length ? values()[pId] : NORMAL;
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