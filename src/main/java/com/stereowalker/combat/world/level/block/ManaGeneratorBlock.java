package com.stereowalker.combat.world.level.block;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.inventory.ManaGeneratorMenu;
import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;
import com.stereowalker.combat.world.level.block.entity.ManaGeneratorBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class ManaGeneratorBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	protected ManaGeneratorBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		if(!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if(tile == null) {
				return 0;
			}
			if(tile instanceof ManaGeneratorBlockEntity) {
				ManaGeneratorBlockEntity gen = (ManaGeneratorBlockEntity)tile;
				return gen.getEnergyStored() / (200/3);
			}
		}
		return 0;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ManaGeneratorBlockEntity(pPos, pState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return createTickerHelper(pBlockEntityType, CBlockEntityType.MANA_GENERATOR, ManaGeneratorBlockEntity::tick);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if(!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if(tile == null) {
			}
			if(tile instanceof ManaGeneratorBlockEntity) {
				NetworkHooks.openScreen((ServerPlayer)player, (ManaGeneratorBlockEntity)tile, extraData -> {extraData.writeBlockPos(pos);});
			}
		}
		return InteractionResult.SUCCESS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			BlockEntity te = worldIn.getBlockEntity(pos);
			if(te instanceof ManaGeneratorBlockEntity) {
				Containers.dropContents(worldIn, pos, (ManaGeneratorBlockEntity)te);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof ManaGeneratorBlockEntity) {
			Component itextcomponent = ((Nameable)tileentity).getDisplayName();
			return new SimpleMenuProvider((p_220147_2_, p_220147_3_, p_220147_4_) -> {
				return new ManaGeneratorMenu(p_220147_2_, p_220147_3_);
			}, itextcomponent);
		} else {
			return null;
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof ManaGeneratorBlockEntity) {
				((ManaGeneratorBlockEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}