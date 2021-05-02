package com.stereowalker.combat.item;

import com.stereowalker.combat.block.PyraniteFireBlock;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PyraniteItem extends Item {

	public PyraniteItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack) {
		return 1;
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity playerentity = context.getPlayer();
		World iworld = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockState blockstate = iworld.getBlockState(blockpos);
		if (CampfireBlock.canBeLit(blockstate)) {
			iworld.playSound(playerentity, blockpos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
			iworld.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
			if (playerentity != null) {
				context.getItem().shrink(1);
			}

			return ActionResultType.func_233537_a_(iworld.isRemote());
		} else {
			BlockPos blockpos1 = blockpos.offset(context.getFace());
			if (PyraniteFireBlock.canLightBlock(iworld, blockpos1, context.getPlacementHorizontalFacing())) {
				iworld.playSound(playerentity, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
				BlockState blockstate1 = PyraniteFireBlock.getFireForPlacement(iworld, blockpos1);
				iworld.setBlockState(blockpos1, blockstate1, 11);
				ItemStack itemstack = context.getItem();
				if (playerentity instanceof ServerPlayerEntity) {
					CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos1, itemstack);
					itemstack.shrink(1);
				}

				return ActionResultType.func_233537_a_(iworld.isRemote());
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

}
