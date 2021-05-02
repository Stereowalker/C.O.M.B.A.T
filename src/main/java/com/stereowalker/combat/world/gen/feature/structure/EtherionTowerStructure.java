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

public class EtherionTowerStructure extends ProbabilityStructure {
	public EtherionTowerStructure() {
		super();
	}

//	public String getStructureName() {
//		return Combat.MOD_ID+":etherion_tower";
//	}

	@Override
	public int getSize() {
		return 20;
	}

	@Override
	public Structure.IStartFactory<ProbabilityStructureConfig> getStartFactory() {
		return EtherionTowerStructure.Start::new;
	}

	@Override
	public int getBiomeFeatureDistance() {
		return 200;
	}

	@Override
	public int getBiomeFeatureSeparation() {
		return 100;
	}

	@Override
	public int getSeedModifier() {
		return 53060063;
	}

//	public static class Start extends StructureStart {
//		public Start(Structure<?> p_i50678_1_, int p_i50678_2_, int p_i50678_3_, MutableBoundingBox p_i50678_5_, int p_i50678_6_, long p_i50678_7_) {
//			super(p_i50678_1_, p_i50678_2_, p_i50678_3_, p_i50678_5_, p_i50678_6_, p_i50678_7_);
//		}
//
//		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
//			ProbabilityStructureConfig probabiltyconfig = (ProbabilityStructureConfig)generator.getStructureConfig(biomeIn, CFeature.ETHERION_TOWER);
//			int i = chunkX * 16;
//			int j = chunkZ * 16;
//			BlockPos blockpos = new BlockPos(i, 90, j);
//			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
//			EtherionTowerPiece.func_207617_a(templateManagerIn, blockpos, rotation, this.components, this.rand, probabiltyconfig);
//			this.recalculateStructureSize();
//		}
//	}

	public static class Start extends StructureStart<ProbabilityStructureConfig> {
		public Start(Structure<ProbabilityStructureConfig> p_i225806_1_, int p_i225806_2_, int p_i225806_3_, MutableBoundingBox p_i225806_4_, int p_i225806_5_, long p_i225806_6_) {
			super(p_i225806_1_, p_i225806_2_, p_i225806_3_, p_i225806_4_, p_i225806_5_, p_i225806_6_);
		}

		public void /* init */func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator chunkGenerator, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, ProbabilityStructureConfig config) {
			int i = p_230364_3_ * 16;
			int j = p_230364_4_ * 16;
			BlockPos blockpos = new BlockPos(i, 90, j);
			Rotation rotation = Rotation.randomRotation(this.rand);
			EtherionTowerPiece.createStructure(p_230364_2_, blockpos, rotation, this.components, this.rand, config.dimensionVariant.equals(DimensionVariant.OVERWORLD));
			this.recalculateStructureSize();
		}
	}
}