package com.stereowalker.combat.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GropapyBlock extends BreakableBlock {
	public GropapyBlock(Block.Properties properties) {
		super(properties);
	}

	/**
	 * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
	 * Block.removedByPlayer
	 */
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			if (worldIn.getDimensionType().isUltrawarm()) {
				worldIn.removeBlock(pos, false);
				return;
			}

			Material material = worldIn.getBlockState(pos.down()).getMaterial();
			if (material.blocksMovement() || material.isLiquid()) {
				worldIn.setBlockState(pos, CBlocks.BIABLE.getDefaultState());
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (world.getLightFor(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
			this.turnIntoBiable(state, world, pos);
		}

	}

	protected void turnIntoBiable(BlockState p_196454_1_, World worldIn, BlockPos p_196454_3_) {
		if (worldIn.getDimensionType().isUltrawarm()) {
			worldIn.removeBlock(p_196454_3_, false);
		} else {
			worldIn.setBlockState(p_196454_3_, CBlocks.BIABLE.getDefaultState());
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

	/**
	 * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
	 */
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (entityIn.isSuppressingBounce()) {
	         super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
	      } else {
	         entityIn.onLivingFall(fallDistance, 0.0F);
	      }

	}

	/**
	 * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
	 * on its own
	 */
	public void onLanded(IBlockReader worldIn, Entity entityIn) {
		if (entityIn.isSuppressingBounce()) {
			super.onLanded(worldIn, entityIn);
		} else {
			this.func_226946_a_(entityIn);
		}

	}

	private void func_226946_a_(Entity p_226946_1_) {
		Vector3d vec3d = p_226946_1_.getMotion();
		if (vec3d.y < 0.0D) {
			double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
			p_226946_1_.setMotion(vec3d.x, -vec3d.y * d0, vec3d.z);
		}

	}
//
//	/**
//	 * Called when the given entity walks on this Block
//	 */
//	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
//		double d0 = Math.abs(entityIn.getMotion().y);
//		if (d0 < 0.1D && !entityIn.func_226271_bk_()) {
//			double d1 = 0.4D + d0 * 0.2D;
//			entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
//		}
//
//		super.onEntityWalk(worldIn, pos, entityIn);
//	}

	//   public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
	//      return type == EntityType.POLAR_BEAR;
	//   }
}