package com.stereowalker.combat.world.level.block.entity;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.item.WireItem;
import com.stereowalker.combat.world.item.WireItem.Type;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ConnectorBlockEntity extends BlockEntity {
	private BlockPos connection;
	private WireItem.Type cable;
	private boolean wasFirst;

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
				pBlockEntity.setConnection(null, false, Type.NONE);
			}
		}
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.setConnection(new BlockPos(compound.getInt("conX"), compound.getInt("conY"), compound.getInt("conZ")), compound.getBoolean("isFirst"), WireItem.Type.values()[compound.getInt("cableType")]);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (isConnected()){
			compound.putInt("conX", this.getConnection().getX());
			compound.putInt("conY", this.getConnection().getY());
			compound.putInt("conZ", this.getConnection().getZ());
			compound.putBoolean("isFirst", this.wasFirst);
			compound.putInt("cableType", this.cable.ordinal());
		}
	}

	@Nullable
	public BlockPos connection() {
		return this.getConnection();
	}

	public void setConnection(BlockPos connection, boolean wasFirst, WireItem.Type cable) {
		this.connection = connection;
		this.wasFirst = wasFirst;
		this.cable = cable;
	}

	public boolean isFirst() {
		return wasFirst;
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
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag compound = new CompoundTag();
		this.saveAdditional(compound);
		return compound;
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(pkt.getTag());
	}

	public Vec3 getWireEndPos() {
		return new Vec3(0.5D, 0.7D, 0.5D);
	}

	public Vec3 getWireStartPos() {
		return new Vec3(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ()).add(0.5D, 0.7D, 0.5D);
	}

	public Vec3 getEyePosition(float pPartialTicks) {
		return new Vec3(this.getBlockPos().getX(), this.getBlockPos().getY() + 0.4F, this.getBlockPos().getZ());
	}
	
	public WireItem.Type cable() {return cable;}
	
	//FORGE:
	@Override
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}
