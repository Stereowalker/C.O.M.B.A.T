package com.stereowalker.combat.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AcrotlestSaplingBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
	private final AbstractTreeGrower treeGrower;

	public AcrotlestSaplingBlock(AbstractTreeGrower treeIn, Block.Properties properties) {
		super(properties);
		this.treeGrower = treeIn;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	/**
	 * Performs a random tick on a block.
	 */
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
		if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0) {
			if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			this.advanceTree(worldIn, pos, state, random);
		}

	}

	public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, RandomSource rand) {
		if (state.getValue(STAGE) == 0) {
			world.setBlock(pos, state.cycle(STAGE), 4);
		} else {
			if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
			this.treeGrower.growTree(world, world.getChunkSource().getGenerator(), pos, state, rand);
		}

	}

	/**
	 * @return whether bonemeal can be used on this block
	 */
	@Override
	public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return (double)worldIn.random.nextFloat() < 0.45D;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		this.advanceTree(worldIn, pos, state, rand);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == CBlocks.PURIFIED_GRASS_BLOCK || block == CBlocks.PURIFIED_DIRT;
	}
}