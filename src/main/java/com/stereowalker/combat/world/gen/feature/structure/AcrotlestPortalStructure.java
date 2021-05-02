package com.stereowalker.combat.world.gen.feature.structure;

import com.stereowalker.combat.world.gen.feature.structure.ProbabilityStructureConfig.DimensionVariant;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class AcrotlestPortalStructure extends ProbabilityStructure {
	public AcrotlestPortalStructure() {
		super();
	}

	//   public String getStructureName() {
	//      return Combat.MOD_ID+":acrotlest_portal";
	//   }
	//
	@Override
	public int getSize() {
		return 4;
	}

	public Structure.IStartFactory<ProbabilityStructureConfig> getStartFactory() {
		return AcrotlestPortalStructure.Start::new;
	}

	@Override
	public int getSeedModifier() {
		return 65165156;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 100;
	}
	
	@Override
	public int getBiomeFeatureDistance() {
		return 10;
	}
	public static class Start extends StructureStart<ProbabilityStructureConfig> {
		public Start(Structure<ProbabilityStructureConfig> p_i225806_1_, int p_i225806_2_, int p_i225806_3_, MutableBoundingBox p_i225806_4_, int p_i225806_5_, long p_i225806_6_) {
			super(p_i225806_1_, p_i225806_2_, p_i225806_3_, p_i225806_4_, p_i225806_5_, p_i225806_6_);
		}

		@Override
		public void /* init */func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator chunkGenerator, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, ProbabilityStructureConfig config) {
			int i = p_230364_3_ * 16;
			int j = p_230364_4_ * 16;
			BlockPos blockpos = new BlockPos(i, 90, j);
			Rotation rotation = Rotation.randomRotation(this.rand);
			if (config.dimensionVariant == DimensionVariant.ACROLEST) {
				AcrotlestPortalPiece.createOverworldStructure(p_230364_2_, blockpos, rotation, this.components, this.rand);
			} else if (config.dimensionVariant == DimensionVariant.OVERWORLD) {
				AcrotlestPortalPiece.createAcrotlestStructure(p_230364_2_, blockpos, rotation, this.components, this.rand);
			}
			this.recalculateStructureSize();
		}
	}

}