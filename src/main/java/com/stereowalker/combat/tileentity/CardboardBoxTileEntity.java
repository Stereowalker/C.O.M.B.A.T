package com.stereowalker.combat.tileentity;

import com.stereowalker.combat.inventory.container.CardboardBoxContainer;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CardboardBoxTileEntity extends LockableLootTileEntity implements INamedContainerProvider {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(10, ItemStack.EMPTY);
	protected String customName;

	public CardboardBoxTileEntity() {
		super(CTileEntityType.CARDBOARD_BOX);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
	}

	public boolean isEmpty() {
		for(ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return super.isUsableByPlayer(player);
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		this.loadFromNbt(compound);
	}

	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		return this.saveToNbt(compound);
	}

	public void loadFromNbt(CompoundNBT compound) {
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound) && compound.contains("Items", 9)) {
			ItemStackHelper.loadAllItems(compound, this.inventory);
		}

	}

	public CompoundNBT saveToNbt(CompoundNBT compound) {
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.inventory, false);
		}

		return compound;
	}

	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		this.fillWithLoot(playerInventory.player);
		return new CardboardBoxContainer(id, this, playerInventory);
	}

	public String getNameString() {
		return "cardboard_box";
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container." + getNameString());
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(this.hasCustomName() ? this.customName : "container.cardboard_box");
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.inventory = itemsIn;
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}
}