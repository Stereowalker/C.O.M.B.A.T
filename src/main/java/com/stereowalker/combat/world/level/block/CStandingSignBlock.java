package com.stereowalker.combat.world.level.block;

import com.stereowalker.combat.world.level.block.entity.CSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CStandingSignBlock extends StandingSignBlock {

	public CStandingSignBlock(Properties properties, WoodType type) {
		super(properties, type);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CSignBlockEntity(pPos, pState);
	}

}
