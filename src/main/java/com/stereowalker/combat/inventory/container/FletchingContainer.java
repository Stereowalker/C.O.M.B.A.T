package com.stereowalker.combat.inventory.container;

import java.util.Optional;

import com.stereowalker.combat.item.crafting.CRecipeType;
import com.stereowalker.combat.item.crafting.IFletchingRecipe;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FletchingContainer extends RecipeBookContainer<CraftingInventory> {
	private final CraftingInventory craftMatrix = new CraftingInventory(this, 1, 3);
	private final CraftResultInventory craftResult = new CraftResultInventory();
	private final IWorldPosCallable world;
	private final PlayerEntity player;

	public FletchingContainer(int p_i50089_1_, PlayerInventory p_i50089_2_) {
		this(p_i50089_1_, p_i50089_2_, IWorldPosCallable.DUMMY);
	}

	public FletchingContainer(int p_i50090_1_, PlayerInventory p_i50090_2_, IWorldPosCallable p_i50090_3_) {
		super(CContainerType.FLETCHING, p_i50090_1_);
		this.world = p_i50090_3_;
		this.player = p_i50090_2_.player;
		this.addSlot(new FletchingResultSlot(p_i50090_2_.player, this.craftMatrix, this.craftResult, 0, 123, 35));

		for(int i = 0; i < 3; ++i) {
			int j = 1;
			this.addSlot(new Slot(this.craftMatrix, i, 31 + j * 18, 17 + i * 18));
		}

		for(int k = 0; k < 3; ++k) {
			for(int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(p_i50090_2_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}

		for(int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(p_i50090_2_, l, 8 + l * 18, 142));
		}

	}

	protected static void craft(int windowId, World world, PlayerEntity player, CraftingInventory inv, CraftResultInventory resultInv) {
		if (!world.isRemote) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<IFletchingRecipe> optional = world.getServer().getRecipeManager().getRecipe(CRecipeType.FLETCHING, inv, world);
			if (optional.isPresent()) {
				IFletchingRecipe icraftingrecipe = optional.get();
				if (resultInv.canUseRecipe(world, serverplayerentity, icraftingrecipe)) {
					itemstack = icraftingrecipe.getCraftingResult(inv);
				}
			}

			resultInv.setInventorySlotContents(0, itemstack);
			serverplayerentity.connection.sendPacket(new SSetSlotPacket(windowId, 0, itemstack));
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		this.world.consume((world, p_217069_2_) -> {
			craft(this.windowId, world, this.player, this.craftMatrix, (CraftResultInventory) this.craftResult);
		});
	}

	public void fillStackedContents(RecipeItemHelper itemHelperIn) {
		this.craftMatrix.fillStackedContents(itemHelperIn);
	}

	public void clear() {
		this.craftMatrix.clear();
		this.craftResult.clear();
	}

	public boolean matches(IRecipe<? super CraftingInventory> recipeIn) {
		return recipeIn.matches(this.craftMatrix, this.player.world);
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.world.consume((p_217068_2_, p_217068_3_) -> {
			this.clearContainer(playerIn, p_217068_2_, this.craftMatrix);
		});
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.world, playerIn, Blocks.FLETCHING_TABLE);
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
			if (index == 0) {
				this.world.consume((p_217067_2_, p_217067_3_) -> {
					itemstack1.getItem().onCreated(itemstack1, p_217067_2_, playerIn);
				});
				if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= 4 && index < 40) {
				if (!this.mergeItemStack(itemstack1, 1, 4, false)) {
					if (index < 31) {
						if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(itemstack1, 4, 31, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
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

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
			if (index == 0) {
				playerIn.dropItem(itemstack2, false);
			}
		}

		return itemstack;
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	public int getOutputSlot() {
		return 0;
	}

	public int getWidth() {
		return this.craftMatrix.getWidth();
	}

	public int getHeight() {
		return this.craftMatrix.getHeight();
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 4;
	}

	@Override
	public RecipeBookCategory func_241850_m() {
		// TODO Figure this out
		return RecipeBookCategory.CRAFTING;
	}
}