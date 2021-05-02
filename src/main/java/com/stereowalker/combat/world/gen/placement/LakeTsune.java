package com.stereowalker.combat.world.gen.placement;

import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class LakeTsune extends Placement<ChanceConfig> {
	public LakeTsune(Codec<ChanceConfig> p_i232090_1_) {
		super(p_i232090_1_);
	}

	@Override
	public Stream<BlockPos> getPositions(WorldDecoratingHelper p_241857_1_, Random random, ChanceConfig configIn, BlockPos pos) {
		if (random.nextInt(configIn.chance / 10) == 0) {
	         int i = random.nextInt(16) + pos.getX();
	         int j = random.nextInt(16) + pos.getZ();
	         int k = random.nextInt(p_241857_1_.func_242891_a());
	         return Stream.of(new BlockPos(i, k, j));
	      } else {
	         return Stream.empty();
	      }
	}
}