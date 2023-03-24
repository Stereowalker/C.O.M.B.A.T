package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;

public class ClientboundEntityStatsPacket extends ClientboundUnionPacket {
	private CompoundTag stats;
	private int id;
	
	public ClientboundEntityStatsPacket(final CompoundTag statsIn, final int id) {
		super(Combat.getInstance().channel);
		this.stats = statsIn;
		this.id = id;
	}
	
	public ClientboundEntityStatsPacket(final LivingEntity entity){
		this(PlayerAttributeLevels.getRankNBT(entity), entity.getId());
	}
	
	public ClientboundEntityStatsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stats = packetBuffer.readNbt();
		this.id = packetBuffer.readInt();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(this.stats);
		packetBuffer.writeInt(this.id);
	}
	
	@Override
	public boolean handleOnClient(LocalPlayer player) {
		if (player != null && player.level.getEntity(id) != null) {
			PlayerAttributeLevels.setRankNBT(stats, player.level.getEntity(id));
		}
		return true;
	}
}
