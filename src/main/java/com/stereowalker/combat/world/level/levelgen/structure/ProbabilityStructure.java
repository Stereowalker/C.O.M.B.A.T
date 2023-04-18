package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.function.IntFunction;

import javax.annotation.Nonnull;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class ProbabilityStructure extends CStructureFeature {
	public ProbabilityStructure(Structure.StructureSettings p_227593_) {
	      super(p_227593_);
	   }

	protected boolean isSurfaceFlat(@Nonnull ChunkGenerator generator, int chunkX, int chunkZ, LevelHeightAccessor pLevel) {
		int offset = getSize() * 16;
		
		int xStart = (chunkX << 4);
		int zStart = (chunkZ << 4);
		int i1 = generator.getFirstFreeHeight(xStart, zStart, Heightmap.Types.WORLD_SURFACE_WG, pLevel, null);
		int j1 = generator.getFirstFreeHeight(xStart, zStart + offset, Heightmap.Types.WORLD_SURFACE_WG, pLevel, null);
		int k1 = generator.getFirstFreeHeight(xStart + offset, zStart, Heightmap.Types.WORLD_SURFACE_WG, pLevel, null);
		int l1 = generator.getFirstFreeHeight(xStart + offset, zStart + offset, Heightmap.Types.WORLD_SURFACE_WG, pLevel, null);
		
		int minHeight = Math.min(Math.min(i1, j1), Math.min(k1, l1));
		int maxHeight = Math.max(Math.max(i1, j1), Math.max(k1, l1));
		
		return Math.abs(maxHeight - minHeight) <= 16/*StructureGenConfig.FLATNESS_DELTA.get()*/;
	}
	
	public abstract int getSize();
	

	
	public static enum DimensionVariant implements StringRepresentable {
		OVERWORLD("overworld"),
		ACROLEST("acrotlest");

		public static final Codec<ProbabilityStructure.DimensionVariant> CODEC = StringRepresentable.fromEnum(ProbabilityStructure.DimensionVariant::values);
		private static final IntFunction<ProbabilityStructure.DimensionVariant> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		private final String name;

		private DimensionVariant(String nameIn) {
			this.name = nameIn;
		}

		public String getName() {
			return this.name;
		}

		public static ProbabilityStructure.DimensionVariant byId(int pId) {
			return BY_ID.apply(pId);
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}