package com.stereowalker.combat.world.level.block;

import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class LiquidCBlock extends LiquidBlock {
	public LiquidCBlock(FlowingFluid pFluid, Properties pProperties) {
		super(pFluid, pProperties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
		super.entityInside(pState, pLevel, pPos, pEntity);
		if(this == CBlocks.OIL) {
			if(pEntity instanceof LivingEntity) {
				((LivingEntity)pEntity).addEffect(new MobEffectInstance(CMobEffects.FLAMMABLE, 60 * 20, 1));
			}
		}
	}
	
	@Override
	public void catchFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
		if (this == CBlocks.OIL) {
			world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 10, true, Explosion.BlockInteraction.BREAK);
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		super.catchFire(state, world, pos, face, igniter);
	}

}
