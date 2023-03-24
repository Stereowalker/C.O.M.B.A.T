package com.stereowalker.combat.world.level.block;

import java.util.Random;

import com.stereowalker.combat.world.inventory.ArcaneWorkbenchMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcaneWorkbenchBlock extends /* Container */Block {
	private static final Component NAME = new TranslatableComponent("container.arcane_workbench");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	protected ArcaneWorkbenchBlock(Block.Properties builder) {
		super(builder);
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);

//		for(int i = -2; i <= 2; ++i) {
//			for(int j = -2; j <= 2; ++j) {
//				if (i > -2 && i < 2 && j == -1) {
//					j = 2;
//				}
//
//				if (rand.nextInt(16) == 0) {
//					for(int k = 0; k <= 1; ++k) {
//						BlockPos blockpos = pos.add(i, k, j);
//						if (worldIn.getBlockState(blockpos).getEnchantPowerBonus(worldIn, pos) > 0) {
//							if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2))) {
//								break;
//							}
//
//							worldIn.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float)i + rand.nextFloat()) - 0.5D, (double)((float)k - rand.nextFloat() - 1.0F), (double)((float)j + rand.nextFloat()) - 0.5D);
//						}
//					}
//				}
//			}
//		}

	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

//	@Override
//	public TileEntity createNewTileEntity(BlockGetter worldIn) {
//		return new ArcaneWorkbenchTileEntity();
//	}

//	@Override
//	public InteractionResult onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//		if(!worldIn.isRemote) {
//			TileEntity tile = worldIn.getTileEntity(pos);
//			if(tile == null) {
//			}
//			if(tile instanceof ArcaneWorkbenchTileEntity) {
//				NetworkHooks.openGui((ServerPlayerEntity)player, (ArcaneWorkbenchTileEntity)tile, extraData -> {extraData.writeBlockPos(pos);});
//			}
//		}
//		return InteractionResult.SUCCESS;
//	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
//		if(state.getBlock() != newState.getBlock()) {
//			TileEntity te = worldIn.getTileEntity(pos);
//			if(te instanceof ArcaneWorkbenchTileEntity) {
//				InventoryHelper.dropInventoryItems(worldIn, pos, (ArcaneWorkbenchTileEntity)te);
//				worldIn.updateComparatorOutputLevel(pos, this);
//			}
//			super.onReplaced(state, worldIn, pos, newState, isMoving);
//		}
//	}

//	@Nullable
//	public MenuProvider getContainer(BlockState state, Level worldIn, BlockPos pos) {
//		TileEntity tileentity = worldIn.getTileEntity(pos);
//		if (tileentity instanceof ArcaneWorkbenchTileEntity) {
//			Component itextcomponent = ((INameable)tileentity).getDisplayName();
//			return new SimpleMenuProvider((p_220147_2_, p_220147_3_, p_220147_4_) -> {
//				return new ArcaneWorkbenchContainer(p_220147_2_, p_220147_3_);
//			}, itextcomponent);
//		} else {
//			return null;
//		}
//	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(worldIn, pos));
			player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		return new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
			return new ArcaneWorkbenchMenu(p_220270_2_, p_220270_3_, ContainerLevelAccess.create(worldIn, pos));
		}, NAME);
	}

	//	/**
	//	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	//	 */
	//	public void onBlockPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	//		if (stack.hasDisplayName()) {
	//			TileEntity tileentity = worldIn.getTileEntity(pos);
	//			if (tileentity instanceof ArcaneWorkbenchTileEntity) {
	//				((ArcaneWorkbenchTileEntity)tileentity).setCustomName(stack.getDisplayName());
	//			}
	//		}
	//
	//	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}
}