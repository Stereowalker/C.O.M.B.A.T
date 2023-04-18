package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundHeldItemStackNBTPacket extends ServerboundUnionPacket {
	private CompoundTag clientTag;
	private boolean isMainHand;
	private UUID uuid;

	public ServerboundHeldItemStackNBTPacket(final CompoundTag clientTag, final boolean isMainHand, final UUID uuid) {
		super(Combat.getInstance().channel);
		this.clientTag = clientTag;
		this.isMainHand = isMainHand;
		this.uuid = uuid;
	}
	
	public ServerboundHeldItemStackNBTPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.clientTag = packetBuffer.readNbt();
		this.isMainHand = packetBuffer.readBoolean();
		this.uuid = new UUID(packetBuffer.readLong(), packetBuffer.readLong());
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.clientTag);
		packetBuffer.writeBoolean(this.isMainHand);
		packetBuffer.writeLong(this.uuid.getMostSignificantBits());
		packetBuffer.writeLong(this.uuid.getLeastSignificantBits());
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		if (uuid.equals(sender.getUUID())) {
			if (isMainHand) {
				sender.getMainHandItem().setTag(clientTag);
			}
			else {
				sender.getOffhandItem().setTag(clientTag);
			}
		}
		return true;
	}
}
