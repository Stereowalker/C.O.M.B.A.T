package com.stereowalker.combat.network.client.play;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CHeldItemStackNBTPacket {
	private CompoundNBT clientTag;
	private boolean isMainHand;
	private UUID uuid;

	public CHeldItemStackNBTPacket(final CompoundNBT clientTag, final boolean isMainHand, final UUID uuid) {
		this.uuid = uuid;
		this.clientTag = clientTag;
		this.isMainHand = isMainHand;
	}

	public static void encode(final CHeldItemStackNBTPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeCompoundTag(msg.clientTag);
		packetBuffer.writeBoolean(msg.isMainHand);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static CHeldItemStackNBTPacket decode(final PacketBuffer packetBuffer) {
		return new CHeldItemStackNBTPacket(packetBuffer.readCompoundTag(), packetBuffer.readBoolean(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final CHeldItemStackNBTPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayerEntity sender = context.getSender();
			if (sender == null) {
				return;
			}
			final CompoundNBT clientItem = msg.clientTag;
			final boolean isMainHand = msg.isMainHand;
			final UUID uuid = msg.uuid;
			if (uuid.equals(PlayerEntity.getUUID(sender.getGameProfile()))) {
				if (isMainHand) {
					sender.getHeldItemMainhand().setTag(clientItem);
				}
				else {
					sender.getHeldItemOffhand().setTag(clientItem);
				}
			}
		});
		context.setPacketHandled(true);
	}
}
