package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientboundPlayerSkillsPacket extends ClientboundUnionPacket {
	private CompoundTag stats;
	
	public ClientboundPlayerSkillsPacket(final CompoundTag statsIn) {
		super(Combat.getInstance().channel);
		this.stats = statsIn;
	}
	
	public ClientboundPlayerSkillsPacket(final ServerPlayer player){
		this(PlayerSkills.getRankSkillNBT(player));
	}
	
	public ClientboundPlayerSkillsPacket(FriendlyByteBuf packetBuffer) {
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
			PlayerSkills.setRankSkillNBT(stats, player);
		}
		return true;
	}
}
