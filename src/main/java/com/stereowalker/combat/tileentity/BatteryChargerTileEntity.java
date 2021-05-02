package com.stereowalker.combat.tileentity;


import javax.annotation.Nullable;

import com.stereowalker.combat.inventory.container.BatteryChargerContainer;
import com.stereowalker.combat.item.LightSaberItem;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BatteryChargerTileEntity  extends AbstractEnergyConsumerTileEntity implements INamedContainerProvider, ITickableTileEntity {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;

	public BatteryChargerTileEntity() {
		super(CTileEntityType.BATTERY_CHARGER, 1000);
	}

	public boolean isEmpty() {
		for(ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	protected final IIntArray data = new IIntArray() {
		public int get(int index) {
			switch(index) {
			case 0:
				return BatteryChargerTileEntity.this.energy;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				BatteryChargerTileEntity.this.energy = value;
			}

		}

		public int size() {
			return 1;
		}
	};

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, this.inventory);
		return super.write(compound);
	}

	public ItemStack getItem() {
		return this.inventory.get(0);
	}

	protected void setItem(ItemStack itemsIn) {
		this.inventory.set(0, itemsIn);
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public void tick() {
		if (!this.world.isRemote) {
			if (!this.isDrained()) {
				if (inventory.get(0).getItem() instanceof LightSaberItem) {
					if (EnergyUtils.getEnergy(inventory.get(0), EnergyType.TECHNO_ENERGY) < EnergyUtils.getMaxEnergy(inventory.get(0), EnergyType.TECHNO_ENERGY)) {
						EnergyUtils.addEnergyToItem(inventory.get(0), 1, EnergyType.TECHNO_ENERGY);
						energy--;
						this.markDirty();
					}
				} else {
					this.setEnergy(getEnergyStored());
				}
			}
		}
	}

	public boolean addItem(ItemStack itemStackIn) {
		for(int i = 0; i < this.inventory.size(); ++i) {
			ItemStack itemstack = this.inventory.get(i);
			if (itemstack.isEmpty()) {
				this.inventory.set(i, itemStackIn.split(1));
				this.inventoryChanged();
				return true;
			}
		}
		return false;
	}

	public boolean removeItem(PlayerEntity player) {
		if (player != null) {
			player.addItemStackToInventory(this.inventory.get(0));
			this.inventoryChanged();
			return true;
		} else {
			if (!this.getWorld().isRemote) {
				InventoryHelper.dropItems(this.getWorld(), this.getPos(), inventory);
			}
			this.inventoryChanged();
			return true;
		}
	}

	private void inventoryChanged() {
		this.markDirty();
		this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	 * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	 */
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	 */
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		this.read(this.getBlockState(), pkt.getNbtCompound());
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.battery_charger");
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(this.hasCustomName() ? this.customName : "container.battery_charger");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new BatteryChargerContainer(id, this, player, data);
	}
}