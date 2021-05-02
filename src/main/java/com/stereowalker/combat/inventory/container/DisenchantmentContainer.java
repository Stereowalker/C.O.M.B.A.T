package com.stereowalker.combat.inventory.container;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class DisenchantmentContainer extends Container {
	private final IInventory outputSlot = new CraftResultInventory();
	private final IInventory inputSlots = new Inventory(2) {
		/**
		 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
		 * it hasn't changed and skip it.
		 */
		public void markDirty() {
			super.markDirty();
			DisenchantmentContainer.this.onCraftMatrixChanged(this);
		}
	};
	private final IWorldPosCallable worldCallable;
	private int power = 0;

	public DisenchantmentContainer(int id, PlayerInventory player) {
		this(id, player, IWorldPosCallable.DUMMY);
	}

	public DisenchantmentContainer(int id, PlayerInventory p_i50102_2_, final IWorldPosCallable p_i50102_3_) {
		super(CContainerType.DISENCHANTMENT, id);
		this.worldCallable = p_i50102_3_;
		this.addSlot(new Slot(this.inputSlots, 0, 27, 47) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() != Items.BOOK && (stack.isEnchanted() || stack.getItem() == Items.ENCHANTED_BOOK);
			}
		});
		this.addSlot(new Slot(this.inputSlots, 1, 76, 47) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BOOK;
			}
		});
		this.addSlot(new Slot(this.outputSlot, 2, 134, 47) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			/**
			 * Return whether this slot's stack can be taken from this slot.
			 */
			//			public boolean canTakeStack(PlayerEntity playerIn) {
			//				return (playerIn.abilities.isCreativeMode || playerIn.experienceLevel >= RepairContainer.this.maximumCost.get()) && RepairContainer.this.maximumCost.get() > 0 && this.getHasStack();
			//			}
			//
			@SuppressWarnings("deprecation")
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				if (DisenchantmentContainer.this.inputSlots.getStackInSlot(0).getItem() == Items.ENCHANTED_BOOK && DisenchantmentContainer.this.inputSlots.getStackInSlot(1).getItem() == Items.BOOK) {
					ItemStack encBook = new ItemStack(Items.ENCHANTED_BOOK);
					ListNBT enchantList = EnchantedBookItem.getEnchantments(DisenchantmentContainer.this.inputSlots.getStackInSlot(0));
					Map<Enchantment, Integer> enchMap = Maps.newHashMap();
					for (int i = 1; i < enchantList.size(); i++) {
						CompoundNBT compoundnbt = enchantList.getCompound(i);
						Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundnbt.getString("id"))).ifPresent((enc) -> {
							enchMap.put(enc, compoundnbt.getInt("lvl"));
						});
					}
					EnchantmentHelper.setEnchantments(enchMap, encBook);
					DisenchantmentContainer.this.inputSlots.setInventorySlotContents(0, encBook);
				} else {
					DisenchantmentContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
				}
				if (DisenchantmentContainer.this.inputSlots.getStackInSlot(1).getCount() > 1) {
					DisenchantmentContainer.this.inputSlots.getStackInSlot(1).shrink(1);
				} else {
					DisenchantmentContainer.this.inputSlots.setInventorySlotContents(1, ItemStack.EMPTY);
				}
				return stack;
			}
		});

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(p_i50102_2_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(p_i50102_2_, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.worldCallable.applyOrElse((p_216979_1_, p_216979_2_) -> {
			return playerIn.getDistanceSq((double)p_216979_2_.getX() + 0.5D, (double)p_216979_2_.getY() + 0.5D, (double)p_216979_2_.getZ() + 0.5D) <= 64.0D;
		}, true);
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.worldCallable.consume((p_216973_2_, p_216973_3_) -> {
			this.clearContainer(playerIn, p_216973_2_, this.inputSlots);
		});
	}

	private float getPower(net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos) {
		return world.getBlockState(pos).getEnchantPowerBonus(world, pos);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn == this.inputSlots) {
			this.worldCallable.consume((p_217002_2_, p_217002_3_) -> {
				float power = 0;

				for(int k = -1; k <= 1; ++k) {
					for(int l = -1; l <= 1; ++l) {
						if ((k != 0 || l != 0) && p_217002_2_.isAirBlock(p_217002_3_.add(l, 0, k)) && p_217002_2_.isAirBlock(p_217002_3_.add(l, 1, k))) {
							power += getPower(p_217002_2_, p_217002_3_.add(l * 2, 0, k * 2));
							power += getPower(p_217002_2_, p_217002_3_.add(l * 2, 1, k * 2));

							if (l != 0 && k != 0) {
								power += getPower(p_217002_2_, p_217002_3_.add(l * 2, 0, k));
								power += getPower(p_217002_2_, p_217002_3_.add(l * 2, 1, k));
								power += getPower(p_217002_2_, p_217002_3_.add(l, 0, k * 2));
								power += getPower(p_217002_2_, p_217002_3_.add(l, 1, k * 2));
							}
						}
					}
				}
				this.power = (int) power;
			});
			this.disenchant();
		}
		super.onCraftMatrixChanged(inventoryIn);
	}
	@SuppressWarnings("deprecation")
	private void disenchant() {
		ItemStack input1 = this.inputSlots.getStackInSlot(0);
		ItemStack input2 = this.inputSlots.getStackInSlot(1);
		this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
		if (input1.isEnchanted() && input2.getItem() == Items.BOOK) {
			ListNBT enchantList = input1.getEnchantmentTagList();
			ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
			int pow = MathHelper.ceil(this.power/10) == 0 ? 1 : MathHelper.ceil(this.power/10);
			for (int i = 0; i < pow; i++) {
				CompoundNBT compoundnbt = enchantList.getCompound(i);
				Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundnbt.getString("id"))).ifPresent((enc) -> {
					EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentData(enc, compoundnbt.getInt("lvl")));
				});
			}
			this.outputSlot.setInventorySlotContents(0, enchantedBook);
		}
		if (input1.getItem() == Items.ENCHANTED_BOOK && input2.getItem() == Items.BOOK) {
			//			EnchantedBookItem book = (EnchantedBookItem)input1.getItem();
			//			EnchantedBookItem.getEnchantments(input1);
			ListNBT enchantList = EnchantedBookItem.getEnchantments(input1);
			CompoundNBT compoundnbt = enchantList.getCompound(0);
			Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundnbt.getString("id"))).ifPresent((enc) -> {
				//	        	 enchMap.put(enc, compoundnbt.getInt("lvl"));
				this.outputSlot.setInventorySlotContents(0, EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enc, compoundnbt.getInt("lvl"))));
			});
		}
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
			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 39 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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
}