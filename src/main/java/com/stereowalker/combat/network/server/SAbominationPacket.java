package com.stereowalker.combat.network.server;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SAbominationPacket {
	private double x;
	private double y;
	private double z;
	
	public SAbominationPacket(
			final double x,
			final double y,
			final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void encode(final SAbominationPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeDouble(msg.x);
		packetBuffer.writeDouble(msg.y);
		packetBuffer.writeDouble(msg.z);
	}
	
	public static SAbominationPacket decode(final PacketBuffer packetBuffer) {
		return new SAbominationPacket(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble());
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final SAbominationPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final double x = msg.x;
			final double y = msg.y;
			final double z = msg.z;
			update(x, y, z);
		}));
		context.setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void update(
			final double x,
			final double y,
			final double z) {
		Minecraft.getInstance().world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
		Minecraft.getInstance().world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
		Minecraft.getInstance().world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
	}
}
