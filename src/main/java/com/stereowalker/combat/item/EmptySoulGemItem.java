package com.stereowalker.combat.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class EmptySoulGemItem extends Item {

	public EmptySoulGemItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (context.getWorld().getBlockState(context.getPos()).getBlock() == Blocks.SOUL_SAND) {
			context.getItem().shrink(1);
			context.getPlayer().addItemStackToInventory(new ItemStack(CItems.SOUL_GEM));
			context.getWorld().setBlockState(context.getPos(), Blocks.SAND.getDefaultState());
		}
		return super.onItemUse(context);
	}

}
