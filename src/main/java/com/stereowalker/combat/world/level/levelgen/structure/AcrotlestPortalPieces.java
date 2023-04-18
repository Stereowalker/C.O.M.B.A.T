package com.stereowalker.combat.world.level.levelgen.structure;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.levelgen.feature.StructurePieceTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class AcrotlestPortalPieces {
	private static final ResourceLocation ACROTLEST_PORTAL = Combat.getInstance().location("portals/acrotlest_portal");
	private static final ResourceLocation OVERWORLD_PORTAL = Combat.getInstance().location("portals/overworld_portal");

	public static void addOverworldPieces(StructureTemplateManager templateManager, BlockPos blockPos, Rotation rotation, StructurePieceAccessor pPieces, RandomSource random) {
		pPieces.addPiece(new AcrotlestPortalPieces.Piece(templateManager, OVERWORLD_PORTAL, blockPos, rotation, 0));
	}
	
	public static void addAcrotlestPieces(StructureTemplateManager templateManager, BlockPos blockPos, Rotation rotation, StructurePieceAccessor pPieces, RandomSource random) {
		pPieces.addPiece(new AcrotlestPortalPieces.Piece(templateManager, ACROTLEST_PORTAL, blockPos, rotation, 0));
	}

	public static class Piece extends CustomTemplateStructurePiece {

		public Piece(StructureTemplateManager pStructureManager, ResourceLocation pLocation, BlockPos pPos, Rotation pRotation, int pDown) {
			super(StructurePieceTypes.ACROTLEST_PORTAL, 0, pStructureManager, pLocation, pLocation.toString(), makeSettings(pRotation, BlockPos.ZERO), makePosition(BlockPos.ZERO, pPos, pDown, Direction.DOWN));
		}

		public Piece(StructureTemplateManager pStructureManager, CompoundTag pTag) {
			super(StructurePieceTypes.ACROTLEST_PORTAL, pTag, pStructureManager, (p_162451_) -> {
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
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
			
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
		 * the end, it adds Fences...
		 */
		@Override
		public void postProcess(WorldGenLevel seedReader, StructureManager mamager, ChunkGenerator chunkGenerator, RandomSource randomIn, BoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos pos) {
			StructurePlaceSettings placementsettings = makeSettings(this.placeSettings.getRotation(), BlockPos.ZERO);
			BlockPos blockpos = BlockPos.ZERO;
			BlockPos blockpos1 = this.templatePosition.offset(StructureTemplate.calculateRelativePosition(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
			int i = seedReader.getHeight(Heightmap.Types.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.offset(0, i - 90 - 1, 0);
			super.postProcess(seedReader, mamager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPosIn, pos);
			this.templatePosition = blockpos2;
		}
	}
}