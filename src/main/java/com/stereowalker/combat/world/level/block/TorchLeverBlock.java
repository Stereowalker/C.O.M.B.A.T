package com.stereowalker.combat.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchLeverBlock extends TorchBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public TorchLeverBlock(BlockBehaviour.Properties properties, ParticleOptions particleData) {
		super(properties, particleData);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTrace) {
		if (world.isClientSide) {
			BlockState blockstate1 = state.cycle(POWERED);
			if (blockstate1.getValue(POWERED)) {
				addParticles(blockstate1, world, pos, 1.0F);
			}

			return InteractionResult.SUCCESS;
		} else {
			BlockState blockstate = this.update(state, world, pos);
			float f = blockstate.getValue(POWERED) ? 0.6F : 0.5F;
			world.playSound((Player)null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
			return InteractionResult.SUCCESS;
		}
	}

	public BlockState update(BlockState state, Level world, BlockPos pos) {
		state = state.cycle(POWERED);
		world.setBlock(pos, state, 3);
		this.updateNeighbours(state, world, pos);
		return state;
	}

	private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha) {
		//		Direction direction = state.getValue(HORIZONTAL_FACING).getOpposite();
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
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (stateIn.getValue(POWERED) && rand.nextFloat() < 0.25F) {
			addParticles(stateIn, worldIn, pos, 0.5F);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			if (state.getValue(POWERED)) {
				this.updateNeighbours(state, worldIn, pos);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWERED) /*&& getFacing(blockState) == side*/ ? 15 : 0;
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	protected void updateNeighbours(BlockState p_196378_1_, Level p_196378_2_, BlockPos p_196378_3_) {
		p_196378_2_.updateNeighborsAt(p_196378_3_, this);
		p_196378_2_.updateNeighborsAt(p_196378_3_.relative(Direction.DOWN), this);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

}
