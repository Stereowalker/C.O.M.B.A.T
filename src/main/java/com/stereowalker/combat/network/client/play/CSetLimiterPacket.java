package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class CSetLimiterPacket extends CUnionPacket {
	public CSetLimiterPacket() {
		super(Combat.getInstance().channel);
	}

	public CSetLimiterPacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
	}

	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		CombatEntityStats.setLimiter(sender, !CombatEntityStats.isLimiterOn(sender));
		return true;
	}
}
