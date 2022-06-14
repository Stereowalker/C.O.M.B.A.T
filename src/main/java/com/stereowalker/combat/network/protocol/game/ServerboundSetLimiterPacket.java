package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundSetLimiterPacket extends ServerboundUnionPacket {
	public ServerboundSetLimiterPacket() {
		super(Combat.getInstance().channel);
	}

	public ServerboundSetLimiterPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
	}

	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		CombatEntityStats.setLimiter(sender, !CombatEntityStats.isLimiterOn(sender));
		return true;
	}
}
