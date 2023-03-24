package com.stereowalker.combat.world.inventory;

import java.util.Optional;

import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.combat.world.item.crafting.FletchingRecipe;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FletchingMenu extends RecipeBookMenu<CraftingContainer> {
	private final CraftingContainer craftMatrix = new CraftingContainer(this, 1, 3);
	private final ResultContainer craftResult = new ResultContainer();
	private final ContainerLevelAccess world;
	private final Player player;

	public FletchingMenu(int p_i50089_1_, Inventory p_i50089_2_) {
		this(p_i50089_1_, p_i50089_2_, ContainerLevelAccess.NULL);
	}

	public FletchingMenu(int p_i50090_1_, Inventory p_i50090_2_, ContainerLevelAccess p_i50090_3_) {
		super(CMenuType.FLETCHING, p_i50090_1_);
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

	protected static void craft(AbstractContainerMenu menu, Level world, Player player, CraftingContainer inv, ResultContainer resultInv) {
		if (!world.isClientSide) {
			ServerPlayer serverplayerentity = (ServerPlayer)player;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<FletchingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(CRecipeType.FLETCHING, inv, world);
			if (optional.isPresent()) {
				FletchingRecipe icraftingrecipe = optional.get();
				if (resultInv.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) {
					itemstack = icraftingrecipe.assemble(inv);
				}
			}

			resultInv.setItem(0, itemstack);
			serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.getStateId(), 0, itemstack));
		}
	}

	@Override
	public void slotsChanged(Container inventoryIn) {
		this.world.execute((world, p_217069_2_) -> {
			craft(this, world, this.player, this.craftMatrix, (ResultContainer) this.craftResult);
		});
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents itemHelperIn) {
		this.craftMatrix.fillStackedContents(itemHelperIn);
	}

	@Override
	public void clearCraftingContent() {
		this.craftMatrix.clearContent();
		this.craftResult.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super CraftingContainer> recipeIn) {
		return recipeIn.matches(this.craftMatrix, this.player.level);
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		this.world.execute((p_217068_2_, p_217068_3_) -> {
			this.clearContainer(playerIn, this.craftMatrix);
		});
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(this.world, playerIn, Blocks.FLETCHING_TABLE);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 0) {
				this.world.execute((p_217067_2_, p_217067_3_) -> {
					itemstack1.getItem().onCraftedBy(itemstack1, p_217067_2_, playerIn);
				});
				if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index >= 4 && index < 40) {
				if (!this.moveItemStackTo(itemstack1, 1, 4, false)) {
					if (index < 31) {
						if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.moveItemStackTo(itemstack1, 4, 31, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
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
			if (index == 0) {
				playerIn.drop(itemstack1, false);
			}
		}

		return itemstack;
	}

	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
		return slotIn.container != this.craftResult && super.canTakeItemForPickAll(stack, slotIn);
	}

	@Override
	public int getResultSlotIndex() {
		return 0;
	}

	@Override
	public int getGridWidth() {
		return this.craftMatrix.getWidth();
	}

	@Override
	public int getGridHeight() {
		return this.craftMatrix.getHeight();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		// TODO Figure this out
		return RecipeBookType.CRAFTING;
	}

	@Override
	public boolean shouldMoveToInventory(int p_150553_) {
		return p_150553_ != this.getResultSlotIndex();
	}
}