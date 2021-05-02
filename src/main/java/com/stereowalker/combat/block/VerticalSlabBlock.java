package com.stereowalker.combat.block;

import javax.annotation.Nullable;

import com.stereowalker.combat.state.properties.BlockStatePropertiesList;
import com.stereowalker.combat.state.properties.VerticalSlabType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class VerticalSlabBlock extends Block implements IWaterLoggable {
	public static final EnumProperty<VerticalSlabType> TYPE = BlockStatePropertiesList.VERTICAL_SLAB_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);

	public VerticalSlabBlock(Block.Properties builder) {
		super(builder);
		this.setDefaultState(this.getDefaultState().with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false)));
	}

	public boolean isTransparent(BlockState state) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VerticalSlabType slabtype = state.get(TYPE);
		switch(slabtype) {
		case DOUBLE:
			return VoxelShapes.fullCube();
		case EAST:
			return EAST_SHAPE;
		case WEST:
			return WEST_SHAPE;
		case SOUTH:
			return SOUTH_SHAPE;
		default:
			return NORTH_SHAPE;
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		Direction playerFacing = context.getPlayer().getHorizontalFacing();
		BlockState blockState = context.getWorld().getBlockState(context.getPos());
		if (blockState.getBlock() == this) {
			return blockState.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false));
		} else {
			FluidState FluidState = context.getWorld().getFluidState(context.getPos());
			BlockState northBlockstate = this.getDefaultState().with(TYPE, VerticalSlabType.NORTH).with(WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER));
			BlockState southBlockstate = this.getDefaultState().with(TYPE, VerticalSlabType.SOUTH).with(WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER));
			BlockState eastBlockstate = this.getDefaultState().with(TYPE, VerticalSlabType.EAST).with(WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER));
			BlockState westBlockstate = this.getDefaultState().with(TYPE, VerticalSlabType.WEST).with(WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER));
			Direction direction = context.getFace();
			if		(direction!= Direction.SOUTH && (direction == Direction.NORTH || (direction == Direction.UP || direction == Direction.DOWN)&&((double)context.getHitVec().z - (double)blockpos.getZ() > 0.5D && (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH)))) return northBlockstate;
			else if(direction != Direction.NORTH && (direction == Direction.SOUTH || (direction == Direction.UP || direction == Direction.DOWN)&&((double)context.getHitVec().z - (double)blockpos.getZ() < 0.5D && (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH)))) return southBlockstate;
			else if(direction != Direction.EAST  && (direction == Direction.WEST  || (direction == Direction.UP || direction == Direction.DOWN)&&((double)context.getHitVec().x - (double)blockpos.getX() > 0.5D && (playerFacing == Direction.WEST  || playerFacing == Direction.EAST)))) return westBlockstate;
			else if(direction != Direction.WEST  && (direction == Direction.EAST  || (direction == Direction.UP || direction == Direction.DOWN)&&((double)context.getHitVec().x - (double)blockpos.getX() < 0.5D && (playerFacing == Direction.WEST  || playerFacing == Direction.EAST)))) return eastBlockstate;
			else return westBlockstate;
		}
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		VerticalSlabType slabtype = state.get(TYPE);
		if (slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem()) {
			if (useContext.replacingClickedOnBlock()) {
				Direction direction = useContext.getFace();
				if (slabtype == VerticalSlabType.EAST) {
					return direction == Direction.EAST;
				} else if (slabtype == VerticalSlabType.WEST) {
					return direction == Direction.WEST;
				} else if (slabtype == VerticalSlabType.NORTH) {
					return direction == Direction.NORTH;
				} else {
					return direction == Direction.SOUTH;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE ? IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn) : false;
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE ? IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn) : false;
	}

	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction Direction, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, Direction, facingState, worldIn, currentPos, facingPos);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch(type) {
		case LAND:
			return state.get(TYPE) == VerticalSlabType.NORTH || state.get(TYPE) == VerticalSlabType.SOUTH || state.get(TYPE) == VerticalSlabType.EAST || state.get(TYPE) == VerticalSlabType.WEST;
		case WATER:
			return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}

	//	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
	//		return worldIn.getMaxLightLevel();
	//	}
	//
	//	protected boolean canSilkHarvest() {
	//		return false;
	//	}
	//
	//
	//	/**
	//	 * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
	//	 * @deprecated prefer calling {@link BlockState#isTopSolid()} wherever possible
	//	 */
	//	public boolean isTopSolid(BlockState state) {
	//		return state.get(TYPE) == VerticalSlabType.DOUBLE;
	//	}
	//
	//	public int quantityDropped(BlockState state, Random random) {
	//		return state.get(TYPE) == VerticalSlabType.DOUBLE ? 2 : 1;
	//	}
	//
	//	/**
	//	 * @deprecated call via {@link BlockState#isFullCube()} whenever possible. Implementing/overriding is fine.
	//	 */
	//	public boolean isFullCube(BlockState state) {
	//		return state.get(TYPE) == VerticalSlabType.DOUBLE;
	//	}
	//
	//	public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
	//		if (state.get(WATERLOGGED)) {
	//			worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
	//			return Fluids.WATER;
	//		} else {
	//			return Fluids.EMPTY;
	//		}
	//	}

}