package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;

public class ServerboundPronePacket extends ServerboundUnionPacket {
	boolean isProne;

	public ServerboundPronePacket(boolean isProneIn) {
		super(Combat.getInstance().channel);
		this.isProne = isProneIn;
	}
	
	public ServerboundPronePacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.isProne = packetBuffer.readBoolean();
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeBoolean(this.isProne);
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		if (isProne && sender.getForcedPose() != Pose.SWIMMING) sender.setForcedPose(Pose.SWIMMING);
		else if (!isProne && sender.getForcedPose() == Pose.SWIMMING) sender.setForcedPose(null);
		return true;
	}
}
