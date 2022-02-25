package com.stereowalker.combat.world.level.levelgen;

import java.util.Arrays;

import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AcrotlestAquifer implements Aquifer {
	private static final int X_RANGE = 10;
    private static final int Y_RANGE = 9;
    private static final int Z_RANGE = 10;
    private static final int X_SEPARATION = 6;
    private static final int Y_SEPARATION = 3;
    private static final int Z_SEPARATION = 6;
    private static final int X_SPACING = 16;
    private static final int Y_SPACING = 12;
    private static final int Z_SPACING = 16;
    protected final NormalNoise barrierNoise;
    protected final NormalNoise waterLevelNoise;
    protected final NormalNoise lavaNoise;
    protected final NoiseGeneratorSettings noiseGeneratorSettings;
    protected final AcrotlestAquifer.AquiferStatus[] aquiferCache;
    protected final long[] aquiferLocationCache;
    protected boolean shouldScheduleFluidUpdate;
    protected final NoiseSampler sampler;
    protected final int minGridX;
    protected final int minGridY;
    protected final int minGridZ;
    protected final int gridSizeX;
    protected final int gridSizeZ;

    public AcrotlestAquifer(ChunkPos pChunkPos, NormalNoise pBarrierNoise, NormalNoise pWaterLevelNoise, NormalNoise pLavaNoise, NoiseGeneratorSettings pNoiseGeneratorSettings, NoiseSampler pSampler, int pMinY, int pHeight) {
       this.barrierNoise = pBarrierNoise;
       this.waterLevelNoise = pWaterLevelNoise;
       this.lavaNoise = pLavaNoise;
       this.noiseGeneratorSettings = pNoiseGeneratorSettings;
       this.sampler = pSampler;
       this.minGridX = this.gridX(pChunkPos.getMinBlockX()) - 1;
       int i = this.gridX(pChunkPos.getMaxBlockX()) + 1;
       this.gridSizeX = i - this.minGridX + 1;
       this.minGridY = this.gridY(pMinY) - 1;
       int j = this.gridY(pMinY + pHeight) + 1;
       int k = j - this.minGridY + 1;
       this.minGridZ = this.gridZ(pChunkPos.getMinBlockZ()) - 1;
       int l = this.gridZ(pChunkPos.getMaxBlockZ()) + 1;
       this.gridSizeZ = l - this.minGridZ + 1;
       int i1 = this.gridSizeX * k * this.gridSizeZ;
       this.aquiferCache = new AcrotlestAquifer.AquiferStatus[i1];
       this.aquiferLocationCache = new long[i1];
       Arrays.fill(this.aquiferLocationCache, Long.MAX_VALUE);
    }

    /**
     * @return A cache index based on grid positions.
     */
    protected int getIndex(int pGridX, int pGridY, int pGridZ) {
       int i = pGridX - this.minGridX;
       int j = pGridY - this.minGridY;
       int k = pGridZ - this.minGridZ;
       return (j * this.gridSizeZ + k) * this.gridSizeX + i;
    }

    /**
     * Computes the state to be placed at a given (x, y, z) position in the world.
     * @param pStoneSource The stone source used to choose solid blocks (stone) for placement.
     * @param pNoise The cumulative terrain density noise. Positive values indicate the position is typically filled
     * with solid blocks, negative values indicate air or fluid.
     */
    public BlockState computeState(BaseStoneSource pStoneSource, int pX, int pY, int pZ, double pNoise) {
       if (pNoise <= 0.0D) {
          double d0;
          BlockState blockstate;
          boolean flag;
          if (this.isLavaLevel(pY)) {
             blockstate = CBlocks.GREEN_TSUNE.defaultBlockState();
             d0 = 0.0D;
             flag = false;
          } else {
             int i = Math.floorDiv(pX - 5, 16);
             int j = Math.floorDiv(pY + 1, 12);
             int k = Math.floorDiv(pZ - 5, 16);
             int l = Integer.MAX_VALUE;
             int i1 = Integer.MAX_VALUE;
             int j1 = Integer.MAX_VALUE;
             long k1 = 0L;
             long l1 = 0L;
             long i2 = 0L;

             for(int j2 = 0; j2 <= 1; ++j2) {
                for(int k2 = -1; k2 <= 1; ++k2) {
                   for(int l2 = 0; l2 <= 1; ++l2) {
                      int i3 = i + j2;
                      int j3 = j + k2;
                      int k3 = k + l2;
                      int l3 = this.getIndex(i3, j3, k3);
                      long j4 = this.aquiferLocationCache[l3];
                      long i4;
                      if (j4 != Long.MAX_VALUE) {
                         i4 = j4;
                      } else {
                         WorldgenRandom worldgenrandom = new WorldgenRandom(Mth.getSeed(i3, j3 * 3, k3) + 1L);
                         i4 = BlockPos.asLong(i3 * 16 + worldgenrandom.nextInt(10), j3 * 12 + worldgenrandom.nextInt(9), k3 * 16 + worldgenrandom.nextInt(10));
                         this.aquiferLocationCache[l3] = i4;
                      }

                      int j5 = BlockPos.getX(i4) - pX;
                      int k4 = BlockPos.getY(i4) - pY;
                      int l4 = BlockPos.getZ(i4) - pZ;
                      int i5 = j5 * j5 + k4 * k4 + l4 * l4;
                      if (l >= i5) {
                         i2 = l1;
                         l1 = k1;
                         k1 = i4;
                         j1 = i1;
                         i1 = l;
                         l = i5;
                      } else if (i1 >= i5) {
                         i2 = l1;
                         l1 = i4;
                         j1 = i1;
                         i1 = i5;
                      } else if (j1 >= i5) {
                         i2 = i4;
                         j1 = i5;
                      }
                   }
                }
             }

             AcrotlestAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus = this.getAquiferStatus(k1);
             AcrotlestAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus1 = this.getAquiferStatus(l1);
             AcrotlestAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus2 = this.getAquiferStatus(i2);
             double d6 = this.similarity(l, i1);
             double d7 = this.similarity(l, j1);
             double d8 = this.similarity(i1, j1);
             flag = d6 > 0.0D;
             if (aquifer$noisebasedaquifer$aquiferstatus.fluidLevel >= pY && aquifer$noisebasedaquifer$aquiferstatus.fluidType.is(CBlocks.BIABLE) && this.isLavaLevel(pY - 1)) {
                d0 = 1.0D;
             } else if (d6 > -1.0D) {
                double d9 = 1.0D + (this.barrierNoise.getValue((double)pX, (double)pY, (double)pZ) + 0.05D) / 4.0D;
                double d10 = this.calculatePressure(pY, d9, aquifer$noisebasedaquifer$aquiferstatus, aquifer$noisebasedaquifer$aquiferstatus1);
                double d11 = this.calculatePressure(pY, d9, aquifer$noisebasedaquifer$aquiferstatus, aquifer$noisebasedaquifer$aquiferstatus2);
                double d1 = this.calculatePressure(pY, d9, aquifer$noisebasedaquifer$aquiferstatus1, aquifer$noisebasedaquifer$aquiferstatus2);
                double d2 = Math.max(0.0D, d6);
                double d3 = Math.max(0.0D, d7);
                double d4 = Math.max(0.0D, d8);
                double d5 = 2.0D * d2 * Math.max(d10, Math.max(d11 * d3, d1 * d4));
                d0 = Math.max(0.0D, d5);
             } else {
                d0 = 0.0D;
             }

             blockstate = pY >= aquifer$noisebasedaquifer$aquiferstatus.fluidLevel ? Blocks.AIR.defaultBlockState() : aquifer$noisebasedaquifer$aquiferstatus.fluidType;
          }

          if (pNoise + d0 <= 0.0D) {
             this.shouldScheduleFluidUpdate = flag;
             return blockstate;
          }
       }

       this.shouldScheduleFluidUpdate = false;
       return pStoneSource.getBaseBlock(pX, pY, pZ);
    }

    /**
     * Returns {@code true} if there should be a fluid update scheduled - due to a fluid block being placed in a
     * possibly unsteady position - at the last position passed into {@link #computeState}.
     * This <strong>must</strong> be invoked only after {@link #computeState}, and will be using the same parameters
     * as that method.
     */
    public boolean shouldScheduleFluidUpdate() {
       return this.shouldScheduleFluidUpdate;
    }

    /**
     * @return {@code true} if the provided y position is below the level under which all air blocks should be filled
     * with lava.
     * @see #ALWAYS_LAVA_AT_OR_BELOW_Y_INDEX
     */
    protected boolean isLavaLevel(int pY) {
       return pY - this.noiseGeneratorSettings.noiseSettings().minY() <= 9;
    }

    /**
     * Compares two distances (between aquifers).
     * @return {@code 1.0} if the distances are equal, and returns smaller values the more different in absolute value
     * the two distances are.
     */
    protected double similarity(int pFirstDistance, int pSecondDistance) {
       double d0 = 25.0D;
       return 1.0D - (double)Math.abs(pSecondDistance - pFirstDistance) / d0;
    }

    /**
     * Calculates a pressure value for an aquifer. Used when determining if there should be solid stone between two
     * aquifers. The product of the pressure and similarity determines if stone should be placed.
     */
    protected double calculatePressure(int pY, double pBarrierNoiseValue, AcrotlestAquifer.AquiferStatus pFirstAquifer, AcrotlestAquifer.AquiferStatus pSecondAquifer) {
       if (pY <= pFirstAquifer.fluidLevel && pY <= pSecondAquifer.fluidLevel && pFirstAquifer.fluidType != pSecondAquifer.fluidType) {
          return 1.0D;
       } else {
          int i = Math.abs(pFirstAquifer.fluidLevel - pSecondAquifer.fluidLevel);
          double d0 = 0.5D * (double)(pFirstAquifer.fluidLevel + pSecondAquifer.fluidLevel);
          double d1 = Math.abs(d0 - (double)pY - 0.5D);
          return 0.5D * (double)i * pBarrierNoiseValue - d1;
       }
    }

    protected int gridX(int pX) {
       return Math.floorDiv(pX, 16);
    }

    protected int gridY(int pY) {
       return Math.floorDiv(pY, 12);
    }

    protected int gridZ(int pZ) {
       return Math.floorDiv(pZ, 16);
    }

    /**
     * Calculates the aquifer at a given location. Internally references a cache using the grid positions as an index.
     * If the cache is not populated, computes a new aquifer at that grid location using {@link #computeAquifer}.
     * @param pPackedPos The aquifer block position, packed into a {@code long}.
     */
    protected AcrotlestAquifer.AquiferStatus getAquiferStatus(long pPackedPos) {
       int i = BlockPos.getX(pPackedPos);
       int j = BlockPos.getY(pPackedPos);
       int k = BlockPos.getZ(pPackedPos);
       int l = this.gridX(i);
       int i1 = this.gridY(j);
       int j1 = this.gridZ(k);
       int k1 = this.getIndex(l, i1, j1);
       AcrotlestAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus = this.aquiferCache[k1];
       if (aquifer$noisebasedaquifer$aquiferstatus != null) {
          return aquifer$noisebasedaquifer$aquiferstatus;
       } else {
          AcrotlestAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus1 = this.computeAquifer(i, j, k);
          this.aquiferCache[k1] = aquifer$noisebasedaquifer$aquiferstatus1;
          return aquifer$noisebasedaquifer$aquiferstatus1;
       }
    }

    /**
     * Computes the aquifer which is centered at the given (x, y, z) position.
     * Aquifers are placed at a positive offset from their grid corner and so the grid corner can always be extracted
     * from the aquifer position.
     * If the aquifer y level is above {@link #ALWAYS_USE_SEA_LEVEL_WHEN_ABOVE}, then the aquifer fluid level will be
     * at sea level.
     * Otherwise, this queries the internal noise functions to determine both the height of the aquifer, and the fluid
     * (either lava or water).
     */
    protected AcrotlestAquifer.AquiferStatus computeAquifer(int pX, int pY, int pZ) {
       int i = this.noiseGeneratorSettings.seaLevel();
       if (pY > 30) {
          return new AcrotlestAquifer.AquiferStatus(i, CBlocks.BIABLE.defaultBlockState());
       } else {
          int j = 64;
          int k = -10;
          int l = 40;
          double d0 = this.waterLevelNoise.getValue((double)Math.floorDiv(pX, 64), (double)Math.floorDiv(pY, 40) / 1.4D, (double)Math.floorDiv(pZ, 64)) * 30.0D + -10.0D;
          boolean flag = false;
          if (Math.abs(d0) > 8.0D) {
             d0 *= 4.0D;
          }

          int i1 = Math.floorDiv(pY, 40) * 40 + 20;
          int j1 = i1 + Mth.floor(d0);
          if (i1 == -20) {
             double d1 = this.lavaNoise.getValue((double)Math.floorDiv(pX, 64), (double)Math.floorDiv(pY, 40) / 1.4D, (double)Math.floorDiv(pZ, 64));
             flag = Math.abs(d1) > (double)0.22F;
          }

          return new AcrotlestAquifer.AquiferStatus(Math.min(56, j1), flag ? CBlocks.GREEN_TSUNE.defaultBlockState() : CBlocks.BIABLE.defaultBlockState());
       }
    }

    public static final class AquiferStatus {
       /** The y height of the aquifer. */
       public final int fluidLevel;
       /** The fluid state the aquifer is filled with. */
       public final BlockState fluidType;

       public AquiferStatus(int pFluidLevel, BlockState pFluidState) {
          this.fluidLevel = pFluidLevel;
          this.fluidType = pFluidState;
       }
    }
}
