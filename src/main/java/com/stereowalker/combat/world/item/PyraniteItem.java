package com.stereowalker.combat.world.item;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.level.block.PyraniteFireBlock;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PyraniteItem extends Item {

	public PyraniteItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 1;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player playerentity = context.getPlayer();
		Level iworld = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = iworld.getBlockState(blockpos);
		if (CampfireBlock.canLight(blockstate)) {
			iworld.playSound(playerentity, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, iworld.getRandom().nextFloat() * 0.4F + 0.8F);
			iworld.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
			if (playerentity != null) {
				context.getItemInHand().shrink(1);
			}

			return InteractionResult.sidedSuccess(iworld.isClientSide());
		} else {
			BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
			if (PyraniteFireBlock.canBePlacedAt(iworld, blockpos1, context.getHorizontalDirection())) {
				iworld.playSound(playerentity, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, iworld.getRandom().nextFloat() * 0.4F + 0.8F);
				BlockState blockstate1 = PyraniteFireBlock.getState(iworld, blockpos1);
				iworld.setBlock(blockpos1, blockstate1, 11);
				ItemStack itemstack = context.getItemInHand();
				if (playerentity instanceof ServerPlayer) {
					CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerentity, blockpos1, itemstack);
					itemstack.shrink(1);
				}

				return InteractionResult.sidedSuccess(iworld.isClientSide());
			} else {
				return InteractionResult.FAIL;
			}
		}
	}

}
