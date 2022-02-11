package com.stereowalker.combat.world.level.block.entity;


import javax.annotation.Nullable;

import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.inventory.BatteryChargerMenu;
import com.stereowalker.combat.world.item.Mythril;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MythrilChargerBlockEntity  extends AbstractEnergyConsumerBlockEntity implements MenuProvider {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;

	public MythrilChargerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.MYTHRIL_CHARGER, pWorldPosition, pBlockState, 1000);
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	protected final ContainerData data = new ContainerData() {
		public int get(int index) {
			switch(index) {
			case 0:
				return MythrilChargerBlockEntity.this.energy;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				MythrilChargerBlockEntity.this.energy = value;
			}

		}

		public int getCount() {
			return 1;
		}
	};

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		ContainerHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		ContainerHelper.saveAllItems(compound, this.inventory);
		return super.save(compound);
	}

	public ItemStack getItem() {
		return this.inventory.get(0);
	}

	protected void setItem(ItemStack itemsIn) {
		this.inventory.set(0, itemsIn);
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MythrilChargerBlockEntity pBlockEntity) {
		if (!pLevel.isClientSide) {
			ItemStack stack = pBlockEntity.inventory.get(0);
			if (!pBlockEntity.isDrained()) {
				if (stack.getItem() instanceof Mythril) {
					if (!EnergyUtils.isFull(pBlockEntity.inventory.get(0), EnergyUtils.EnergyType.DIVINE_ENERGY)) {
						EnergyUtils.addEnergyToItem(pBlockEntity.inventory.get(0), 1, EnergyUtils.EnergyType.DIVINE_ENERGY);
						pBlockEntity.energy--;
						pBlockEntity.setChanged();
					}
				} else {
					pBlockEntity.setEnergy(pBlockEntity.getEnergyStored());
				}
			}
		}
	}

	public boolean addItem(ItemStack itemStackIn) {
		for(int i = 0; i < this.inventory.size(); ++i) {
			ItemStack itemstack = this.inventory.get(i);
			if (itemstack.isEmpty()) {
				this.inventory.set(i, itemStackIn.split(1));
				this.markUpdated();
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("resource")
	public boolean removeItem(Player player) {
		if (player != null) {
			player.addItem(this.inventory.get(0));
			this.markUpdated();
			return true;
		} else {
			if (!this.getLevel().isClientSide) {
				Containers.dropContents(this.getLevel(), this.getBlockPos(), inventory);
			}
			this.markUpdated();
			return true;
		}
	}

	private void markUpdated() {
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 13, this.getUpdateTag());
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(pkt.getTag());
	}

	@Override
	public ItemStack getItem(int index) {
		return this.inventory.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.inventory, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.inventory, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		this.inventory.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.getLevel().getBlockEntity(this.getBlockPos()) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 0.5D, (double)this.getBlockPos().getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clearContent() {
		this.inventory.clear();
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("container.battery_charger");
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent(this.hasCustomName() ? this.customName : "container.battery_charger");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new BatteryChargerMenu(id, this, player, data);
	}
}