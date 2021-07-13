package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

public class CClientMotionPacket extends CUnionPacket {
	Vector3d motion;

	public CClientMotionPacket(Vector3d motionIn) {
		super(Combat.getInstance().channel);
		this.motion = motionIn;
	}
	
	public CClientMotionPacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.motion = new Vector3d(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble());
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeDouble(this.motion.x);
		packetBuffer.writeDouble(this.motion.y);
		packetBuffer.writeDouble(this.motion.z);
	}
	
	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		CombatEntityStats.setClientMotion(sender, this.motion);
		return true;
	}
}
