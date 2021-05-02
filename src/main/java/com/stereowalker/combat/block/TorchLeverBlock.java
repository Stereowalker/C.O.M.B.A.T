package com.stereowalker.combat.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchLeverBlock extends TorchBlock{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public TorchLeverBlock(AbstractBlock.Properties properties, IParticleData particleData) {
		super(properties, particleData);
		this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, Boolean.valueOf(false)));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
		if (world.isRemote) {
			BlockState blockstate1 = state.cycleValue(POWERED);
			if (blockstate1.get(POWERED)) {
				addParticles(blockstate1, world, pos, 1.0F);
			}

			return ActionResultType.SUCCESS;
		} else {
			BlockState blockstate = this.update(state, world, pos);
			float f = blockstate.get(POWERED) ? 0.6F : 0.5F;
			world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			return ActionResultType.SUCCESS;
		}
	}

	public BlockState update(BlockState state, World world, BlockPos pos) {
		state = state.cycleValue(POWERED);
		world.setBlockState(pos, state, 3);
		this.updateNeighbors(state, world, pos);
		return state;
	}

	private static void addParticles(BlockState state, IWorld worldIn, BlockPos pos, float alpha) {
		//		Direction direction = state.get(HORIZONTAL_FACING).getOpposite();
		//		Direction direction1 = getFacing(state).getOpposite();
		//		double d0 = (double)pos.getX() + 0.5D + 0.1D * (double)direction.getXOffset() + 0.2D * (double)direction1.getXOffset();
		//		double d1 = (double)pos.getY() + 0.5D + 0.1D * (double)direction.getYOffset() + 0.2D * (double)direction1.getYOffset();
		//		double d2 = (double)pos.getZ() + 0.5D + 0.1D * (double)direction.getZOffset() + 0.2D * (double)direction1.getZOffset();
		//		worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	/**
	 * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	 * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	 * of whether the block can receive random update ticks
	 */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.get(POWERED) && rand.nextFloat() < 0.25F) {
			addParticles(stateIn, worldIn, pos, 0.5F);
		}

	}

	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			if (state.get(POWERED)) {
				this.updateNeighbors(state, worldIn, pos);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	/**
	 * @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(POWERED) ? 15 : 0;
	}

	/**
	 * @deprecated call via {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(POWERED) /*&& getFacing(blockState) == side*/ ? 15 : 0;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
	 */
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	protected void updateNeighbors(BlockState p_196378_1_, World p_196378_2_, BlockPos p_196378_3_) {
		p_196378_2_.notifyNeighborsOfStateChange(p_196378_3_, this);
		p_196378_2_.notifyNeighborsOfStateChange(p_196378_3_.offset(Direction.DOWN), this);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

}
