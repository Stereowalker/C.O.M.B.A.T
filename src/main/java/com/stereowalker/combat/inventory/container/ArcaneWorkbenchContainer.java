package com.stereowalker.combat.inventory.container;


import java.util.Optional;

import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.item.AbstractMagicCastingItem;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.crafting.AbstractArcaneWorkbenchRecipe;
import com.stereowalker.combat.item.crafting.CRecipeType;
import com.stereowalker.combat.item.crafting.IFletchingRecipe;
import com.stereowalker.unionlib.item.UItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class ArcaneWorkbenchContainer extends Container {
	private final IInventory inputSlots = new Inventory(5) {
		/**
		 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
		 * it hasn't changed and skip it.
		 */
		public void markDirty() {
			super.markDirty();
			ArcaneWorkbenchContainer.this.onCraftMatrixChanged(this);
		}
	};
	private final IWorldPosCallable world;
	private final IInventory outputSlot = new CraftResultInventory();
	private final PlayerEntity player;

	public ArcaneWorkbenchContainer(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, IWorldPosCallable.DUMMY);
	}

	public ArcaneWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldIn) {
		super(CContainerType.ARCANE_WORKBENCH, id);
		this.world = worldIn;
		this.player = playerInventory.player;
		this.addSlot(new Slot(outputSlot, 0, 129, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				if (ArcaneWorkbenchContainer.this.repairWands(false).isEmpty()) {
					ArcaneWorkbenchContainer.this.world.consume((world, pos) -> {
						ArcaneWorkbenchContainer.this.getConversionOutput(world, true);
					});
				} else {
					ArcaneWorkbenchContainer.this.repairWands(true);
				}
				ArcaneWorkbenchContainer.this.onCraftMatrixChanged(inputSlots);
				return stack;
			}
		});
		this.addSlot(new Slot(inputSlots, 0, 58, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		//Tridox Slot
		this.addSlot(new Slot(inputSlots, 1, 33, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 2, 58, 10) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 3, 83, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 4, 58, 60) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});

		for(int i1 = 0; i1 < 3; ++i1) {
			for(int k1 = 0; k1 < 9; ++k1) {
				this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 88 + i1 * 18));
			}
		}

		for(int j1 = 0; j1 < 9; ++j1) {
			this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 146));
		}

	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.inputSlots.isUsableByPlayer(playerIn);
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
				this.world.consume((world, pos) -> {
					itemstack1.getItem().onCreated(itemstack1, world, playerIn);
				});
				if (!this.mergeItemStack(itemstack1, 6, 42, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= 6 && index < 42) {
				if (!this.mergeItemStack(itemstack1, 1, 6, false)) {
					if (index < 33) {
						if (!this.mergeItemStack(itemstack1, 33, 42, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(itemstack1, 6, 33, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.mergeItemStack(itemstack1, 6, 42, false)) {
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

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	public ItemStack mergeItem(ItemStack result, ItemStack mainItem, ItemStack gem1, int cost1, ItemStack gem2, int cost2, ItemStack gem3, int cost3, int tridoxCost, boolean attemptShrink) {
		ItemStack tridox = new ItemStack(CItems.TRIDOX);
		ItemStack baseStack = this.inputSlots.getStackInSlot(0);
		ItemStack tridoxStack = this.inputSlots.getStackInSlot(1);

		ItemStack add1Stack = this.inputSlots.getStackInSlot(2);
		ItemStack add2Stack = this.inputSlots.getStackInSlot(3);
		ItemStack add3Stack = this.inputSlots.getStackInSlot(4);
		if (baseStack.isItemEqual(mainItem) && (tridoxStack.isItemEqual(tridox) && tridoxStack.getCount() >= tridoxCost)) {
			if (add1Stack.isItemEqual(gem1) && add1Stack.getCount() >= cost1) {
				if (((add2Stack.isItemEqual(gem2) && add2Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add3Stack.isItemEqual(gem3) && add3Stack.getCount() >= cost3) || gem3.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add1Stack.shrink(cost1);
						add2Stack.shrink(cost2);
						add3Stack.shrink(cost3);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add2Stack.isItemEqual(gem3) && add2Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add3Stack.isItemEqual(gem2) && add3Stack.getCount() >= cost2) || gem2.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add1Stack.shrink(cost1);
						add2Stack.shrink(cost3);
						add3Stack.shrink(cost2);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
			}
			if (add2Stack.isItemEqual(gem1) && add2Stack.getCount() >= cost1) {
				if (((add1Stack.isItemEqual(gem3) && add1Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add3Stack.isItemEqual(gem2) && add3Stack.getCount() >= cost2) || gem2.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add2Stack.shrink(cost1);
						add1Stack.shrink(cost3);
						add3Stack.shrink(cost2);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add1Stack.isItemEqual(gem2) && add1Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add3Stack.isItemEqual(gem3) && add3Stack.getCount() >= cost3) || gem3.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add2Stack.shrink(cost1);
						add1Stack.shrink(cost2);
						add3Stack.shrink(cost3);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
			}
			if (add3Stack.isItemEqual(gem1) && add3Stack.getCount() >= cost1) {
				if (((add2Stack.isItemEqual(gem3) && add2Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add1Stack.isItemEqual(gem2) && add1Stack.getCount() >= cost2) || gem2.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add3Stack.shrink(cost1);
						add1Stack.shrink(cost2);
						add2Stack.shrink(cost3);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add2Stack.isItemEqual(gem2) && add2Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add1Stack.isItemEqual(gem3) && add1Stack.getCount() >= cost3) || gem3.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add3Stack.shrink(cost1);
						add1Stack.shrink(cost3);
						add2Stack.shrink(cost2);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.world.consume((p_216973_2_, p_216973_3_) -> {
			this.clearContainer(playerIn, p_216973_2_, this.inputSlots);
		});
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn == this.inputSlots) {
			this.updateMatrix();
		}
		super.onCraftMatrixChanged(inventoryIn);
	}

	protected static void updateOutput(int windowId, World world, PlayerEntity player, ItemStack result, IInventory resultInv) {
		if (!world.isRemote) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;

			resultInv.setInventorySlotContents(0, result);
			serverplayerentity.connection.sendPacket(new SSetSlotPacket(windowId, 0, result));
		}
	}

	public void updateMatrix() {
		if (this.repairWands(false).isEmpty()) {
			this.world.consume((world, pos) -> {
				updateOutput(this.windowId, world, player, this.getConversionOutput(world, false), outputSlot);
			});
		} else {
			this.outputSlot.setInventorySlotContents(0, this.repairWands(false));
		}
	}

	public ItemStack repairWands(boolean attemptShrink) {
		if (this.inputSlots.getStackInSlot(0).getItem() instanceof AbstractMagicCastingItem) {
			ItemStack input = this.inputSlots.getStackInSlot(0);
			AbstractMagicCastingItem wand = (AbstractMagicCastingItem)input.getItem();
			ItemStack repairedStack = ItemStack.EMPTY;
			int[] inputs = new int[] {2,3,4};
			for (int i = 0; i < 3; i++) {
				if (this.inputSlots.getStackInSlot(inputs[i]).getItem() == CItems.PURPLE_MAGIC_STONE) {
					ItemStack input2 = this.inputSlots.getStackInSlot(inputs[i]);
					if (wand.getTier() == Rank.BASIC && input2.getCount() >= 1) {
						repairedStack = input.copy();
						if (attemptShrink) {
							input.shrink(1);
							input2.shrink(1);
						}
						break;
					}
					if (wand.getTier() == Rank.NOVICE && input2.getCount() >= 2) {
						repairedStack = input.copy();
						if (attemptShrink) {
							input.shrink(1);
							input2.shrink(2);
						}
						break;
					}
					if (wand.getTier() == Rank.APPRENTICE && input2.getCount() >= 3) {
						repairedStack = input.copy();
						if (attemptShrink) {
							input.shrink(1);
							input2.shrink(3);
						}
						break;
					}
					if (wand.getTier() == Rank.ADVANCED && input2.getCount() >= 4) {
						repairedStack = input.copy();
						if (attemptShrink) {
							input.shrink(1);
							input2.shrink(4);
						}
						break;
					}
					if (wand.getTier() == Rank.MASTER && input2.getCount() >= 5) {
						repairedStack = input.copy();
						if (attemptShrink) {
							input.shrink(1);
							input2.shrink(5);
						}
						break;
					}
				}
			}
			if (!repairedStack.isEmpty()) {
				repairedStack.setDamage(0);
				return repairedStack;
			}
		}
		return ItemStack.EMPTY;
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

	public void shrinkInventory(AbstractArcaneWorkbenchRecipe recipe, IInventory inv) {
		ItemStack tridox = new ItemStack(CItems.TRIDOX);
		ItemStack tridoxSlot = inv.getStackInSlot(1);
		ItemStack slot1 = inv.getStackInSlot(2);
		ItemStack slot2 = inv.getStackInSlot(3);
		ItemStack slot3 = inv.getStackInSlot(4);

		if (recipe.base.test(inv.getStackInSlot(0)) && (tridoxSlot.isItemEqual(tridox) && tridoxSlot.getCount() >= recipe.tridoxCost)) {
			if (recipe.addition1.test(slot1) && slot1.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot2) && slot2.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot3) && slot3.getCount() >= recipe.additionCost3))) {
					inv.getStackInSlot(0).shrink(1);
					slot1.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot2.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot3.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot2) && slot2.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot3) && slot3.getCount() >= recipe.additionCost2))) {
					inv.getStackInSlot(0).shrink(1);
					slot1.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot2.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot3.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
			if (recipe.addition1.test(slot2) && slot2.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot1) && slot1.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot3) && slot3.getCount() >= recipe.additionCost2))) {
					inv.getStackInSlot(0).shrink(1);
					slot2.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot1.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot3.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot1) && slot1.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot3) && slot3.getCount() >= recipe.additionCost3))) {
					inv.getStackInSlot(0).shrink(1);
					slot2.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot1.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot3.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
			if (recipe.addition1.test(slot3) && slot3.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot2) && slot2.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot1) && slot1.getCount() >= recipe.additionCost2))) {
					inv.getStackInSlot(0).shrink(1);
					slot3.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot1.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot2.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot2) && slot2.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot1) && slot1.getCount() >= recipe.additionCost3))) {
					inv.getStackInSlot(0).shrink(1);
					slot3.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot1.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot2.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
		}
	}

	public ItemStack getConversionOutput(World world, boolean shrink) {
		AbstractArcaneWorkbenchRecipe recipe = world.getRecipeManager().getRecipe(CRecipeType.ARCANE_CONVERSION, this.inputSlots, world).orElse(null);
		if (recipe != null) {
			if (!world.isRemote && shrink) {
				this.shrinkInventory(recipe, inputSlots);
			}
			return recipe.getCraftingResult(inputSlots);
		}
		else {
			ItemStack stack2 = ItemStack.EMPTY;
			//Wands
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.BASIC_STICK_WAND, CItems.NOVICE_STICK_WAND, CItems.APPRENTICE_STICK_WAND, CItems.ADVANCED_STICK_WAND, CItems.MASTER_STICK_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.BASIC_BLAZE_WAND, CItems.NOVICE_BLAZE_WAND, CItems.APPRENTICE_BLAZE_WAND, CItems.ADVANCED_BLAZE_WAND, CItems.MASTER_BLAZE_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.BASIC_GOLDEN_WAND, CItems.NOVICE_GOLDEN_WAND, CItems.APPRENTICE_GOLDEN_WAND, CItems.ADVANCED_GOLDEN_WAND, CItems.MASTER_GOLDEN_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.BASIC_SERABLE_WAND, CItems.NOVICE_SERABLE_WAND, CItems.APPRENTICE_SERABLE_WAND, CItems.ADVANCED_SERABLE_WAND, CItems.MASTER_SERABLE_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.BASIC_SHULKER_WAND, CItems.NOVICE_SHULKER_WAND, CItems.APPRENTICE_SHULKER_WAND, CItems.ADVANCED_SHULKER_WAND, CItems.MASTER_SHULKER_WAND, shrink);
			//Rings
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.AEROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(Items.PHANTOM_MEMBRANE), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.ELECTROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(CItems.COPPER_INGOT), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.HYDROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(Items.NAUTILUS_SHELL), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			//		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(CItems.PYROMANCER_RING), new ItemStack(CItems.GOLDEN_RING), new ItemStack(Items.BLAZE_POWDER), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			//		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(CItems.TERRAMANCER_RING), new ItemStack(CItems.GOLDEN_RING), new ItemStack(Items.EMERALD), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			return stack2;
		}
	}

	public ItemStack upgradeWands(Item basic, Item novice, Item apprentice, Item advanced, Item master, boolean shrink) {
		ItemStack stack = ItemStack.EMPTY;
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(novice), new ItemStack(basic), new ItemStack(Items.LAPIS_LAZULI), 10, new ItemStack(CItems.YELLOW_MAGIC_STONE), 16, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(apprentice), new ItemStack(novice), new ItemStack(Items.QUARTZ), 10, new ItemStack(CItems.YELLOW_MAGIC_STONE), 32, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(advanced), new ItemStack(apprentice), new ItemStack(CItems.PYRANITE), 10, new ItemStack(CItems.RED_MAGIC_STONE), 16, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(master), new ItemStack(advanced), new ItemStack(Items.DRAGON_BREATH), 10, new ItemStack(CItems.RED_MAGIC_STONE), 32, ItemStack.EMPTY, 0, 10, shrink);
		return stack;
	}
}