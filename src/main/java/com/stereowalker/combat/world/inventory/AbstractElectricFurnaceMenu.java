package com.stereowalker.combat.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractElectricFurnaceMenu extends RecipeBookMenu<Container> {
	private final Container furnaceInventory;
	private final ContainerData field_217064_e;
	protected final Level level;
	private final RecipeType<? extends AbstractCookingRecipe> recipeType;

	protected AbstractElectricFurnaceMenu(MenuType<?> containerTypeIn, RecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, Inventory playerInventoryIn) {
		this(containerTypeIn, recipeTypeIn, id, playerInventoryIn, new SimpleContainer(2), new SimpleContainerData(4));
	}

	protected AbstractElectricFurnaceMenu(MenuType<?> containerTypeIn, RecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, Inventory playerInventoryIn, Container furnaceInventoryIn, ContainerData p_i50104_6_) {
		super(containerTypeIn, id);
		this.recipeType = recipeTypeIn;
		checkContainerSize(furnaceInventoryIn, 2);
		checkContainerDataCount(p_i50104_6_, 4);
		this.furnaceInventory = furnaceInventoryIn;
		this.field_217064_e = p_i50104_6_;
		this.level = playerInventoryIn.player.level;
		this.addSlot(new Slot(furnaceInventoryIn, 0, 56, 35));
		this.addSlot(new ElectricFurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 1, 116, 35));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
		}

		this.addDataSlots(p_i50104_6_);
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents p_201771_1_) {
		if (this.furnaceInventory instanceof StackedContentsCompatible) {
			((StackedContentsCompatible)this.furnaceInventory).fillStackedContents(p_201771_1_);
		}

	}

	@Override
	public void clearCraftingContent() {
		this.getSlot(0).set(ItemStack.EMPTY);
		this.getSlot(1).set(ItemStack.EMPTY);
	}

	@Override
	public boolean recipeMatches(Recipe<? super Container> recipeIn) {
		return recipeIn.matches(this.furnaceInventory, this.level);
	}

	@Override
	public int getResultSlotIndex() {
		return 2;
	}

	@Override
	public int getGridWidth() {
		return 1;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return this.furnaceInventory.stillValid(playerIn);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 1) {
				if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index != 0) {
				if (this.canSmelt(itemstack1)) {
					if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 2 && index < 29) {
					if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@SuppressWarnings({ "unchecked" })
	protected boolean canSmelt(ItemStack pStack) {
		return this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, new SimpleContainer(pStack), this.level).isPresent();
	}

	@OnlyIn(Dist.CLIENT)
	public int getCookProgressionScaled() {
		int i = this.field_217064_e.get(1);
		int j = this.field_217064_e.get(2);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isPowered() {
		int i = this.field_217064_e.get(3);
		return i == 0 ? false : true;
	}

	@OnlyIn(Dist.CLIENT)
	public int getEnergyScaled() {
		int i = 300;
		if (i == 0) {
			i = 200;
		}

		return Math.min(this.field_217064_e.get(3), i) * 70 / i;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean hasEnergy() {
		return this.field_217064_e.get(3) > 0;
	}

	@Override
	public boolean shouldMoveToInventory(int p_150463_) {
		return p_150463_ != 1;
	}
}