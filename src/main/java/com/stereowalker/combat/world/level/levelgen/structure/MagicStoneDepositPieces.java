package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.levelgen.feature.StructurePieceTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class MagicStoneDepositPieces {
	private static final ResourceLocation PORTAL = Combat.getInstance().location("magic_stone_deposit");

	public static void addPieces(StructureManager templateManager, BlockPos blockPos, Rotation rotation, StructurePieceAccessor pPieces, Random random) {
		pPieces.addPiece(new MagicStoneDepositPieces.Piece(templateManager, PORTAL, blockPos, rotation, 0));
	}

	public static class Piece extends CustomTemplateStructurePiece {

		public Piece(StructureManager pStructureManager, ResourceLocation pLocation, BlockPos pPos, Rotation pRotation, int pDown) {
			super(StructurePieceTypes.AGIC_STONE_DEPOT, 0, pStructureManager, pLocation, pLocation.toString(), makeSettings(pRotation, BlockPos.ZERO), makePosition(BlockPos.ZERO, pPos, pDown, Direction.DOWN));
		}

		public Piece(StructureManager pStructureManager, CompoundTag pTag) {
			super(StructurePieceTypes.AGIC_STONE_DEPOT, pTag, pStructureManager, (p_162451_) -> {
				return makeSettings(Rotation.valueOf(pTag.getString("Rot")), BlockPos.ZERO);
			});
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
			super.addAdditionalSaveData(pContext, pTag);
			pTag.putString("Rot", this.placeSettings.getRotation().name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {
			
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
		 * the end, it adds Fences...
		 */
		@Override
		public void postProcess(WorldGenLevel seedReader, StructureFeatureManager mamager, ChunkGenerator chunkGenerator, Random randomIn, BoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos pos) {
			this.templatePosition = this.templatePosition.offset(0, randomIn.nextInt(20) - 10, 0);
			super.postProcess(seedReader, mamager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPosIn, pos);
		}
	}
}