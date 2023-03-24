package com.stereowalker.combat.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class EmptySoulGemItem extends Item {

	public EmptySoulGemItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.SOUL_SAND) {
			context.getItemInHand().shrink(1);
			context.getPlayer().addItem(new ItemStack(CItems.SOUL_GEM));
			context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.SAND.defaultBlockState());
		} else if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.SOUL_SOIL) {
			context.getItemInHand().shrink(1);
			context.getPlayer().addItem(new ItemStack(CItems.SOUL_GEM));
			context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.DIRT.defaultBlockState());
		}
		return super.useOn(context);
	}

}
