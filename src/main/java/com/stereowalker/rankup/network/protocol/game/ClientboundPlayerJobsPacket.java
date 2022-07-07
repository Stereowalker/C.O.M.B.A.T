package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.world.job.PlayerJobs;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;

public class ClientboundPlayerJobsPacket extends ClientboundUnionPacket {
	private CompoundTag stats;
	
	public ClientboundPlayerJobsPacket(final CompoundTag statsIn) {
		super(Combat.getInstance().channel);
		this.stats = statsIn;
	}
	
	public ClientboundPlayerJobsPacket(final LivingEntity entity){
		this(PlayerJobs.getRankNBT(entity));
	}
	
	public ClientboundPlayerJobsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stats = packetBuffer.readNbt();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.stats);
	}
	
	@Override
	public boolean handleOnClient(LocalPlayer player) {
		if (player != null) {
			PlayerJobs.setRankNBT(stats, player);
		}
		return true;
	}
}
