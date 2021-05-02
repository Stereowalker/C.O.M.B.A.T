package com.stereowalker.combat.block;

import com.stereowalker.combat.potion.CEffects;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class FlowingFluidCBlock extends FlowingFluidBlock {

	@SuppressWarnings("deprecation")
	public FlowingFluidCBlock(FlowingFluid p_i49014_1_, Properties p_i49014_2_) {
		super(p_i49014_1_, p_i49014_2_);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		super.onEntityCollision(state, worldIn, pos, entityIn);
		if(this == CBlocks.OIL) {
			if(entityIn instanceof LivingEntity) {
				((LivingEntity)entityIn).addPotionEffect(new EffectInstance(CEffects.FLAMMABLE, 60 * 20, 1));
			}
		}
	}
	
	@Override
	public void catchFire(BlockState state, World world, BlockPos pos, Direction face, LivingEntity igniter) {
		if (this == CBlocks.OIL) {
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10, true, Mode.BREAK);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		super.catchFire(state, world, pos, face, igniter);
	}

}
