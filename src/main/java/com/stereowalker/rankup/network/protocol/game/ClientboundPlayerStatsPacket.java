package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientboundPlayerStatsPacket extends ClientboundUnionPacket {
	private CompoundTag stats;
	
	public ClientboundPlayerStatsPacket(final CompoundTag statsIn) {
		super(Combat.getInstance().channel);
		this.stats = statsIn;
	}
	
	public ClientboundPlayerStatsPacket(final ServerPlayer player){
		this(PlayerAttributeLevels.getRankNBT(player));
	}
	
	public ClientboundPlayerStatsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stats = packetBuffer.readNbt();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.stats);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean handleOnClient(LocalPlayer player) {
		if (player != null) {
			PlayerAttributeLevels.setRankNBT(stats, player);
		}
		return true;
	}
}
