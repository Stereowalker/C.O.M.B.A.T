package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.world.item.AbstractSpellBookItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundSpellbookNBTPacket {
	private CompoundTag clientTag;
	private UUID uuid;

	public ServerboundSpellbookNBTPacket(final CompoundTag clientTag, final UUID uuid) {
		this.uuid = uuid;
		this.clientTag = clientTag;
	}

	public static void encode(final ServerboundSpellbookNBTPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.clientTag);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ServerboundSpellbookNBTPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ServerboundSpellbookNBTPacket(packetBuffer.readNbt(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final ServerboundSpellbookNBTPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayer sender = context.getSender();
			if (sender == null) {
				return;
			}
			final CompoundTag clientItem = msg.clientTag;
			final UUID uuid = msg.uuid;
			if (uuid.equals(Player.createPlayerUUID(sender.getGameProfile()))) {
				AbstractSpellBookItem.getMainSpellBook(sender).setTag(clientItem);
			}
		});
		context.setPacketHandled(true);
	}
}
