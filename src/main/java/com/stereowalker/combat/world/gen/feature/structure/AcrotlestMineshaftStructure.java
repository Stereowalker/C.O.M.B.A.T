package com.stereowalker.combat.world.gen.feature.structure;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class AcrotlestMineshaftStructure extends CStructure<AcrotlestMineshaftConfig> {
	public AcrotlestMineshaftStructure(Codec<AcrotlestMineshaftConfig> p_i51478_1_) {
		super(p_i51478_1_);
	}

	@Override
	protected boolean /* canBeGenerated */func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_, SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_, AcrotlestMineshaftConfig p_230363_10_) {
		p_230363_5_.setLargeFeatureSeed(p_230363_3_, p_230363_6_, p_230363_7_);
		double d0 = p_230363_10_.probability;
		return p_230363_5_.nextDouble() < d0;
	}

	//	@Override
	//	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator<?> p_225558_2_, Random p_225558_3_, int p_225558_4_, int p_225558_5_, Biome p_225558_6_) {
	//		((SharedSeedRandom)p_225558_3_).setLargeFeatureSeed(p_225558_2_.getSeed(), p_225558_4_, p_225558_5_);
	//		if (p_225558_2_.hasStructure(p_225558_6_, this)) {
	//			AcrotlestMineshaftConfig mineshaftconfig = (AcrotlestMineshaftConfig)p_225558_2_.getStructureConfig(p_225558_6_, this);
	//			double d0 = mineshaftconfig.probability;
	//			return p_225558_3_.nextDouble() < d0;
	//		} else {
	//			return false;
	//		}
	//	}
	//
	@Override
	public Structure.IStartFactory<AcrotlestMineshaftConfig> getStartFactory() {
		return AcrotlestMineshaftStructure.Start::new;
	}

	//	public static class Start extends StructureStart {
	//		public Start(Structure<?> structure, int p_i50446_2_, int p_i50446_3_, MutableBoundingBox p_i50446_5_, int p_i50446_6_, long p_i50446_7_) {
	//			super(structure, p_i50446_2_, p_i50446_3_, p_i50446_5_, p_i50446_6_, p_i50446_7_);
	//		}
	//
	//		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
	//			AcrotlestMineshaftConfig mineshaftconfig = (AcrotlestMineshaftConfig)generator.getStructureConfig(biomeIn, CFeature.ACROTLEST_MINESHAFT);
	//			AcrotlestMineshaftPieces.Room mineshaftpieces$room = new AcrotlestMineshaftPieces.Room(0, this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, mineshaftconfig.type);
	//			this.components.add(mineshaftpieces$room);
	//			mineshaftpieces$room.buildComponent(mineshaftpieces$room, this.components, this.rand);
	//			this.recalculateStructureSize();
	//			if (mineshaftconfig.type == AcrotlestMineshaftStructure.Type.MESA) {
	//				int j = generator.getSeaLevel() - this.bounds.maxY + this.bounds.getYSize() / 2 - -5;
	//				this.bounds.offset(0, j, 0);
	//
	//				for(StructurePiece structurepiece : this.components) {
	//					structurepiece.offset(0, j, 0);
	//				}
	//			} else {
	//				this.func_214628_a(generator.getSeaLevel(), this.rand, 10);
	//			}
	//
	//		}
	//	}

	public static class Start extends StructureStart<AcrotlestMineshaftConfig> {
		public Start(Structure<AcrotlestMineshaftConfig> p_i225811_1_, int p_i225811_2_, int p_i225811_3_, MutableBoundingBox p_i225811_4_, int p_i225811_5_, long p_i225811_6_) {
			super(p_i225811_1_, p_i225811_2_, p_i225811_3_, p_i225811_4_, p_i225811_5_, p_i225811_6_);
		}

		public void /* init */func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator chunkGenerator, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, AcrotlestMineshaftConfig p_230364_6_) {
			AcrotlestMineshaftPieces.Room mineshaftpieces$room = new AcrotlestMineshaftPieces.Room(0, this.rand, (p_230364_3_ << 4) + 2, (p_230364_4_ << 4) + 2, p_230364_6_.type);
			this.components.add(mineshaftpieces$room);
			mineshaftpieces$room.buildComponent(mineshaftpieces$room, this.components, this.rand);
			this.recalculateStructureSize();
			if (p_230364_6_.type == AcrotlestMineshaftStructure.Type.MESA) {
//				int i = -5;
				int j = chunkGenerator.getSeaLevel() - this.bounds.maxY + this.bounds.getYSize() / 2 - -5;
				this.bounds.offset(0, j, 0);

				for(StructurePiece structurepiece : this.components) {
					structurepiece.offset(0, j, 0);
				}
			} else {
				this.func_214628_a(chunkGenerator.getSeaLevel(), this.rand, 10);
			}

		}
	}

	public static enum Type implements IStringSerializable {
		NORMAL("normal"),
		MESA("mesa");

		public static final Codec<AcrotlestMineshaftStructure.Type> field_236324_c_ = IStringSerializable.createEnumCodec(AcrotlestMineshaftStructure.Type::values, AcrotlestMineshaftStructure.Type::byName);
		private static final Map<String, AcrotlestMineshaftStructure.Type> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(AcrotlestMineshaftStructure.Type::getName, (p_214716_0_) -> {
			return p_214716_0_;
		}));
		private final String name;

		private Type(String nameIn) {
			this.name = nameIn;
		}

		public String getName() {
			return this.name;
		}

		public static AcrotlestMineshaftStructure.Type byName(String p_214715_0_) {
			return BY_NAME.get(p_214715_0_);
		}

		public static AcrotlestMineshaftStructure.Type byId(int id) {
			return id >= 0 && id < values().length ? values()[id] : NORMAL;
		}

		@Override
		public String getString() {
			return this.name;
		}
	}
}