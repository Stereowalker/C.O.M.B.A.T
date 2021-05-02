package com.stereowalker.combat.block;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.entity.boss.RobinEntity;
import com.stereowalker.combat.item.CItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class RobinSummonerBlock extends Block {
	public RobinSummonerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		RobinEntity boss = new RobinEntity(CEntityType.ROBIN, worldIn);
		LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(worldIn);
		lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(pos));
		boss.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		boss.setHeldItem(Hand.MAIN_HAND, new ItemStack(CItems.ARCH));
		BlockPos goldCornerUNE = pos.offset(Direction.UP, 2).offset(Direction.EAST, 2).offset(Direction.NORTH, 2);
		BlockPos goldCornerUSW = pos.offset(Direction.UP, 2).offset(Direction.WEST, 2).offset(Direction.SOUTH, 2);
		BlockPos goldCornerDNW = pos.offset(Direction.DOWN, 2).offset(Direction.WEST, 2).offset(Direction.NORTH, 2);
		BlockPos goldCornerDSE = pos.offset(Direction.DOWN, 2).offset(Direction.EAST, 2).offset(Direction.SOUTH, 2);

		BlockPos ironCornerDNE = pos.offset(Direction.DOWN, 2).offset(Direction.EAST, 2).offset(Direction.NORTH, 2);
		BlockPos ironCornerDSW = pos.offset(Direction.DOWN, 2).offset(Direction.WEST, 2).offset(Direction.SOUTH, 2);
		BlockPos ironCornerUNW = pos.offset(Direction.UP, 2).offset(Direction.WEST, 2).offset(Direction.NORTH, 2);
		BlockPos ironCornerUSE = pos.offset(Direction.UP, 2).offset(Direction.EAST, 2).offset(Direction.SOUTH, 2);
		if(!worldIn.isRemote) {
			if (worldIn.getBlockState(pos.offset(Direction.UP)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.EAST).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.WEST).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.EAST).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.UP).offset(Direction.WEST).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.EAST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.WEST)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.EAST).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.WEST).offset(Direction.NORTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.EAST).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(pos.offset(Direction.DOWN).offset(Direction.WEST).offset(Direction.SOUTH)).getBlock() == Blocks.AIR &&
					worldIn.getBlockState(goldCornerUNE).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.offset(Direction.DOWN)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.offset(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUNE.offset(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.offset(Direction.DOWN)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.offset(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerUSW.offset(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.offset(Direction.UP)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.offset(Direction.SOUTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDNW.offset(Direction.EAST)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.offset(Direction.UP)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.offset(Direction.NORTH)).getBlock() == Blocks.GOLD_BLOCK &&
					worldIn.getBlockState(goldCornerDSE.offset(Direction.WEST)).getBlock() == Blocks.GOLD_BLOCK &&

					worldIn.getBlockState(ironCornerDNE).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.offset(Direction.UP)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.offset(Direction.SOUTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDNE.offset(Direction.WEST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.offset(Direction.UP)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.offset(Direction.NORTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerDSW.offset(Direction.EAST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.offset(Direction.DOWN)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.offset(Direction.SOUTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUNW.offset(Direction.EAST)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.offset(Direction.DOWN)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.offset(Direction.NORTH)).getBlock() == Blocks.IRON_BLOCK &&
					worldIn.getBlockState(ironCornerUSE.offset(Direction.WEST)).getBlock() == Blocks.IRON_BLOCK &&

					worldIn.getDayTime() % 24000L >= 11000 &&
					worldIn.getDayTime() % 24000L <= 13000
					) {
				worldIn.setBlockState(pos, CBlocks.EMPTY_ROBIN_SUMMONER.getDefaultState());
				worldIn.addEntity(lightningboltentity);
				worldIn.createExplosion(lightningboltentity, pos.getX(), pos.getY(), pos.getZ(), 1.5F, Mode.NONE);
				worldIn.setBlockState(goldCornerUNE, Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUNE.offset(Direction.DOWN), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUNE.offset(Direction.SOUTH), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUNE.offset(Direction.WEST), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUSW, Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUSW.offset(Direction.DOWN), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUSW.offset(Direction.NORTH), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerUSW.offset(Direction.EAST), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDNW, Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDNW.offset(Direction.UP), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDNW.offset(Direction.SOUTH), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDNW.offset(Direction.EAST), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDSE, Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDSE.offset(Direction.UP), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDSE.offset(Direction.NORTH), Blocks.COARSE_DIRT.getDefaultState());
				worldIn.setBlockState(goldCornerDSE.offset(Direction.WEST), Blocks.COARSE_DIRT.getDefaultState());

				worldIn.setBlockState(ironCornerDNE, Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDNE.offset(Direction.UP), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDNE.offset(Direction.SOUTH), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDNE.offset(Direction.WEST), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDSW, Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDSW.offset(Direction.UP), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDSW.offset(Direction.NORTH), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerDSW.offset(Direction.EAST), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUNW, Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUNW.offset(Direction.DOWN), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUNW.offset(Direction.SOUTH), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUNW.offset(Direction.EAST), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUSE, Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUSE.offset(Direction.DOWN), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUSE.offset(Direction.NORTH), Blocks.STONE.getDefaultState());
				worldIn.setBlockState(ironCornerUSE.offset(Direction.WEST), Blocks.STONE.getDefaultState());
				worldIn.addEntity(boss);
			}
		}
		return ActionResultType.SUCCESS;
	}

}
