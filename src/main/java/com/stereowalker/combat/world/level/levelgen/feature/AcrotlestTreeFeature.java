package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class AcrotlestTreeFeature extends Feature<TreeConfiguration> {
	public AcrotlestTreeFeature(Codec<TreeConfiguration> p_i231999_1_) {
		super(p_i231999_1_);
	}

	private static boolean isVine(LevelSimulatedReader p_236414_0_, BlockPos p_236414_1_) {
		return p_236414_0_.isStateAtPosition(p_236414_1_, (p_236415_0_) -> {
			return p_236415_0_.is(Blocks.VINE);
		});
	}

	private static boolean isWaterAt(LevelSimulatedReader p_236416_0_, BlockPos p_236416_1_) {
		return p_236416_0_.isStateAtPosition(p_236416_1_, (p_236413_0_) -> {
			return p_236413_0_.is(Blocks.WATER);
		});
	}

	public static boolean isAirOrLeavesAt(LevelSimulatedReader p_236412_0_, BlockPos p_236412_1_) {
		return p_236412_0_.isStateAtPosition(p_236412_1_, (p_236411_0_) -> {
			return p_236411_0_.isAir() || p_236411_0_.is(BlockTags.LEAVES);
		});
	}

	private static boolean isDirtOrFarmlandAt(LevelSimulatedReader p_236418_0_, BlockPos p_236418_1_) {
		return p_236418_0_.isStateAtPosition(p_236418_1_, (p_236409_0_) -> {
			Block block = p_236409_0_.getBlock();
			return block == CBlocks.PURIFIED_GRASS_BLOCK || block == CBlocks.PURIFIED_DIRT || block == CBlocks.ACROTLEST_FARMLAND;
		});
	}

	public static boolean isReplaceableAt(LevelSimulatedReader p_236404_0_, BlockPos p_236404_1_) {
		return isAirOrLeavesAt(p_236404_0_, p_236404_1_) || TreeFeature.isReplaceablePlant(p_236404_0_, p_236404_1_) || isWaterAt(p_236404_0_, p_236404_1_);
	}

	private boolean doPlace(WorldGenLevel pLevel, Random pRandom, BlockPos pPos, BiConsumer<BlockPos, BlockState> pTrunkBlockSetter, BiConsumer<BlockPos, BlockState> pFoliageBlockSetter, TreeConfiguration pConfig) {
		int i = pConfig.trunkPlacer.getTreeHeight(pRandom);
		int j = pConfig.foliagePlacer.foliageHeight(pRandom, i, pConfig);
		int k = i - j;
		int l = pConfig.foliagePlacer.foliageRadius(pRandom, k);
		if (pPos.getY() >= pLevel.getMinBuildHeight() + 1 && pPos.getY() + i + 1 <= pLevel.getMaxBuildHeight()) {
			OptionalInt optionalint = pConfig.minimumSize.minClippedHeight();
			int i1 = this.getMaxFreeTreeHeight(pLevel, i, pPos, pConfig);
			if (i1 >= i || optionalint.isPresent() && i1 >= optionalint.getAsInt()) {
				List<FoliagePlacer.FoliageAttachment> list = pConfig.trunkPlacer.placeTrunk(pLevel, pTrunkBlockSetter, pRandom, i1, pPos, pConfig);
				list.forEach((p_160539_) -> {
					pConfig.foliagePlacer.createFoliage(pLevel, pFoliageBlockSetter, pRandom, pConfig, i1, p_160539_, j, l);
				});
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private int getMaxFreeTreeHeight(LevelSimulatedReader pLevel, int pTrunkHeight, BlockPos pTopPosition, TreeConfiguration pConfig) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(int i = 0; i <= pTrunkHeight + 1; ++i) {
			int j = pConfig.minimumSize.getSizeAtHeight(pTrunkHeight, i);

			for(int k = -j; k <= j; ++k) {
				for(int l = -j; l <= j; ++l) {
					blockpos$mutableblockpos.setWithOffset(pTopPosition, k, i, l);
					if (!TreeFeature.isFree(pLevel, blockpos$mutableblockpos) || !pConfig.ignoreVines && isVine(pLevel, blockpos$mutableblockpos)) {
						return i - 2;
					}
				}
			}
		}

		return pTrunkHeight;
	}

	//   /**
	//    * Called when placing the tree feature.
	//    */
	//   private boolean place(LevelSimulatedRW generationReader, Random rand, BlockPos positionIn, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, BoundingBox boundingBoxIn, TreeConfiguration configIn) {
		//      int i = configIn.trunkPlacer.getHeight(rand);
	//      int j = configIn.foliagePlacer.func_230374_a_(rand, i, configIn);
	//      int k = i - j;
	//      int l = configIn.foliagePlacer.func_230376_a_(rand, k);
	//      BlockPos blockpos;
	//      if (!configIn.forcePlacement) {
	//         int i1 = generationReader.getHeight(Heightmap.Types.OCEAN_FLOOR, positionIn).getY();
	//         int j1 = generationReader.getHeight(Heightmap.Types.WORLD_SURFACE, positionIn).getY();
	//         if (j1 - i1 > configIn.maxWaterDepth) {
	//            return false;
	//         }
	//
	//         int k1;
	//         if (configIn.heightmap == Heightmap.Types.OCEAN_FLOOR) {
	//            k1 = i1;
	//         } else if (configIn.heightmap == Heightmap.Types.WORLD_SURFACE) {
	//            k1 = j1;
	//         } else {
	//            k1 = generationReader.getHeight(configIn.heightmap, positionIn).getY();
	//         }
	//
	//         blockpos = new BlockPos(positionIn.getX(), k1, positionIn.getZ());
	//      } else {
	//         blockpos = positionIn;
	//      }
	//
	//      if (blockpos.getY() >= 1 && blockpos.getY() + i + 1 <= 256) {
	//         if (!isDirtOrFarmlandAt(generationReader, blockpos.down())) {
	//            return false;
	//         } else {
	//            OptionalInt optionalint = configIn.minimumSize.func_236710_c_();
	//            int l1 = this.func_241521_a_(generationReader, i, blockpos, configIn);
	//            if (l1 >= i || optionalint.isPresent() && l1 >= optionalint.getAsInt()) {
	//               List<FoliagePlacer.Foliage> list = configIn.trunkPlacer.getFoliages(generationReader, rand, l1, blockpos, p_225557_4_, boundingBoxIn, configIn);
	//               list.forEach((p_236407_8_) -> {
	//                  configIn.foliagePlacer.func_236752_a_(generationReader, rand, configIn, l1, p_236407_8_, j, l, p_225557_5_, boundingBoxIn);
	//               });
	//               return true;
	//            } else {
	//               return false;
	//            }
	//         }
	//      } else {
	//         return false;
	//      }
	//   }
	//
	//   private int func_241521_a_(LevelSimulatedReader p_241521_1_, int p_241521_2_, BlockPos p_241521_3_, TreeConfiguration p_241521_4_) {
	//      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
	//
	//      for(int i = 0; i <= p_241521_2_ + 1; ++i) {
	//         int j = p_241521_4_.minimumSize.func_230369_a_(p_241521_2_, i);
	//
	//         for(int k = -j; k <= j; ++k) {
	//            for(int l = -j; l <= j; ++l) {
	//               blockpos$mutable.setAndOffset(p_241521_3_, k, i, l);
	//               if (!TreeFeature.isFree(p_241521_1_, blockpos$mutable) || !p_241521_4_.ignoreVines && func_236414_e_(p_241521_1_, blockpos$mutable)) {
	//                  return i - 2;
	//               }
	//            }
	//         }
	//      }
	//
	//      return p_241521_2_;
	//   }

	protected void setBlock(LevelWriter world, BlockPos pos, BlockState state) {
		TreeFeature.setBlockKnownShape(world, pos, state);
	}

	/**
	 * Places the given feature at the given location.
	 * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
	 * that they can safely generate into.
	 * @param pContext A context object with a reference to the level and the position the feature is being placed at
	 */
	public final boolean place(FeaturePlaceContext<TreeConfiguration> pContext) {
		WorldGenLevel worldgenlevel = pContext.level();
		Random random = pContext.random();
		BlockPos blockpos = pContext.origin();
		TreeConfiguration treeconfiguration = pContext.config();
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		BiConsumer<BlockPos, BlockState> biconsumer = (p_160555_, p_160556_) -> {
			set.add(p_160555_.immutable());
			worldgenlevel.setBlock(p_160555_, p_160556_, 19);
		};
		BiConsumer<BlockPos, BlockState> biconsumer1 = (p_160548_, p_160549_) -> {
			set1.add(p_160548_.immutable());
			worldgenlevel.setBlock(p_160548_, p_160549_, 19);
		};
		BiConsumer<BlockPos, BlockState> biconsumer2 = (p_160543_, p_160544_) -> {
			set2.add(p_160543_.immutable());
			worldgenlevel.setBlock(p_160543_, p_160544_, 19);
		};
		boolean flag = this.doPlace(worldgenlevel, random, blockpos, biconsumer, biconsumer1, treeconfiguration);
		if (flag && (!set.isEmpty() || !set1.isEmpty())) {
			if (!treeconfiguration.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list1 = Lists.newArrayList(set1);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list1.sort(Comparator.comparingInt(Vec3i::getY));
				treeconfiguration.decorators.forEach((p_160528_) -> {
					p_160528_.place(worldgenlevel, biconsumer2, random, list, list1);
				});
			}

			return BoundingBox.encapsulatingPositions(Iterables.concat(set, set1, set2)).map((p_160521_) -> {
				DiscreteVoxelShape discretevoxelshape = TreeFeature.updateLeaves(worldgenlevel, p_160521_, set, set2);
				StructureTemplate.updateShapeAtEdge(worldgenlevel, 3, discretevoxelshape, p_160521_.minX(), p_160521_.minY(), p_160521_.minZ());
				return true;
			}).orElse(false);
		} else {
			return false;
		}
	}

	//   @Override
	//   public final boolean place(WorldGenLevel p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, TreeConfiguration p_241855_5_) {
	//      Set<BlockPos> set = Sets.newHashSet();
	//      Set<BlockPos> set1 = Sets.newHashSet();
	//      Set<BlockPos> set2 = Sets.newHashSet();
	//      BoundingBox mutableboundingbox = BoundingBox.getUnknownBox();
	//      boolean flag = this.place(p_241855_1_, p_241855_3_, p_241855_4_, set, set1, mutableboundingbox, p_241855_5_);
	//      if (mutableboundingbox.minX <= mutableboundingbox.maxX && flag && !set.isEmpty()) {
	//         if (!p_241855_5_.decorators.isEmpty()) {
	//            List<BlockPos> list = Lists.newArrayList(set);
	//            List<BlockPos> list1 = Lists.newArrayList(set1);
	//            list.sort(Comparator.comparingInt(Vec3i::getY));
	//            list1.sort(Comparator.comparingInt(Vec3i::getY));
	//            p_241855_5_.decorators.forEach((p_236405_6_) -> {
	//               p_236405_6_.place(p_241855_1_, p_241855_3_, list, list1, set2, mutableboundingbox);
	//            });
	//         }
	//
	//         DiscreteVoxelShape voxelshapepart = TreeFeature.updateLeaves(p_241855_1_, mutableboundingbox, set, set2);
	//         StructureTemplate.updateShapeAtEdge(p_241855_1_, 3, voxelshapepart, mutableboundingbox.minX(), mutableboundingbox.minY(), mutableboundingbox.minZ());
	//         return true;
	//      } else {
	//         return false;
	//      }
	//   }
}