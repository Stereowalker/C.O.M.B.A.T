package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.Combat;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundRequestStatsPacket {
	private UUID uuid;

	public ServerboundRequestStatsPacket(final UUID uuid) {
		this.uuid = uuid;
	}

	public static void encode(final ServerboundRequestStatsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ServerboundRequestStatsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ServerboundRequestStatsPacket(new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final ServerboundRequestStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayer sender = context.getSender();
			if (sender == null) {
				return;
			}
			final UUID uuid = msg.uuid;
			if (uuid.equals(Player.createPlayerUUID(sender.getGameProfile()))) {
				Combat.getInstance().channel.sendTo(new ClientboundPlayerStatsPacket(sender), sender.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		});
		context.setPacketHandled(true);
	}
}
