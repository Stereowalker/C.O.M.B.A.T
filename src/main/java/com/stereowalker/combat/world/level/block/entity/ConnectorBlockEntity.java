package com.stereowalker.combat.world.level.block.entity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ConnectorBlockEntity extends BlockEntity {
	private BlockPos connection;

	public ConnectorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.CONNECTOR, pWorldPosition, pBlockState);
	}

	public boolean requestEnergyFromConnector() {
		if (this.isConnected()) {
			if (this.getLevel().getBlockEntity(this.getConnection()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity connector = (ConnectorBlockEntity)this.getLevel().getBlockEntity(this.getConnection());
				return connector.removeEnergyFromGenerator();
			}
		}
		return false;
	}

	public boolean removeEnergyFromGenerator() {
		if (isConnectedToGenerator()) {
			AbstractEnergyGeneratorBlockEntity source = (AbstractEnergyGeneratorBlockEntity)this.getLevel().getBlockEntity(this.getBlockPos().below());
			if (!source.isDrained()) {
				source.extractEnergy(1, false);
				return true;
			}
		}
		return false;
	}

	public boolean putEnergyIntoConsumer() {
		if (isConnectedToConsumer()) {
			AbstractEnergyConsumerBlockEntity consumer = (AbstractEnergyConsumerBlockEntity)this.getLevel().getBlockEntity(this.getBlockPos().below());
			if (!consumer.isFull()) {
				consumer.receiveEnergy(1, false);
				return true;
			}
		}
		return false;
	}

	public boolean confirmEnergyFromConnector() {
		if (this.isConnected()) {
			if (this.getLevel().getBlockEntity(this.getConnection()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity connector = (ConnectorBlockEntity)this.getLevel().getBlockEntity(this.getConnection());
				return connector.confirmSourceHasEnergy();
			}
		}
		return false;
	}

	public boolean confirmSourceHasEnergy() {
		if (isConnectedToGenerator()) {
			AbstractEnergyGeneratorBlockEntity source = (AbstractEnergyGeneratorBlockEntity)this.getLevel().getBlockEntity(this.getBlockPos().below());
			return !source.isDrained();
		}
		return false;
	}

	public boolean confirmConsumerNeedsEnergy() {
		if (isConnectedToConsumer()) {
			AbstractEnergyConsumerBlockEntity consumer = (AbstractEnergyConsumerBlockEntity)this.getLevel().getBlockEntity(this.getBlockPos().below());
			return !consumer.isFull();
		}
		return false;
	}

	public boolean isConnectedToGenerator() {
		return this.getLevel().getBlockEntity(this.getBlockPos().below()) instanceof AbstractEnergyGeneratorBlockEntity;
	}

	public boolean isConnectedToConsumer() {
		return this.getLevel().getBlockEntity(this.getBlockPos().below()) instanceof AbstractEnergyConsumerBlockEntity;
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ConnectorBlockEntity pBlockEntity) {
		if (pBlockEntity.isConnected()) {
			if (pBlockEntity.getLevel().getBlockEntity(pBlockEntity.getConnection()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity generatorConnector = (ConnectorBlockEntity)pBlockEntity.getLevel().getBlockEntity(pBlockEntity.getConnection());
				if (pBlockEntity.isConnectedToConsumer() && generatorConnector.isConnectedToGenerator()) {
					if (generatorConnector.confirmSourceHasEnergy() && pBlockEntity.confirmConsumerNeedsEnergy()) {
						generatorConnector.removeEnergyFromGenerator();
						pBlockEntity.putEnergyIntoConsumer();
					}
				}
			} else {
				pBlockEntity.setConnection(null);
			}
		}
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.setConnection(new BlockPos(compound.getInt("conX"), compound.getInt("conY"), compound.getInt("conZ")));
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		if (isConnected()){
			compound.putInt("conX", this.getConnection().getX());
			compound.putInt("conY", this.getConnection().getY());
			compound.putInt("conZ", this.getConnection().getZ());
		}
		return super.save(compound);
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

	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, /*(double)this.getEyeHeight()*/0.4D, (double)(/*this.getBbWidth()*/5.0D * 0.4F));
	}

	public Vec3 getRopeHoldPosition() {
		return new Vec3(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()).add(0.0D, /*(double)this.eyeHeight*/0.4D * 0.7D, 0.0D);
	}

	public Vec3 getEyePosition(float pPartialTicks) {
		return new Vec3(this.getBlockPos().getX(), this.getBlockPos().getY() + 0.4F, this.getBlockPos().getZ());
		}
}
