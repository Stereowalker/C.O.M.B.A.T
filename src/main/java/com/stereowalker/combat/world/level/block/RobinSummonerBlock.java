package com.stereowalker.combat.world.level.block;

import com.stereowalker.combat.world.entity.boss.robin.RobinBoss;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RobinSummonerBlock extends Block {
	public RobinSummonerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		RobinBoss boss = new RobinBoss(worldIn);
		LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(worldIn);
		lightningboltentity.moveTo(Vec3.atBottomCenterOf(pos));
		boss.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		boss.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(CItems.ARCH));
		BlockPos goldCornerUNE = pos.relative(Direction.UP, 2).relative(Direction.EAST, 2).relative(Direction.NORTH, 2);
		BlockPos goldCornerUSW = pos.relative(Direction.UP, 2).relative(Direction.WEST, 2).relative(Direction.SOUTH, 2);
		BlockPos goldCornerDNW = pos.relative(Direction.DOWN, 2).relative(Direction.WEST, 2).relative(Direction.NORTH, 2);
		BlockPos goldCornerDSE = pos.relative(Direction.DOWN, 2).relative(Direction.EAST, 2).relative(Direction.SOUTH, 2);

		BlockPos ironCornerDNE = pos.relative(Direction.DOWN, 2).relative(Direction.EAST, 2).relative(Direction.NORTH, 2);
		BlockPos ironCornerDSW = pos.relative(Direction.DOWN, 2).relative(Direction.WEST, 2).relative(Direction.SOUTH, 2);
		BlockPos ironCornerUNW = pos.relative(Direction.UP, 2).relative(Direction.WEST, 2).relative(Direction.NORTH, 2);
		BlockPos ironCornerUSE = pos.relative(Direction.UP, 2).relative(Direction.EAST, 2).relative(Direction.SOUTH, 2);
		if(!worldIn.isClientSide) {
			if (worldIn.getBlockState(pos.relative(Direction.UP)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.EAST).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.WEST).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.EAST).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.UP).relative(Direction.WEST).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.EAST).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.WEST).relative(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.EAST).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.relative(Direction.DOWN).relative(Direction.WEST).relative(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(goldCornerUNE).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.relative(Direction.DOWN)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.relative(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.relative(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.relative(Direction.DOWN)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.relative(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.relative(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.relative(Direction.UP)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.relative(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.relative(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.relative(Direction.UP)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.relative(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.relative(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK &&

					worldIn.getBlockState(ironCornerDNE).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.relative(Direction.UP)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.relative(Direction.SOUTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.relative(Direction.WEST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.relative(Direction.UP)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.relative(Direction.NORTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.relative(Direction.EAST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.relative(Direction.DOWN)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.relative(Direction.SOUTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.relative(Direction.EAST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.relative(Direction.DOWN)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.relative(Direction.NORTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.relative(Direction.WEST)).getBlock() == Blocks.IRON_BLOCK &&

					worldIn.getDayTime() % 24000L >= 11000 &&
					worldIn.getDayTime() % 24000L <= 13000
					) {
				worldIn.setBlockAndUpdate(pos, CBlocks.EMPTY_ROBIN_SUMMONER.defaultBlockState());
				worldIn.addFreshEntity(lightningboltentity);
				worldIn.explode(lightningboltentity, pos.getX(), pos.getY(), pos.getZ(), 1.5F, Explosion.BlockInteraction.NONE);
				worldIn.setBlockAndUpdate(goldCornerUNE, Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUNE.relative(Direction.DOWN), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUNE.relative(Direction.SOUTH), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUNE.relative(Direction.WEST), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUSW, Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUSW.relative(Direction.DOWN), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUSW.relative(Direction.NORTH), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerUSW.relative(Direction.EAST), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDNW, Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDNW.relative(Direction.UP), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDNW.relative(Direction.SOUTH), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDNW.relative(Direction.EAST), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDSE, Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDSE.relative(Direction.UP), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDSE.relative(Direction.NORTH), Blocks.COARSE_DIRT.defaultBlockState());
				worldIn.setBlockAndUpdate(goldCornerDSE.relative(Direction.WEST), Blocks.COARSE_DIRT.defaultBlockState());

				worldIn.setBlockAndUpdate(ironCornerDNE, Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDNE.relative(Direction.UP), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDNE.relative(Direction.SOUTH), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDNE.relative(Direction.WEST), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDSW, Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDSW.relative(Direction.UP), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDSW.relative(Direction.NORTH), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerDSW.relative(Direction.EAST), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUNW, Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUNW.relative(Direction.DOWN), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUNW.relative(Direction.SOUTH), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUNW.relative(Direction.EAST), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUSE, Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUSE.relative(Direction.DOWN), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUSE.relative(Direction.NORTH), Blocks.STONE.defaultBlockState());
				worldIn.setBlockAndUpdate(ironCornerUSE.relative(Direction.WEST), Blocks.STONE.defaultBlockState());
				worldIn.addFreshEntity(boss);
			}
		}
		return InteractionResult.SUCCESS;
	}

}
