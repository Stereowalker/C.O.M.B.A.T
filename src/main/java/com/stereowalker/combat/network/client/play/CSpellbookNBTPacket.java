package com.stereowalker.combat.network.client.play;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.item.AbstractSpellBookItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CSpellbookNBTPacket {
	private CompoundNBT clientTag;
	private UUID uuid;

	public CSpellbookNBTPacket(final CompoundNBT clientTag, final UUID uuid) {
		this.uuid = uuid;
		this.clientTag = clientTag;
	}

	public static void encode(final CSpellbookNBTPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeCompoundTag(msg.clientTag);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static CSpellbookNBTPacket decode(final PacketBuffer packetBuffer) {
		return new CSpellbookNBTPacket(packetBuffer.readCompoundTag(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final CSpellbookNBTPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayerEntity sender = context.getSender();
			if (sender == null) {
				return;
			}
			final CompoundNBT clientItem = msg.clientTag;
			final UUID uuid = msg.uuid;
			if (uuid.equals(PlayerEntity.getUUID(sender.getGameProfile()))) {
				AbstractSpellBookItem.getMainSpellBook(sender).setTag(clientItem);
			}
		});
		context.setPacketHandled(true);
	}
}
