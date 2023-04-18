package com.stereowalker.combat.world.level.block.entity;

import com.stereowalker.combat.world.item.CItems;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MezepineFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
	public MezepineFurnaceBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.MEZEPINE_FURNACE, pWorldPosition, pBlockState, RecipeType.SMELTING);
	}

	protected Component getDefaultName() {
		return Component.translatable("container.furnace");
	}

	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new FurnaceMenu(id, player, this, this.dataAccess);
	}

	@Override
	protected int getBurnDuration(ItemStack p_213997_1_) {
		if (p_213997_1_.getItem() != CItems.PYRANITE) {
			return 0;
		} else {
			return 1000;
		}
	}
	
	/*TODO
	@Override
	protected int getTotalCookTime(Level pLevel, RecipeType<? extends AbstractCookingRecipe> pRecipeType, Container pContainer) {
		return super.getTotalCookTime(pLevel, pRecipeType, pContainer) / 3;
	}*/
}
