package com.stereowalker.combat.world.level.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.inventory.DisenchantmentMenu;
import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;
import com.stereowalker.combat.world.level.block.entity.DisenchantmentTableBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DisenchantmentTableBlock extends BaseEntityBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	protected DisenchantmentTableBlock(Block.Properties builder) {
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);

		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				if (i > -2 && i < 2 && j == -1) {
					j = 2;
				}

				if (rand.nextInt(16) == 0) {
					for(int k = 0; k <= 1; ++k) {
						BlockPos blockpos = pos.offset(i, k, j);
						if (worldIn.getBlockState(blockpos).getEnchantPowerBonus(worldIn, pos) > 0) {
							if (!worldIn.isEmptyBlock(pos.offset(i / 2, 0, j / 2))) {
								break;
							}

							worldIn.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float)i + rand.nextFloat()) + 0.5D, (double)((float)k + rand.nextFloat() + 1.0F), (double)((float)j - rand.nextFloat()) + 0.5D);
						}
					}
				}
			}
		}

	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new DisenchantmentTableBlockEntity(pPos, pState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return pLevel.isClientSide ? createTickerHelper(pBlockEntityType, CBlockEntityType.DISENCHANTING_TABLE, DisenchantmentTableBlockEntity::bookAnimationTick) : null;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(worldIn, pos));
			return InteractionResult.SUCCESS;
		}
	}

	@Nullable
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof DisenchantmentTableBlockEntity) {
			Component itextcomponent = ((Nameable)tileentity).getDisplayName();
			return new SimpleMenuProvider((p_220147_2_, p_220147_3_, p_220147_4_) -> {
				return new DisenchantmentMenu(p_220147_2_, p_220147_3_, ContainerLevelAccess.create(worldIn, pos));
			}, itextcomponent);
		} else {
			return null;
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof DisenchantmentTableBlockEntity) {
				((DisenchantmentTableBlockEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}
}