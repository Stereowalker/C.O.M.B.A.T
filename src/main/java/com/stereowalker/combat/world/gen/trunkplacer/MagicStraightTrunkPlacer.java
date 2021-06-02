package com.stereowalker.combat.world.gen.trunkplacer;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class MagicStraightTrunkPlacer extends AbstractTrunkPlacer {
   public static final Codec<MagicStraightTrunkPlacer> CODEC = RecordCodecBuilder.create((builderInstance) -> {
      return getAbstractTrunkCodec(builderInstance).apply(builderInstance, MagicStraightTrunkPlacer::new);
   });

   public MagicStraightTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
      super(baseHeight, heightRandA, heightRandB);
   }

   protected TrunkPlacerType<?> getPlacerType() {
      return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
   }

   public List<FoliagePlacer.Foliage> getFoliages(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
      func_236909_a_(reader, p_230382_4_.down());

      for(int i = 0; i < treeHeight; ++i) {
         func_236911_a_(reader, rand, p_230382_4_.up(i), p_230382_5_, p_230382_6_, p_230382_7_);
      }

      return ImmutableList.of(new FoliagePlacer.Foliage(p_230382_4_.up(treeHeight), 0, false));
   }

   protected static void func_236909_a_(IWorldGenerationReader reader, BlockPos pos) {
      if (!func_236912_a_(reader, pos)) {
         TreeFeature.setBlockStateWithoutUpdate(reader, pos, CBlocks.CALTAS.getDefaultState());
      }
   }
}