package com.stereowalker.rankup.network.protocol.game;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ClientboundNearbyPlayerStatsPacket extends ClientboundUnionPacket {
	private CompoundTag stats;
	private UUID id;
	
	public ClientboundNearbyPlayerStatsPacket(final CompoundTag statsIn, final UUID id) {
		super(Combat.getInstance().channel);
		this.stats = statsIn;
		this.id = id;
	}
	
	public ClientboundNearbyPlayerStatsPacket(final ServerPlayer entity){
		this(PlayerAttributeLevels.getRankNBT(entity), entity.getUUID());
	}
	
	public ClientboundNearbyPlayerStatsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stats = packetBuffer.readNbt();
		this.id = packetBuffer.readUUID();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.stats);
		packetBuffer.writeUUID(this.id);
	}
	
	@Override
	public boolean handleOnClient(LocalPlayer player) {
		if (player != null) {
			Player pl = player.level.getPlayerByUUID(id);
			if (pl != null) {
				PlayerAttributeLevels.setRankNBT(stats, pl);
			}
		}
		return true;
	}
}
