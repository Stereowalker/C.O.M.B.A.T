package com.stereowalker.combat.world.level.block.entity;

import com.stereowalker.combat.world.inventory.CardboardBoxMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CardboardBoxBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(10, ItemStack.EMPTY);
	protected String customName;

	public CardboardBoxBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.CARDBOARD_BOX, pWorldPosition, pBlockState);
	}

	@Override
	public AABB getRenderBoundingBox() {
		return new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
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
	public boolean stillValid(Player player) {
		return super.stillValid(player);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(compound) && compound.contains("Items", 9)) {
			ContainerHelper.loadAllItems(compound, this.inventory);
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound = saveToTag(compound);
	}
	
	public CompoundTag saveToTag(CompoundTag compound) {
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.inventory, false);
		}
		return compound;
	}
	
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
		this.unpackLootTable(playerInventory.player);
		return new CardboardBoxMenu(id, this, playerInventory);
	}

	public String getNameString() {
		return "cardboard_box";
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("container." + getNameString());
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable(this.hasCustomName() ? this.customName : "container.cardboard_box");
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.inventory = itemsIn;
	}

	@Override
	public int getContainerSize() {
		return 10;
	}
}