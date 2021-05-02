package com.stereowalker.combat.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ConnectorTileEntity extends TileEntity implements ITickableTileEntity {
	private BlockPos connection;

	public ConnectorTileEntity() {
		super(CTileEntityType.CONNECTOR);
	}

	public boolean requestEnergyFromConnector() {
		if (this.isConnected()) {
			if (this.world.getTileEntity(this.getConnection()) instanceof ConnectorTileEntity) {
				ConnectorTileEntity connector = (ConnectorTileEntity)this.world.getTileEntity(this.getConnection());
				return connector.removeEnergyFromGenerator();
			}
		}
		return false;
	}

	public boolean removeEnergyFromGenerator() {
		if (isConnectedToGenerator()) {
			AbstractEnergyGeneratorTileEntity source = (AbstractEnergyGeneratorTileEntity)this.world.getTileEntity(this.pos.down());
			if (!source.isDrained()) {
				source.extractEnergy(1, false);
				return true;
			}
		}
		return false;
	}
	
	public boolean putEnergyIntoConsumer() {
		if (isConnectedToConsumer()) {
			AbstractEnergyConsumerTileEntity consumer = (AbstractEnergyConsumerTileEntity)this.world.getTileEntity(this.pos.down());
			if (!consumer.isFull()) {
				consumer.receiveEnergy(1, false);
				return true;
			}
		}
		return false;
	}
	
	public boolean confirmEnergyFromConnector() {
		if (this.isConnected()) {
			if (this.world.getTileEntity(this.getConnection()) instanceof ConnectorTileEntity) {
				ConnectorTileEntity connector = (ConnectorTileEntity)this.world.getTileEntity(this.getConnection());
				return connector.confirmSourceHasEnergy();
			}
		}
		return false;
	}

	public boolean confirmSourceHasEnergy() {
		if (isConnectedToGenerator()) {
			AbstractEnergyGeneratorTileEntity source = (AbstractEnergyGeneratorTileEntity)this.world.getTileEntity(this.pos.down());
			return !source.isDrained();
		}
		return false;
	}
	
	public boolean confirmConsumerNeedsEnergy() {
		if (isConnectedToConsumer()) {
			AbstractEnergyConsumerTileEntity consumer = (AbstractEnergyConsumerTileEntity)this.world.getTileEntity(this.pos.down());
			return !consumer.isFull();
		}
		return false;
	}
	
	public boolean isConnectedToGenerator() {
		return this.world.getTileEntity(this.pos.down()) instanceof AbstractEnergyGeneratorTileEntity;
	}
	
	public boolean isConnectedToConsumer() {
		return this.world.getTileEntity(this.pos.down()) instanceof AbstractEnergyConsumerTileEntity;
	}

	@Override
	public void tick() {
		if (this.isConnected()) {
			if (this.world.getTileEntity(this.getConnection()) instanceof ConnectorTileEntity) {
				ConnectorTileEntity generatorConnector = (ConnectorTileEntity)this.world.getTileEntity(this.getConnection());
				if (this.isConnectedToConsumer() && generatorConnector.isConnectedToGenerator()) {
					if (generatorConnector.confirmSourceHasEnergy() && this.confirmConsumerNeedsEnergy()) {
						generatorConnector.removeEnergyFromGenerator();
						this.putEnergyIntoConsumer();
					}
				}
			} else {
				this.setConnection(null);
			}
		}
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		this.setConnection(new BlockPos(compound.getInt("conX"), compound.getInt("conY"), compound.getInt("conZ")));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		if (isConnected()){
			compound.putInt("conX", this.getConnection().getX());
			compound.putInt("conY", this.getConnection().getY());
			compound.putInt("conZ", this.getConnection().getZ());
		}
		return super.write(compound);
	}

	@Nullable
	public BlockPos connection() {
		return this.getConnection();
	}

	public void setConnection(BlockPos connection) {
		this.connection = connection;
	}

	public boolean isConnected() {
		return !(this.getConnection() == null);
	}

	public BlockPos getConnection() {
		return connection;
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
