//package com.stereowalker.combat.world.level.levelgen.surfacebuilders;
//
//import java.util.Random;
//
//import com.mojang.serialization.Codec;
//import com.stereowalker.combat.world.level.block.CBlocks;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.chunk.ChunkAccess;
//import net.minecraft.world.level.levelgen.surfacebuilders.DefaultSurfaceBuilder;
//import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
//import net.minecraft.world.level.material.Material;
//
//public class NanISurfaceBuilder extends DefaultSurfaceBuilder{
//	
//	public static final BlockState NETHERRACK = CBlocks.AUSLDINE_LOG.defaultBlockState();
//	   protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
//	public static final BlockState WATER = Blocks.WATER.defaultBlockState();
//	protected static final BlockState STONE = Blocks.STONE.defaultBlockState();
//    protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
//    protected static final BlockState BEDROCK = Blocks.BEDROCK.defaultBlockState();
//    protected static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
//    protected static final BlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.defaultBlockState();
//    protected static final BlockState SANDSTONE = Blocks.SANDSTONE.defaultBlockState();
//    protected static final BlockState ICE = Blocks.ICE.defaultBlockState();
//	public static SurfaceBuilderBaseConfiguration NETHERRACK_SURFACE_BUILDER = new SurfaceBuilderBaseConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState());
//	public Random rand = new Random(10);
//	public NanISurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_i51315_1_) {
//		super(p_i51315_1_);
//	}
//	
//	@Override
//	public void apply(Random pRandom, ChunkAccess pChunk, Biome pBiome, int pX, int pZ, int pHeight, double pNoise, BlockState pDefaultBlock, BlockState pDefaultFluid, int pSeaLevel, int pMinSurfaceLevel, long pSeed, SurfaceBuilderBaseConfiguration pConfig) {
//		this.apply(pRandom, pChunk, pBiome, pX, pZ, pHeight, pNoise, pDefaultBlock, pDefaultFluid, NETHERRACK_SURFACE_BUILDER.getTopMaterial(), NETHERRACK_SURFACE_BUILDER.getUnderMaterial(), NETHERRACK_SURFACE_BUILDER.getUnderwaterMaterial(), pSeaLevel, pMinSurfaceLevel);
////		IWorld world = chunk.getWorldForge();
////		int i = world.getSeaLevel();
//		int i = 62;
//        BlockState iblockstate = GRASS_BLOCK;
//        BlockState iblockstate1 = GRASS_BLOCK;
//        int j = -1;
//        int k = (int)(pNoise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
//        int l = pX & 15;
//        int i1 = pZ & 15;
//        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//
//        for (int j1 = 255; j1 >= 0; --j1)
//        {
//            BlockPos pos = new BlockPos(i1, j1, l);
//            if (j1 <= rand.nextInt(5)/*+50*/)
//            {
//                pChunk.setBlockState(pos, BEDROCK, true);
//            }
//            else
//            {
//                BlockState iblockstate2 = pChunk.getBlockState(pos);
//
//                if (iblockstate2.getMaterial() == Material.AIR)
//                {
//                    j = -1;
//                }
//                else if (iblockstate2.getBlock() == Blocks.STONE)
//                {
//                    if (j == -1)
//                    {
//                        if (k <= 0)
//                        {
//                            iblockstate = AIR;
//                            iblockstate1 = STONE;
//                        }
//                        else if (j1 >= i - 4 && j1 <= i + 1)
//                        {
//                            iblockstate = GRASS_BLOCK;
//                            iblockstate1 = GRASS_BLOCK;
//                        }
//
//                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
//                        {
//                            if (pBiome.getTemperature(blockpos$mutableblockpos.set(pX, j1, pZ)) < 0.15F)
//                            {
//                                iblockstate = ICE;
//                            }
//                            else
//                            {
//                                iblockstate = WATER;
//                            }
//                        }
//
//                        j = k;
//
//                        if (j1 >= i - 1)
//                        {
//                            pChunk.setBlockState(pos, iblockstate, true);
//                        }
//                        else if (j1 < i - 7 - k)
//                        {
//                            iblockstate = AIR;
//                            iblockstate1 = STONE;
//                            pChunk.setBlockState(pos, GRAVEL, true);
//                        }
//                        else
//                        {
//                            pChunk.setBlockState(pos, iblockstate1,  true);
//                        }
//                    }
////                    else if (j > 0)
////                    {
////                        --j;
////                        chunk.setBlockState(pos, iblockstate,  true);
////
////                        if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1)
////                        {
////                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
////                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
////                        }
////                    }
//                }
//            }
//        }
//		}
//}
