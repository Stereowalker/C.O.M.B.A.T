package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestMineShaftPieces;

import net.minecraft.core.RegistryAccess;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;


public class AcrotlestMineshaftFeature extends CStructureFeature<AcrotlestMineshaftConfiguration> {
	public AcrotlestMineshaftFeature(Codec<AcrotlestMineshaftConfiguration> p_66273_) {
		super(p_66273_);
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator pGenerator, BiomeSource pBiomeSource, long pSeed, WorldgenRandom pRandom, ChunkPos pChunkPos, Biome pBiome, ChunkPos pPotentialPos, AcrotlestMineshaftConfiguration pConfig, LevelHeightAccessor pLevel) {
		pRandom.setLargeFeatureSeed(pSeed, pChunkPos.x, pChunkPos.z);
		double d0 = (double)pConfig.probability;
		return pRandom.nextDouble() < d0;
	}

	@Override
	public CStructureFeature.StructureStartFactory<AcrotlestMineshaftConfiguration> getStartFactory() {
		return AcrotlestMineshaftFeature.MineShaftStart::new;
	}

	public static class MineShaftStart extends StructureStart<AcrotlestMineshaftConfiguration> {
		public MineShaftStart(StructureFeature<AcrotlestMineshaftConfiguration> p_160031_, ChunkPos p_160032_, int p_160033_, long p_160034_) {
			super(p_160031_, p_160032_, p_160033_, p_160034_);
		}

		@Override
		public void generatePieces(RegistryAccess p_160044_, ChunkGenerator p_160045_, StructureManager p_160046_, ChunkPos p_160047_, Biome p_160048_, AcrotlestMineshaftConfiguration p_160049_, LevelHeightAccessor p_160050_) {
			AcrotlestMineShaftPieces.AcrotlestMineShaftRoom mineshaftpieces$mineshaftroom = new AcrotlestMineShaftPieces.AcrotlestMineShaftRoom(0, this.random, p_160047_.getBlockX(2), p_160047_.getBlockZ(2), p_160049_.type);
			this.addPiece(mineshaftpieces$mineshaftroom);
			mineshaftpieces$mineshaftroom.addChildren(mineshaftpieces$mineshaftroom, this, this.random);
			if (p_160049_.type == AcrotlestMineshaftFeature.Type.MESA) {
				int i = -5;
				BoundingBox boundingbox = this.getBoundingBox();
				int j = p_160045_.getSeaLevel() - boundingbox.maxY() + boundingbox.getYSpan() / 2 - i;
				this.offsetPiecesVertically(j);
			} else {
				this.moveBelowSeaLevel(p_160045_.getSeaLevel(), p_160045_.getMinY(), this.random, 10);
			}

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