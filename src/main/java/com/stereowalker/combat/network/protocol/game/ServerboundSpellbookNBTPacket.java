package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundSpellbookNBTPacket extends ServerboundUnionPacket {
	private CompoundTag clientTag;
	private UUID uuid;

	public ServerboundSpellbookNBTPacket(final CompoundTag clientTag, final UUID uuid) {
		super(Combat.getInstance().channel);
		this.clientTag = clientTag;
		this.uuid = uuid;
	}
	
	public ServerboundSpellbookNBTPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.clientTag = packetBuffer.readNbt();
		this.uuid = new UUID(packetBuffer.readLong(), packetBuffer.readLong());
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.clientTag);
		packetBuffer.writeLong(this.uuid.getMostSignificantBits());
		packetBuffer.writeLong(this.uuid.getLeastSignificantBits());
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		if (uuid.equals(sender.getUUID())) {
			AbstractSpellBookItem.getMainSpellBook(sender).setTag(clientTag);
		}
		return true;
	}
}
