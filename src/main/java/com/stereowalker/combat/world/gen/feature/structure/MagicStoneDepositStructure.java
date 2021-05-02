package com.stereowalker.combat.world.gen.feature.structure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MagicStoneDepositStructure extends ProbabilityStructure {
	public MagicStoneDepositStructure() {
		super();
	}

//	public String getStructureName() {
//		return Combat.MOD_ID+":magic_stone_deposit";
//	}
//
	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public Structure.IStartFactory<ProbabilityStructureConfig> getStartFactory() {
		return MagicStoneDepositStructure.Start::new;
	}

	@Override
	public int getBiomeFeatureDistance() {
		return 10;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 4;
	}

	public int getSeedModifier() {
		return 71509846;
	}

	public static class Start extends StructureStart<ProbabilityStructureConfig> {
		public Start(Structure<ProbabilityStructureConfig> p_i225806_1_, int p_i225806_2_, int p_i225806_3_, MutableBoundingBox p_i225806_4_, int p_i225806_5_, long p_i225806_6_) {
			super(p_i225806_1_, p_i225806_2_, p_i225806_3_, p_i225806_4_, p_i225806_5_, p_i225806_6_);
		}

		@Override
		public void /* init */func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator chunkGenerator, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, ProbabilityStructureConfig p_230364_6_) {
			int i = p_230364_3_ * 16;
			int j = p_230364_4_ * 16;
			BlockPos blockpos = new BlockPos(i, 30, j);
			Rotation rotation = Rotation.randomRotation(this.rand);
			MagicStoneDepositPiece.createStructure(p_230364_2_, blockpos, rotation, this.components, this.rand);
			this.recalculateStructureSize();
		}
	}
}