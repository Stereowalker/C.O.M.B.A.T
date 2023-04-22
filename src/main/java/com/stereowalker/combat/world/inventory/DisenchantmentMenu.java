package com.stereowalker.combat.world.inventory;

import java.util.Map;

import com.google.common.collect.Maps;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.EnchantmentTableBlock;

public class DisenchantmentMenu extends AbstractContainerMenu {
	private final Container outputSlot = new ResultContainer();
	private final Container inputSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			DisenchantmentMenu.this.slotsChanged(this);
		}
	};
	private final ContainerLevelAccess worldCallable;
	private int power = 0;
	private final DataSlot disecnhantCost = DataSlot.standalone();

	public DisenchantmentMenu(int id, Inventory player) {
		this(id, player, ContainerLevelAccess.NULL);
	}

	public DisenchantmentMenu(int id, Inventory p_i50102_2_, final ContainerLevelAccess p_i50102_3_) {
		super(CMenuType.DISENCHANTMENT, id);
		this.worldCallable = p_i50102_3_;
		this.addSlot(new Slot(this.inputSlots, 0, 27, 47) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() != Items.BOOK && (stack.isEnchanted() || stack.getItem() == Items.ENCHANTED_BOOK);
			}
		});
		this.addSlot(new Slot(this.inputSlots, 1, 76, 47) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.BOOK;
			}
		});
		this.addSlot(new Slot(this.outputSlot, 2, 134, 47) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
			
			@Override
			public boolean mayPickup(Player pPlayer) {
				return pPlayer.experienceLevel >= DisenchantmentMenu.this.disecnhantCost.get();
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onTake(Player thePlayer, ItemStack stack) {
				if (DisenchantmentMenu.this.inputSlots.getItem(0).getItem() == Items.ENCHANTED_BOOK && DisenchantmentMenu.this.inputSlots.getItem(1).getItem() == Items.BOOK) {
					ItemStack encBook = DisenchantmentMenu.this.inputSlots.getItem(0).copy();
					ListTag enchantList = EnchantedBookItem.getEnchantments(encBook);
					encBook.getOrCreateTag().put("StoredEnchantments", new ListTag());
					Map<Enchantment, Integer> enchMap = Maps.newHashMap();
					for (int i = 1; i < enchantList.size(); i++) {
						CompoundTag compoundnbt = enchantList.getCompound(i);
						BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(compoundnbt.getString("id"))).ifPresent((enc) -> {
							enchMap.put(enc, compoundnbt.getInt("lvl"));
						});
					}
					EnchantmentHelper.setEnchantments(enchMap, encBook);
					DisenchantmentMenu.this.inputSlots.setItem(0, encBook);
				} else
					DisenchantmentMenu.this.inputSlots.setItem(0, ItemStack.EMPTY);
				if (DisenchantmentMenu.this.inputSlots.getItem(1).getCount() > 1) {
					DisenchantmentMenu.this.inputSlots.getItem(1).shrink(1);
				} else
					DisenchantmentMenu.this.inputSlots.setItem(1, ItemStack.EMPTY);
				thePlayer.giveExperienceLevels(-DisenchantmentMenu.this.disecnhantCost.get());
				thePlayer.level.playSound((Player)null, thePlayer.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, thePlayer.level.random.nextFloat() * 0.1F + 0.9F);
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
		this.addDataSlot(this.disecnhantCost).set(0);
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(this.worldCallable, playerIn, CBlocks.DISENCHANTING_TABLE);
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		this.worldCallable.execute((p_216973_2_, p_216973_3_) -> {
			this.clearContainer(playerIn, this.inputSlots);
		});
	}

	@Override
	public void slotsChanged(Container inventoryIn) {
		if (inventoryIn == this.inputSlots) {
			this.worldCallable.execute((p_217002_2_, p_217002_3_) -> {
				float power = 0;

				for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
					if (EnchantmentTableBlock.isValidBookShelf(p_217002_2_, p_217002_3_, blockpos)) {
						power += p_217002_2_.getBlockState(p_217002_3_.offset(blockpos)).getEnchantPowerBonus(p_217002_2_, p_217002_3_.offset(blockpos));
					}
				}
				this.power = (int) power;
				this.disenchant();
			});
		}
		super.slotsChanged(inventoryIn);
	}
	@SuppressWarnings("deprecation")
	private void disenchant() {
		ItemStack input1 = this.inputSlots.getItem(0);
		ItemStack input2 = this.inputSlots.getItem(1);
		this.outputSlot.setItem(0, ItemStack.EMPTY);
		if (input1.isEnchanted() && input2.getItem() == Items.BOOK) {
			ListTag enchantList = input1.getEnchantmentTags();
			ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
			System.out.println("POWERZ "+power+" "+Mth.ceil((float)this.power/11.0f));
			int pow = Mth.ceil((float)this.power/10.0f) == 0 ? 1 : Mth.ceil((float)this.power/10.0f);
			for (int i = 0; i < pow; i++) {
				CompoundTag compoundnbt = enchantList.getCompound(i);
				BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(compoundnbt.getString("id"))).ifPresent((enc) -> {
					EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(enc, compoundnbt.getInt("lvl")));
				});
			}
			this.disecnhantCost.set(Math.min(pow, enchantList.size()));
			this.outputSlot.setItem(0, enchantedBook);
		}
		else if (input1.getItem() == Items.ENCHANTED_BOOK && input2.getItem() == Items.BOOK) {
			ListTag enchantList = EnchantedBookItem.getEnchantments(input1);
			if (enchantList.size() > 1) {
				CompoundTag compoundnbt = enchantList.getCompound(0);
				this.disecnhantCost.set(2);
				BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(compoundnbt.getString("id"))).ifPresent((enc) -> {
					this.outputSlot.setItem(0, EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enc, compoundnbt.getInt("lvl"))));
				});
			}
		}
		else {
			this.disecnhantCost.set(0);
		}
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 2) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 39 && !this.moveItemStackTo(itemstack1, 0, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
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
	
	public int getDisenchantCost() {
		return this.disecnhantCost.get();
	}
}