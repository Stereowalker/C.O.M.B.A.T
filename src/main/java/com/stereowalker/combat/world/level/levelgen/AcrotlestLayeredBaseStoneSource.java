package com.stereowalker.combat.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class AcrotlestLayeredBaseStoneSource implements BaseStoneSource {
	private final WorldgenRandom random;
	private final long seed;
	private final BlockState topLayerBlock, middleLayerBlock, finalLayerBlock;

	public AcrotlestLayeredBaseStoneSource(long pSeed, BlockState pTopLayerBlock, BlockState pMiddleLayerBlock, BlockState pFinalLayerBlock) {
		this.random = new WorldgenRandom(pSeed);
		this.seed = pSeed;
		this.topLayerBlock = pTopLayerBlock;
		this.middleLayerBlock = pMiddleLayerBlock;
		this.finalLayerBlock = pFinalLayerBlock;
	}

	@Override
	public BlockState getBaseBlock(int pX, int pY, int pZ) {
		int l1 = 68;
		int l2 = 60;
		if (pY > l1) {
			return this.topLayerBlock;
		}
		else if (pY >= l2 && pY <= l1) {
			double d0 = Mth.map((double)pY, l2, l1, 1.0D, 0.0D);
			this.random.setBaseStoneSeed(this.seed, pX, pY, pZ);
			return (double)this.random.nextFloat() < d0 ? this.middleLayerBlock : this.topLayerBlock;
		}
		else if (pY > -60 && pY < l2) {
			return this.middleLayerBlock;
		}
		else if (pY >= -68 && pY <= -60) {
			double d0 = Mth.map((double)pY, -8.0D, 0.0D, 1.0D, 0.0D);
			this.random.setBaseStoneSeed(this.seed, pX, pY, pZ);
			return (double)this.random.nextFloat() < d0 ? this.finalLayerBlock : this.middleLayerBlock;
		}
		else {
			return finalLayerBlock;
		}
	}
}
