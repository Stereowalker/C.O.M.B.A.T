package com.stereowalker.combat.world.level.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class GropapyBlock extends HalfTransparentBlock {
	public GropapyBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
	 * Block.removedByPlayer
	 */
	@Override
	public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
		super.playerDestroy(worldIn, player, pos, state, te, stack);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			if (worldIn.dimensionType().ultraWarm()) {
				worldIn.removeBlock(pos, false);
				return;
			}

			Material material = worldIn.getBlockState(pos.below()).getMaterial();
			if (material.blocksMotion() || material.isLiquid()) {
				worldIn.setBlockAndUpdate(pos, CBlocks.BIABLE.defaultBlockState());
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
		if (world.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(world, pos)) {
			this.turnIntoBiable(state, world, pos);
		}

	}

	protected void turnIntoBiable(BlockState p_196454_1_, Level worldIn, BlockPos p_196454_3_) {
		if (worldIn.dimensionType().ultraWarm()) {
			worldIn.removeBlock(p_196454_3_, false);
		} else {
			worldIn.setBlockAndUpdate(p_196454_3_, CBlocks.BIABLE.defaultBlockState());
			worldIn.neighborChanged(p_196454_3_, CBlocks.BIABLE, p_196454_3_);
		}
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return true;
	}

	@Override
	public boolean canStickTo(BlockState state, BlockState other) {
		if (other.getBlock() == this) return false;
		else return super.canStickTo(state, other);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	@Override
	public void fallOn(Level worldIn, BlockState p_154568_, BlockPos pos, Entity entityIn, float fallDistance) {
		if (entityIn.isSuppressingBounce()) {
	         super.fallOn(worldIn, p_154568_, pos, entityIn, fallDistance);
	      } else {
	    	  entityIn.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
	      }

	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		if (entityIn.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(worldIn, entityIn);
		} else {
			this.bounceUp(entityIn);
		}

	}

	private void bounceUp(Entity p_226946_1_) {
		Vec3 vec3d = p_226946_1_.getDeltaMovement();
		if (vec3d.y < 0.0D) {
			double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
			p_226946_1_.setDeltaMovement(vec3d.x, -vec3d.y * d0, vec3d.z);
		}

	}
//
//	/**
//	 * Called when the given entity walks on this Block
//	 */
//	public void onEntityWalk(Level worldIn, BlockPos pos, Entity entityIn) {
//		double d0 = Math.abs(entityIn.getMotion().y);
//		if (d0 < 0.1D && !entityIn.func_226271_bk_()) {
//			double d1 = 0.4D + d0 * 0.2D;
//			entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
//		}
//
//		super.onEntityWalk(worldIn, pos, entityIn);
//	}

	//   public boolean canEntitySpawn(BlockState state, BlockGetter worldIn, BlockPos pos, EntityType<?> type) {
	//      return type == EntityType.POLAR_BEAR;
	//   }
}