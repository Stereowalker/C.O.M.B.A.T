package com.stereowalker.combat.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacerFurnace;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractElectricFurnaceContainer extends RecipeBookContainer<IInventory> {
	private final IInventory furnaceInventory;
	private final IIntArray field_217064_e;
	protected final World world;
	private final IRecipeType<? extends AbstractCookingRecipe> recipeType;

	protected AbstractElectricFurnaceContainer(ContainerType<?> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn) {
		this(containerTypeIn, recipeTypeIn, id, playerInventoryIn, new Inventory(2), new IntArray(4));
	}

	protected AbstractElectricFurnaceContainer(ContainerType<?> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray p_i50104_6_) {
		super(containerTypeIn, id);
		this.recipeType = recipeTypeIn;
		assertInventorySize(furnaceInventoryIn, 2);
		assertIntArraySize(p_i50104_6_, 4);
		this.furnaceInventory = furnaceInventoryIn;
		this.field_217064_e = p_i50104_6_;
		this.world = playerInventoryIn.player.world;
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

		this.trackIntArray(p_i50104_6_);
	}

	@Override
	public void fillStackedContents(RecipeItemHelper p_201771_1_) {
		if (this.furnaceInventory instanceof IRecipeHelperPopulator) {
			((IRecipeHelperPopulator)this.furnaceInventory).fillStackedContents(p_201771_1_);
		}

	}

	public void clear() {
		this.furnaceInventory.clear();
	}

	@SuppressWarnings("unchecked")
	public void func_217056_a(boolean p_217056_1_, IRecipe<?> p_217056_2_, ServerPlayerEntity p_217056_3_) {
		(new ServerRecipePlacerFurnace<>(this)).place(p_217056_3_, (IRecipe<IInventory>)p_217056_2_, p_217056_1_);
	}

	public boolean matches(IRecipe<? super IInventory> recipeIn) {
		return recipeIn.matches(this.furnaceInventory, this.world);
	}

	public int getOutputSlot() {
		return 2;
	}

	public int getWidth() {
		return 1;
	}

	public int getHeight() {
		return 1;
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 3;
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.furnaceInventory.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 1) {
				if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0) {
				if (this.func_217057_a(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 2 && index < 29) {
					if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean func_217057_a(ItemStack p_217057_1_) {
		return this.world.getRecipeManager().getRecipe((IRecipeType)this.recipeType, new Inventory(p_217057_1_), this.world).isPresent();
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
}