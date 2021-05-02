package com.stereowalker.combat.tileentity;

import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MezepineFurnaceTileEntity extends AbstractFurnaceTileEntity {
	public MezepineFurnaceTileEntity() {
		super(CTileEntityType.MEZEPINE_FURNACE, IRecipeType.SMELTING);
	}

	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.furnace");
	}

	protected Container createMenu(int id, PlayerInventory player) {
		return new FurnaceContainer(id, player, this, this.furnaceData);
	}

	@Override
	protected int getBurnTime(ItemStack p_213997_1_) {
		if (p_213997_1_.getItem() != CItems.PYRANITE) {
			return 0;
		} else {
			return 1000;
		}
	}
	
	@Override
	protected int getCookTime() {
		return super.getCookTime() / 3;
	}
}
