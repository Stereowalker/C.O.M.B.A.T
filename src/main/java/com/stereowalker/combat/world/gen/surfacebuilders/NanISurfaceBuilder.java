package com.stereowalker.combat.world.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class NanISurfaceBuilder extends DefaultSurfaceBuilder{
	
	public static final BlockState NETHERRACK = CBlocks.AUSLDINE_LOG.getDefaultState();
	   protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
	public static final BlockState WATER = Blocks.WATER.getDefaultState();
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();
    protected static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    protected static final BlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
    protected static final BlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
    protected static final BlockState ICE = Blocks.ICE.getDefaultState();
	public static SurfaceBuilderConfig NETHERRACK_SURFACE_BUILDER = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
	public Random rand = new Random(10);
	public NanISurfaceBuilder(Codec<SurfaceBuilderConfig> p_i51315_1_) {
		super(p_i51315_1_);
	}
	
	@Override
	public void buildSurface(Random rand, IChunk chunk, Biome biome, int x,
			int z, int startHeight, double noiseVal, BlockState defBlock, BlockState defFluid,
			int i4, long l1, SurfaceBuilderConfig sbc) {
		super.buildSurface(rand, chunk, biome, x, z, startHeight, noiseVal,
				NETHERRACK, WATER, i4, l1, NETHERRACK_SURFACE_BUILDER);
//		IWorld world = chunk.getWorldForge();
//		int i = world.getSeaLevel();
		int i = 62;
        BlockState iblockstate = GRASS_BLOCK;
        BlockState iblockstate1 = GRASS_BLOCK;
        int j = -1;
        int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int l = x & 15;
        int i1 = z & 15;
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        for (int j1 = 255; j1 >= 0; --j1)
        {
            BlockPos pos = new BlockPos(i1, j1, l);
            if (j1 <= rand.nextInt(5)/*+50*/)
            {
                chunk.setBlockState(pos, BEDROCK, true);
            }
            else
            {
                BlockState iblockstate2 = chunk.getBlockState(pos);

                if (iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                }
                else if (iblockstate2.getBlock() == Blocks.STONE)
                {
                    if (j == -1)
                    {
                        if (k <= 0)
                        {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                        }
                        else if (j1 >= i - 4 && j1 <= i + 1)
                        {
                            iblockstate = GRASS_BLOCK;
                            iblockstate1 = GRASS_BLOCK;
                        }

                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
                        {
                            if (biome.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F)
                            {
                                iblockstate = ICE;
                            }
                            else
                            {
                                iblockstate = WATER;
                            }
                        }

                        j = k;

                        if (j1 >= i - 1)
                        {
                            chunk.setBlockState(pos, iblockstate, true);
                        }
                        else if (j1 < i - 7 - k)
                        {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                            chunk.setBlockState(pos, GRAVEL, true);
                        }
                        else
                        {
                            chunk.setBlockState(pos, iblockstate1,  true);
                        }
                    }
//                    else if (j > 0)
//                    {
//                        --j;
//                        chunk.setBlockState(pos, iblockstate,  true);
//
//                        if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1)
//                        {
//                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
//                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
//                        }
//                    }
                }
            }
        }
		}
}
