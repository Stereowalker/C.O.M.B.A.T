package com.stereowalker.combat.tileentity;


import javax.annotation.Nullable;

import com.stereowalker.combat.block.PodiumBlock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

public class PodiumTileEntity extends TileEntity implements ITickableTileEntity {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;
	public int rotation;

	public PodiumTileEntity() {
		super(CTileEntityType.PODIUM);
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
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}

	public CompoundNBT saveToNbt(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, this.inventory, false);
		return compound;
	}
	
	public NonNullList<ItemStack> getInventory() {
		return inventory;
	}
	
	public ItemStack getItem() {
		return this.inventory.get(0);
	}

	protected void setItem(ItemStack itemsIn) {
		this.inventory.set(0, itemsIn);
	}
	
	

	public int getSizeInventory() {
		return 1;
	}

	@Override
	public void tick() {
		if (world.isBlockPowered(getPos())) {
			rotation++;
			if (rotation >= 360) {
				rotation = 0;
			}
		} else {
			if (world.getBlockState(getPos()).get(PodiumBlock.FACING).equals(Direction.NORTH)) {
				rotation = 180;
			} else if (world.getBlockState(getPos()).get(PodiumBlock.FACING).equals(Direction.EAST)) {
				rotation = 90;
			} else if (world.getBlockState(getPos()).get(PodiumBlock.FACING).equals(Direction.SOUTH)) {
				rotation = 0;
			} else if (world.getBlockState(getPos()).get(PodiumBlock.FACING).equals(Direction.WEST)) {
				rotation = 270;
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
		         InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
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
}