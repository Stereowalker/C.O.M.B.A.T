package com.stereowalker.combat.world.level.block;

import java.util.Map;

import com.stereowalker.combat.tags.BlockCTags;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
public class BeamBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	public static final BooleanProperty UP = PipeBlock.UP;
	public static final BooleanProperty DOWN = PipeBlock.DOWN;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP_H = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_199775_0_) -> {
		return p_199775_0_.getKey().getAxis().isHorizontal();
	}).collect(Util.toMap());
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP_V = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_199775_0_) -> {
		return p_199775_0_.getKey().getAxis().isVertical();
	}).collect(Util.toMap());
	protected final VoxelShape[] beamShape;

	public BeamBlock(Properties properties) {
		super(properties);
		this.beamShape = this.beamShape();
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)).setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	protected VoxelShape[] beamShape() {
		VoxelShape voxelBase = Block.box(7,7,7,9,9,9);
		VoxelShape voxelDown = Block.box(7,0,7,9,9,9);
		VoxelShape voxelUp = Block.box(7,7,7,9,16,9);
		VoxelShape voxelNorth = Block.box(7,7,0,9,9,9);
		VoxelShape voxelSouth = Block.box(7,7,7,9,9,16);
		VoxelShape voxelWest = Block.box(0,7,7,9,9,9);
		VoxelShape voxelEast = Block.box(7,7,7,16,9,9);
		VoxelShape voxelDU = Shapes.or(voxelDown, voxelUp);
		VoxelShape voxelDN = Shapes.or(voxelDown, voxelNorth);
		VoxelShape voxelDS = Shapes.or(voxelDown, voxelSouth);
		VoxelShape voxelDW = Shapes.or(voxelDown, voxelWest);
		VoxelShape voxelDE = Shapes.or(voxelDown, voxelEast);
		VoxelShape voxelUN = Shapes.or(voxelUp, voxelNorth);
		VoxelShape voxelUS = Shapes.or(voxelUp, voxelSouth);
		VoxelShape voxelUW = Shapes.or(voxelUp, voxelWest);
		VoxelShape voxelUE = Shapes.or(voxelUp, voxelEast);
		VoxelShape voxelNS = Shapes.or(voxelNorth, voxelSouth);
		VoxelShape voxelNW = Shapes.or(voxelNorth, voxelWest);
		VoxelShape voxelNE = Shapes.or(voxelNorth, voxelEast);
		VoxelShape voxelSW = Shapes.or(voxelSouth, voxelWest);
		VoxelShape voxelSE = Shapes.or(voxelSouth, voxelEast);
		VoxelShape voxelWE = Shapes.or(voxelWest, voxelEast);
		VoxelShape voxelDUN = Shapes.or(voxelDU, voxelNorth);
		VoxelShape voxelNSW = Shapes.or(voxelNorth, voxelSW);
		VoxelShape voxelUNSW = Shapes.or(voxelUp, voxelNSW);
		VoxelShape voxelSWE = Shapes.or(voxelEast, voxelSW);
		VoxelShape voxelDUNSW = Shapes.or(voxelDown, voxelUNSW);

		VoxelShape[] avoxelshape = new VoxelShape[]{Shapes.empty(), voxelDown, voxelUp, voxelDU, voxelNorth, voxelDN, voxelUN, voxelDUN, voxelSouth, 
				Shapes.or(voxelDown, voxelSouth), Shapes.or(voxelUp, voxelSouth), Shapes.or(voxelDU, voxelSouth), voxelNS, Shapes.or(voxelNS, voxelDown), 
				Shapes.or(voxelNS, voxelUp), Shapes.or(voxelNS, voxelDU), voxelWest, Shapes.or(voxelWest, voxelDown), Shapes.or(voxelWest, voxelUp), 
				Shapes.or(voxelWest, voxelDU), Shapes.or(voxelWest, voxelNorth), Shapes.or(voxelWest, voxelDN), Shapes.or(voxelWest, voxelUN), 
				Shapes.or(voxelWest, voxelDUN), voxelSW, Shapes.or(voxelSW, voxelDown),  Shapes.or(voxelSW, voxelUp), Shapes.or(voxelSW, voxelDU),voxelNSW, 
				Shapes.or(voxelNSW, voxelDown), voxelUNSW, voxelDUNSW, voxelEast, voxelDE, voxelUE, Shapes.or(voxelEast, voxelDU), voxelNE, 
				Shapes.or(voxelEast, voxelDN), Shapes.or(voxelEast, voxelUN), Shapes.or(voxelNE, voxelDU), voxelSE, Shapes.or(voxelEast, voxelDS), 
				Shapes.or(voxelEast, voxelUS), Shapes.or(voxelSE, voxelDU), Shapes.or(voxelEast, voxelNS), Shapes.or(voxelSE, voxelDN), 
				Shapes.or(voxelSE, voxelUN), Shapes.or(voxelSE, voxelDUN), voxelWE, Shapes.or(voxelEast, voxelDW), Shapes.or(voxelEast, voxelUW), 
				Shapes.or(voxelWE, voxelDU), Shapes.or(voxelEast, voxelNW), Shapes.or(voxelWE, voxelDN), Shapes.or(voxelWE, voxelUN), 
				Shapes.or(voxelWE, voxelDUN), Shapes.or(voxelEast, voxelSW), Shapes.or(voxelWE, voxelDS), Shapes.or(voxelWE, voxelUS), 
				Shapes.or(voxelSWE, voxelDU), Shapes.or(voxelWE, voxelNS), Shapes.or(voxelSWE, voxelDN), Shapes.or(voxelSWE, voxelUN), 
				Shapes.or(voxelSWE, voxelDUN)};

		for(int i = 0; i < 64; ++i) {
			avoxelshape[i] = Shapes.or(voxelBase, avoxelshape[i]);
		}

		return avoxelshape;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return this.beamShape[this.getIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return this.beamShape[this.getIndex(state)];
	}

	public VoxelShape getRenderShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return this.beamShape[this.getIndex(state)];
	}

	private static int getMask(Direction p_196407_0_) {
		return 1 << p_196407_0_.get3DDataValue();
	}

	protected int getIndex(BlockState p_196406_1_) {
		int i = 0;
		if (p_196406_1_.getValue(DOWN)) {
			i |= getMask(Direction.DOWN);
		}
		if (p_196406_1_.getValue(UP)) {
			i |= getMask(Direction.UP);
		}
		if (p_196406_1_.getValue(NORTH)) {
			i |= getMask(Direction.NORTH);
		}
		if (p_196406_1_.getValue(SOUTH)) {
			i |= getMask(Direction.SOUTH);
		}
		if (p_196406_1_.getValue(WEST)) {
			i |= getMask(Direction.WEST);
		}
		if (p_196406_1_.getValue(EAST)) {
			i |= getMask(Direction.EAST);
		}

		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return !state.getValue(WATERLOGGED) && fluidIn == Fluids.WATER;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		switch(rot) {
		case CLOCKWISE_180:
			return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		switch(mirrorIn) {
		case LEFT_RIGHT:
			return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
		default:
			return super.mirror(state, mirrorIn);
		}
	}

	//	@Override
	//	public boolean isFullCube(BlockState state) {
	//		return false;
	//	}

	@Override
	public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
		if (p_225533_2_.isClientSide) {
			ItemStack itemstack = p_225533_4_.getItemInHand(p_225533_5_);
			return itemstack.getItem() == Items.LEAD ? InteractionResult.SUCCESS : InteractionResult.PASS;
		} else {
			return LeadItem.bindPlayerMobs(p_225533_4_, p_225533_2_, p_225533_3_);
		}
	}

	public boolean connectsTo(BlockState pState, boolean pIsSideSolid, Direction pDirection) {
//		Block block = pState.getBlock();
		boolean flag = this.isSameBeam(pState);
		return !isExceptionForConnection(pState) && pIsSideSolid || flag;
	}

	   private boolean isSameBeam(BlockState pState) {
	      return pState.is(BlockCTags.BEAMS) && pState.is(BlockCTags.WOODEN_BEAMS) == this.defaultBlockState().is(BlockCTags.WOODEN_BEAMS);
	   }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockGetter iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockPos blockpos5 = blockpos.above();
		BlockPos blockpos6 = blockpos.below();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		BlockState blockstate4 = iblockreader.getBlockState(blockpos5);
		BlockState blockstate5 = iblockreader.getBlockState(blockpos6);
		return super.getStateForPlacement(context)
				.setValue(NORTH, Boolean.valueOf(this.connectsTo(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)))
				.setValue(EAST, Boolean.valueOf(this.connectsTo(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST)))
				.setValue(SOUTH, Boolean.valueOf(this.connectsTo(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH)))
				.setValue(WEST, Boolean.valueOf(this.connectsTo(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST)))
				.setValue(UP, Boolean.valueOf(this.connectsTo(blockstate4, blockstate4.isFaceSturdy(iblockreader, blockpos5, Direction.DOWN), Direction.DOWN)))
				.setValue(DOWN, Boolean.valueOf(this.connectsTo(blockstate5, blockstate5.isFaceSturdy(iblockreader, blockpos6, Direction.UP), Direction.UP)))
				.setValue(WATERLOGGED, Boolean.valueOf(FluidState.getType() == Fluids.WATER));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		if(facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
			return stateIn.setValue(FACING_TO_PROPERTY_MAP_H.get(facing), Boolean.valueOf(this.connectsTo(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()), facing.getOpposite())));
		} else if(facing.getAxis().getPlane() == Direction.Plane.VERTICAL){
			return stateIn.setValue(FACING_TO_PROPERTY_MAP_V.get(facing), Boolean.valueOf(this.connectsTo(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()), facing.getOpposite())));
		}
		else return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}
}
