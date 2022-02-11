package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ServerboundClientMotionPacket extends CUnionPacket {
	Vec3 motion;

	public ServerboundClientMotionPacket(Vec3 motionIn) {
		super(Combat.getInstance().channel);
		this.motion = motionIn;
	}
	
	public ServerboundClientMotionPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.motion = new Vec3(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble());
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeDouble(this.motion.x);
		packetBuffer.writeDouble(this.motion.y);
		packetBuffer.writeDouble(this.motion.z);
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		CombatEntityStats.setClientMotion(sender, this.motion);
		return true;
	}
}
