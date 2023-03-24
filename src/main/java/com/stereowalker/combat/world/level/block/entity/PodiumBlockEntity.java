package com.stereowalker.combat.world.level.block.entity;


import javax.annotation.Nullable;

import com.stereowalker.combat.world.level.block.PodiumBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PodiumBlockEntity extends BlockEntity implements Clearable {
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	protected String customName;
	public int rotation;

	public PodiumBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.PODIUM, pWorldPosition, pBlockState);
	}

//	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		this.items.clear();
		ContainerHelper.loadAllItems(pTag, this.items);
	}

	@Override
	public void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		ContainerHelper.saveAllItems(pTag, this.items, false);
	}

	public NonNullList<ItemStack> getItems() {
		return items;
	}

	public ItemStack getItem() {
		return this.items.get(0);
	}

	protected void setItem(ItemStack itemsIn) {
		this.items.set(0, itemsIn);
	}

//	@Override
//	public int getContainerSize() {
//		return 1;
//	}

	public static void spinTick(Level pLevel, BlockPos pPos, BlockState pState, PodiumBlockEntity pBlockEntity) {
		if (pLevel.hasNeighborSignal(pPos)) {
			pBlockEntity.rotation++;
			if (pBlockEntity.rotation >= 360) {
				pBlockEntity.rotation = 0;
			}
		} else {
			if (pLevel.getBlockState(pPos).getValue(PodiumBlock.FACING).equals(Direction.NORTH)) {
				pBlockEntity.rotation = 180;
			} else if (pLevel.getBlockState(pPos).getValue(PodiumBlock.FACING).equals(Direction.EAST)) {
				pBlockEntity.rotation = 90;
			} else if (pLevel.getBlockState(pPos).getValue(PodiumBlock.FACING).equals(Direction.SOUTH)) {
				pBlockEntity.rotation = 0;
			} else if (pLevel.getBlockState(pPos).getValue(PodiumBlock.FACING).equals(Direction.WEST)) {
				pBlockEntity.rotation = 270;
			}
		}
	}

	public boolean addItem(ItemStack itemStackIn) {
		for(int i = 0; i < this.items.size(); ++i) {
			ItemStack itemstack = this.items.get(i);
			if (itemstack.isEmpty()) {
				this.items.set(i, itemStackIn.split(1));
				this.markUpdated();
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("resource")
	public boolean removeItem(Player player) {
		if (player != null) {
			player.addItem(this.items.get(0));
			this.markUpdated();
			return true;
		} else {
			if (!this.getLevel().isClientSide) {
				Containers.dropContents(this.getLevel(), this.getBlockPos(), this.items);
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
	public void clearContent() {
		this.items.clear();
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	 * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	 */
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag compound = new CompoundTag();
		this.saveAdditional(new CompoundTag());
		return compound;
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(pkt.getTag());
	}
}