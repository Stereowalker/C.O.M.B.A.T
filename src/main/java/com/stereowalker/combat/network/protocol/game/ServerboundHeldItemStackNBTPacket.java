package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class ServerboundHeldItemStackNBTPacket {
	private CompoundTag clientTag;
	private boolean isMainHand;
	private UUID uuid;

	public ServerboundHeldItemStackNBTPacket(final CompoundTag clientTag, final boolean isMainHand, final UUID uuid) {
		this.uuid = uuid;
		this.clientTag = clientTag;
		this.isMainHand = isMainHand;
	}

	public static void encode(final ServerboundHeldItemStackNBTPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.clientTag);
		packetBuffer.writeBoolean(msg.isMainHand);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ServerboundHeldItemStackNBTPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ServerboundHeldItemStackNBTPacket(packetBuffer.readNbt(), packetBuffer.readBoolean(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final ServerboundHeldItemStackNBTPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayer sender = context.getSender();
			if (sender == null) {
				return;
			}
			final CompoundTag clientItem = msg.clientTag;
			final boolean isMainHand = msg.isMainHand;
			final UUID uuid = msg.uuid;
			if (uuid.equals(Player.createPlayerUUID(sender.getGameProfile()))) {
				if (isMainHand) {
					sender.getMainHandItem().setTag(clientItem);
				}
				else {
					sender.getOffhandItem().setTag(clientItem);
				}
			}
		});
		context.setPacketHandled(true);
	}
}
