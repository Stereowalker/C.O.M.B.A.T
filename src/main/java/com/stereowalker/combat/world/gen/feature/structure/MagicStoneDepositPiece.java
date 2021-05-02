package com.stereowalker.combat.world.gen.feature.structure;

import java.util.List;
import java.util.Random;

import com.stereowalker.combat.Combat;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MagicStoneDepositPiece {
	private static final ResourceLocation PORTAL = Combat.getInstance().location("magic_stone_deposit");

	public static void createStructure(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> structurePieceList, Random random) {
		structurePieceList.add(new MagicStoneDepositPiece.Piece(templateManager, PORTAL, blockPos, rotation, 0));
	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation structure;
		private final Rotation rotaton;

		public Piece(TemplateManager p_i49313_1_, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotationIn, int yOffset) {
			super(StructurePieceTypes.MSDEPOT, 0);
			this.structure = resourceLocation;
			BlockPos blockpos = BlockPos.ZERO;
			this.templatePosition = blockPos.add(blockpos.getX(), blockpos.getY() + yOffset, blockpos.getZ());
			this.rotaton = rotationIn;
			this.func_207614_a(p_i49313_1_);
		}

		public Piece(TemplateManager templateManager, CompoundNBT tagCompound) {
			super(StructurePieceTypes.MSDEPOT, tagCompound);
			this.structure = new ResourceLocation(tagCompound.getString("Template"));
			this.rotaton = Rotation.valueOf(tagCompound.getString("Rot"));
			this.func_207614_a(templateManager);
		}

		private void func_207614_a(TemplateManager p_207614_1_) {
			Template template = p_207614_1_.getTemplateDefaulted(this.structure);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotaton).setMirror(Mirror.NONE).setCenterOffset(BlockPos.ZERO).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			this.setup(template, this.templatePosition, placementsettings);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.structure.toString());
			tagCompound.putString("Rot", this.rotaton.name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
			
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
		 * the end, it adds Fences...
		 */
		@Override
		public boolean /* create */func_230383_a_(ISeedReader seedReader, StructureManager mamager, ChunkGenerator chunkGenerator, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos pos) {
			this.templatePosition = this.templatePosition.add(0, randomIn.nextInt(20) - 10, 0);
			boolean flag = super./*create*/func_230383_a_(seedReader, mamager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPosIn, pos);
			return flag;
		}
	}
}