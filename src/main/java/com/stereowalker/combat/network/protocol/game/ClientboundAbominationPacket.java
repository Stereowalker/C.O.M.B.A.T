package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientboundAbominationPacket extends ClientboundUnionPacket {
	private double x;
	private double y;
	private double z;
	
	public ClientboundAbominationPacket(
			final double x,
			final double y,
			final double z) {
		super(Combat.getInstance().channel);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public ClientboundAbominationPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.x = packetBuffer.readDouble();
		this.y = packetBuffer.readDouble();
		this.z = packetBuffer.readDouble();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeDouble(this.x);
		packetBuffer.writeDouble(this.y);
		packetBuffer.writeDouble(this.z);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean handleOnClient(LocalPlayer player) {
		Minecraft.getInstance().level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
		Minecraft.getInstance().level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
		Minecraft.getInstance().level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
		return true;
	}
}
