package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.Pose;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class CPronePacket extends CUnionPacket {
	boolean isProne;

	public CPronePacket(boolean isProneIn) {
		super(Combat.getInstance().channel);
		this.isProne = isProneIn;
	}
	
	public CPronePacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.isProne = packetBuffer.readBoolean();
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeBoolean(this.isProne);
	}
	
	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		if (isProne && sender.getForcedPose() != Pose.SWIMMING) sender.setForcedPose(Pose.SWIMMING);
		else if (!isProne && sender.getForcedPose() == Pose.SWIMMING) sender.setForcedPose(null);
		return true;
	}
}
