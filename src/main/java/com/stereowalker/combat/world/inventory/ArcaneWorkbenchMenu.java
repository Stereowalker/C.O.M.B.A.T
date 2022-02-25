package com.stereowalker.combat.world.inventory;


import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.crafting.AbstractArcaneWorkbenchRecipe;
import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.unionlib.item.UItems;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu {
	private final Container inputSlots = new SimpleContainer(5) {
		@Override
		public void setChanged() {
			super.setChanged();
			ArcaneWorkbenchMenu.this.slotsChanged(this);
		}
	};
	private final ContainerLevelAccess world;
	private final Container outputSlot = new ResultContainer();
	private final Player player;

	public ArcaneWorkbenchMenu(int id, Inventory playerInventory) {
		this(id, playerInventory, ContainerLevelAccess.NULL);
	}

	public ArcaneWorkbenchMenu(int id, Inventory playerInventory, ContainerLevelAccess worldIn) {
		super(CMenuType.ARCANE_WORKBENCH, id);
		this.world = worldIn;
		this.player = playerInventory.player;
		this.addSlot(new Slot(outputSlot, 0, 129, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			public void onTake(Player thePlayer, ItemStack stack) {
				if (ArcaneWorkbenchMenu.this.repairWands(false).isEmpty()) {
					ArcaneWorkbenchMenu.this.world.execute((world, pos) -> {
						ArcaneWorkbenchMenu.this.getConversionOutput(world, true);
					});
				} else {
					ArcaneWorkbenchMenu.this.repairWands(true);
				}
				ArcaneWorkbenchMenu.this.slotsChanged(inputSlots);
			}
		});
		this.addSlot(new Slot(inputSlots, 0, 58, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		//Tridox Slot
		this.addSlot(new Slot(inputSlots, 1, 33, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 2, 58, 10) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 3, 83, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() != CItems.TRIDOX;
			}
		});
		this.addSlot(new Slot(inputSlots, 4, 58, 60) {
			@Override
			public boolean mayPlace(ItemStack stack) {
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

	@Override
	public boolean stillValid(Player playerIn) {
		return this.inputSlots.stillValid(playerIn);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 0) {
				this.world.execute((world, pos) -> {
					itemstack1.getItem().onCraftedBy(itemstack1, world, playerIn);
				});
				if (!this.moveItemStackTo(itemstack1, 6, 42, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index >= 6 && index < 42) {
				if (!this.moveItemStackTo(itemstack1, 1, 6, false)) {
					if (index < 33) {
						if (!this.moveItemStackTo(itemstack1, 33, 42, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.moveItemStackTo(itemstack1, 6, 33, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemstack1, 6, 42, false)) {
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
	public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
		super.clicked(slotId, dragType, clickTypeIn, player);
	}

	public ItemStack mergeItem(ItemStack result, ItemStack mainItem, ItemStack gem1, int cost1, ItemStack gem2, int cost2, ItemStack gem3, int cost3, int tridoxCost, boolean attemptShrink) {
		ItemStack tridox = new ItemStack(CItems.TRIDOX);
		ItemStack baseStack = this.inputSlots.getItem(0);
		ItemStack tridoxStack = this.inputSlots.getItem(1);

		ItemStack add1Stack = this.inputSlots.getItem(2);
		ItemStack add2Stack = this.inputSlots.getItem(3);
		ItemStack add3Stack = this.inputSlots.getItem(4);
		if (baseStack.sameItem(mainItem) && (tridoxStack.sameItem(tridox) && tridoxStack.getCount() >= tridoxCost)) {
			if (add1Stack.sameItem(gem1) && add1Stack.getCount() >= cost1) {
				if (((add2Stack.sameItem(gem2) && add2Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add3Stack.sameItem(gem3) && add3Stack.getCount() >= cost3) || gem3.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add1Stack.shrink(cost1);
						add2Stack.shrink(cost2);
						add3Stack.shrink(cost3);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add2Stack.sameItem(gem3) && add2Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add3Stack.sameItem(gem2) && add3Stack.getCount() >= cost2) || gem2.isEmpty())) {
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
			if (add2Stack.sameItem(gem1) && add2Stack.getCount() >= cost1) {
				if (((add1Stack.sameItem(gem3) && add1Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add3Stack.sameItem(gem2) && add3Stack.getCount() >= cost2) || gem2.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add2Stack.shrink(cost1);
						add1Stack.shrink(cost3);
						add3Stack.shrink(cost2);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add1Stack.sameItem(gem2) && add1Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add3Stack.sameItem(gem3) && add3Stack.getCount() >= cost3) || gem3.isEmpty())) {
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
			if (add3Stack.sameItem(gem1) && add3Stack.getCount() >= cost1) {
				if (((add2Stack.sameItem(gem3) && add2Stack.getCount() >= cost3) || gem3.isEmpty()) && ((add1Stack.sameItem(gem2) && add1Stack.getCount() >= cost2) || gem2.isEmpty())) {
					if (attemptShrink) {
						baseStack.shrink(1);
						add3Stack.shrink(cost1);
						add1Stack.shrink(cost2);
						add2Stack.shrink(cost3);
						tridoxStack.shrink(tridoxCost);
					}
					return result;
				}
				if (((add2Stack.sameItem(gem2) && add2Stack.getCount() >= cost2) || gem2.isEmpty()) && ((add1Stack.sameItem(gem3) && add1Stack.getCount() >= cost3) || gem3.isEmpty())) {
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

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		this.world.execute((p_216973_2_, p_216973_3_) -> {
			this.clearContainer(playerIn, this.inputSlots);
		});
	}

	@Override
	public void slotsChanged(Container inventoryIn) {
		if (inventoryIn == this.inputSlots) {
			this.updateMatrix();
		}
		super.slotsChanged(inventoryIn);
	}

	protected static void updateOutput(AbstractContainerMenu menu, Level world, Player player, ItemStack result, Container resultInv) {
		if (!world.isClientSide) {
			ServerPlayer serverplayerentity = (ServerPlayer)player;

			resultInv.setItem(0, result);
			serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, result));
		}
	}

	public void updateMatrix() {
		if (this.repairWands(false).isEmpty()) {
			this.world.execute((world, pos) -> {
				updateOutput(this, world, player, this.getConversionOutput(world, false), outputSlot);
			});
		} else {
			this.outputSlot.setItem(0, this.repairWands(false));
		}
	}

	public ItemStack repairWands(boolean attemptShrink) {
		if (this.inputSlots.getItem(0).getItem() instanceof AbstractMagicCastingItem) {
			ItemStack input = this.inputSlots.getItem(0);
			AbstractMagicCastingItem wand = (AbstractMagicCastingItem)input.getItem();
			ItemStack repairedStack = ItemStack.EMPTY;
			int[] inputs = new int[] {2,3,4};
			for (int i = 0; i < 3; i++) {
				if (this.inputSlots.getItem(inputs[i]).getItem() == CItems.PURPLE_MAGIC_STONE) {
					ItemStack input2 = this.inputSlots.getItem(inputs[i]);
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
				repairedStack.setDamageValue(0);
				return repairedStack;
			}
		}
		return ItemStack.EMPTY;
	}

//	protected static void craft(AbstractContainerMenu menu, Level world, Player player, CraftingContainer inv, ResultContainer resultInv) {
//		if (!world.isClientSide) {
//			ServerPlayer serverplayerentity = (ServerPlayer)player;
//			ItemStack itemstack = ItemStack.EMPTY;
//			Optional<FletchingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(CRecipeType.FLETCHING, inv, world);
//			if (optional.isPresent()) {
//				FletchingRecipe icraftingrecipe = optional.get();
//				if (resultInv.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) {
//					itemstack = icraftingrecipe.assemble(inv);
//				}
//			}
//
//			resultInv.setItem(0, itemstack);
//			serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));
//		}
//	}

	public void shrinkInventory(AbstractArcaneWorkbenchRecipe recipe, Container inv) {
		ItemStack tridox = new ItemStack(CItems.TRIDOX);
		ItemStack tridoxSlot = inv.getItem(1);
		ItemStack slot1 = inv.getItem(2);
		ItemStack slot2 = inv.getItem(3);
		ItemStack slot3 = inv.getItem(4);

		if (recipe.base.test(inv.getItem(0)) && (tridoxSlot.sameItem(tridox) && tridoxSlot.getCount() >= recipe.tridoxCost)) {
			if (recipe.addition1.test(slot1) && slot1.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot2) && slot2.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot3) && slot3.getCount() >= recipe.additionCost3))) {
					inv.getItem(0).shrink(1);
					slot1.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot2.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot3.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot2) && slot2.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot3) && slot3.getCount() >= recipe.additionCost2))) {
					inv.getItem(0).shrink(1);
					slot1.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot2.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot3.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
			if (recipe.addition1.test(slot2) && slot2.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot1) && slot1.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot3) && slot3.getCount() >= recipe.additionCost2))) {
					inv.getItem(0).shrink(1);
					slot2.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot1.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot3.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot1) && slot1.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot3) && slot3.getCount() >= recipe.additionCost3))) {
					inv.getItem(0).shrink(1);
					slot2.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot1.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot3.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
			if (recipe.addition1.test(slot3) && slot3.getCount() >= recipe.additionCost1) {
				if ((!recipe.hasAddition3() || (recipe.addition3().test(slot2) && slot2.getCount() >= recipe.additionCost3)) && (!recipe.hasAddition2() || (recipe.addition2().test(slot1) && slot1.getCount() >= recipe.additionCost2))) {
					inv.getItem(0).shrink(1);
					slot3.shrink(recipe.additionCost1);
					if (recipe.hasAddition2())slot1.shrink(recipe.additionCost2);
					if (recipe.hasAddition3())slot2.shrink(recipe.additionCost3);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
				if ((!recipe.hasAddition2() || (recipe.addition2().test(slot2) && slot2.getCount() >= recipe.additionCost2)) && (!recipe.hasAddition3() || (recipe.addition3().test(slot1) && slot1.getCount() >= recipe.additionCost3))) {
					inv.getItem(0).shrink(1);
					slot3.shrink(recipe.additionCost1);
					if (recipe.hasAddition3())slot1.shrink(recipe.additionCost3);
					if (recipe.hasAddition2())slot2.shrink(recipe.additionCost2);
					tridoxSlot.shrink(recipe.tridoxCost);
					return;
				}
			}
		}
	}

	public ItemStack getConversionOutput(Level world, boolean shrink) {
		AbstractArcaneWorkbenchRecipe recipe = world.getRecipeManager().getRecipeFor(CRecipeType.ARCANE_CONVERSION, this.inputSlots, world).orElse(null);
		if (recipe != null) {
			if (!world.isClientSide && shrink) {
				this.shrinkInventory(recipe, inputSlots);
			}
			return recipe.assemble(inputSlots);
		}
		else {
			ItemStack stack2 = ItemStack.EMPTY;
			//Wands
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.NOVICE_STICK_WAND, CItems.APPRENTICE_STICK_WAND, CItems.ADVANCED_STICK_WAND, CItems.MASTER_STICK_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.NOVICE_BLAZE_WAND, CItems.APPRENTICE_BLAZE_WAND, CItems.ADVANCED_BLAZE_WAND, CItems.MASTER_BLAZE_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.NOVICE_GOLDEN_WAND, CItems.APPRENTICE_GOLDEN_WAND, CItems.ADVANCED_GOLDEN_WAND, CItems.MASTER_GOLDEN_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.NOVICE_SERABLE_WAND, CItems.APPRENTICE_SERABLE_WAND, CItems.ADVANCED_SERABLE_WAND, CItems.MASTER_SERABLE_WAND, shrink);
			if (stack2.isEmpty()) stack2 = this.upgradeWands(CItems.NOVICE_SHULKER_WAND, CItems.APPRENTICE_SHULKER_WAND, CItems.ADVANCED_SHULKER_WAND, CItems.MASTER_SHULKER_WAND, shrink);
			//Rings
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.AEROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(Items.PHANTOM_MEMBRANE), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.ELECTROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(CItems.COPPER_INGOT), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			if (stack2.isEmpty()) stack2 = this.mergeItem(new ItemStack(CItems.HYDROMANCER_RING), new ItemStack(UItems.GOLDEN_RING), new ItemStack(Items.NAUTILUS_SHELL), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			//		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(CItems.PYROMANCER_RING), new ItemStack(CItems.GOLDEN_RING), new ItemStack(Items.BLAZE_POWDER), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			//		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(CItems.TERRAMANCER_RING), new ItemStack(CItems.GOLDEN_RING), new ItemStack(Items.EMERALD), 10, new ItemStack(Items.NETHER_WART), 10, ItemStack.EMPTY, 0, 16, shrink);
			return stack2;
		}
	}

	public ItemStack upgradeWands(Item novice, Item apprentice, Item advanced, Item master, boolean shrink) {
		ItemStack stack = ItemStack.EMPTY;
//		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(novice), new ItemStack(basic), new ItemStack(Items.LAPIS_LAZULI), 10, new ItemStack(CItems.YELLOW_MAGIC_STONE), 16, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(apprentice), new ItemStack(novice), new ItemStack(Items.QUARTZ), 10, new ItemStack(CItems.YELLOW_MAGIC_STONE), 32, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(advanced), new ItemStack(apprentice), new ItemStack(CItems.PYRANITE), 10, new ItemStack(CItems.RED_MAGIC_STONE), 16, ItemStack.EMPTY, 0, 10, shrink);
		if (stack.isEmpty()) stack = this.mergeItem(new ItemStack(master), new ItemStack(advanced), new ItemStack(Items.DRAGON_BREATH), 10, new ItemStack(CItems.RED_MAGIC_STONE), 32, ItemStack.EMPTY, 0, 10, shrink);
		return stack;
	}
}