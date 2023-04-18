package com.stereowalker.combat.world.level.block;

import com.stereowalker.combat.world.item.CItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CornBlock extends CropBlock {

	public static final IntegerProperty CORN_AGE = BlockStateProperties.AGE_5;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

	public CornBlock(Properties properties) {
		super(properties);
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return CORN_AGE;
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return CItems.CORN_SEEDS;
	}

	@Override
	public int getMaxAge() {
		return 5;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		if (rand.nextInt(3) != 0) {
			super.tick(state, worldIn, pos, rand);
		}

	}

	@Override
	protected int getBonemealAgeIncrease(Level worldIn) {
		return super.getBonemealAgeIncrease(worldIn) / 3;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CORN_AGE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}

}
