package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundRequestStatsPacket extends ServerboundUnionPacket {
	private UUID uuid;

	public ServerboundRequestStatsPacket(final UUID uuid) {
		super(Combat.getInstance().channel);
		this.uuid = uuid;
	}
	
	public ServerboundRequestStatsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.uuid = new UUID(packetBuffer.readLong(), packetBuffer.readLong());
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeLong(this.uuid.getMostSignificantBits());
		packetBuffer.writeLong(this.uuid.getLeastSignificantBits());
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		if (uuid.equals(sender.getUUID())) {
			new ClientboundPlayerStatsPacket(sender).send(sender);
		}
		return true;
	}
}
