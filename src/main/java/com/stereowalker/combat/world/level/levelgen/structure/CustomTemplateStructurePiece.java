package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public abstract class CustomTemplateStructurePiece extends TemplateStructurePiece {
	public CustomTemplateStructurePiece(StructurePieceType pType, int pGenDepth, StructureManager pStructureManager, ResourceLocation pLocation, String pTemplateName, StructurePlaceSettings pPlaceSettings, BlockPos pTemplatePosition) {
		super(pType, pGenDepth, pStructureManager, pLocation, pTemplateName, pPlaceSettings, pTemplatePosition);
	}

	public CustomTemplateStructurePiece(StructurePieceType pType, CompoundTag p_163669_, StructureManager p_163670_, Function<ResourceLocation, StructurePlaceSettings> p_163671_) {
		super(pType, p_163669_, p_163670_, p_163671_);
	}

	protected static StructurePlaceSettings makeSettings(Rotation pRotation, BlockPos pCenter) {
		return (new StructurePlaceSettings()).setRotation(pRotation).setMirror(Mirror.NONE).setRotationPivot(pCenter).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
	}

	protected static BlockPos makePosition(BlockPos pOffset, BlockPos pPos, int distance, Direction direction) {
		return pPos.offset(pOffset).relative(direction, distance);
	}
}
