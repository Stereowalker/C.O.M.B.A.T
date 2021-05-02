package com.stereowalker.combat.network.client.play;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.network.server.SPlayerStatsPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class CRequestStatsPacket {
	private UUID uuid;

	public CRequestStatsPacket(final UUID uuid) {
		this.uuid = uuid;
	}

	public static void encode(final CRequestStatsPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static CRequestStatsPacket decode(final PacketBuffer packetBuffer) {
		return new CRequestStatsPacket(new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final CRequestStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayerEntity sender = context.getSender();
			if (sender == null) {
				return;
			}
			final UUID uuid = msg.uuid;
			if (uuid.equals(PlayerEntity.getUUID(sender.getGameProfile()))) {
				Combat.getInstance().channel.sendTo(new SPlayerStatsPacket(sender), sender.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
			}
		});
		context.setPacketHandled(true);
	}
}
