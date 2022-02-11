package com.stereowalker.combat.world.level.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;
import com.stereowalker.combat.world.level.block.entity.MezepineFurnaceBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class MezepineFurnaceBlock extends AbstractFurnaceBlock {

	public MezepineFurnaceBlock(Properties builder) {
		super(builder);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new MezepineFurnaceBlockEntity(pPos, pState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return createFurnaceTicker(pLevel, pBlockEntityType, CBlockEntityType.MEZEPINE_FURNACE);
	}

	@Override
	//TODO: onBlockActrivated
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if(!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if(tile == null) {
			}
			if(tile instanceof MezepineFurnaceBlockEntity) {
				NetworkHooks.openGui((ServerPlayer)player, (MezepineFurnaceBlockEntity)tile, extraData -> {extraData.writeBlockPos(pos);});
			}
		}
		return InteractionResult.SUCCESS;
	}



	@Override
	protected void openContainer(Level worldIn, BlockPos pos, Player player) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof MezepineFurnaceBlockEntity) {
			player.openMenu((MenuProvider)tileentity);
			player.awardStat(Stats.INTERACT_WITH_FURNACE);
		}

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(LIT)) {
			double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY();
			double d2 = (double)pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) {
				worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = stateIn.getValue(FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
			double d6 = rand.nextDouble() * 6.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
			worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(CParticleTypes.PYRANITE_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
		}
	}

}
