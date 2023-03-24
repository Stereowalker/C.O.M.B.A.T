package com.stereowalker.combat.network.protocol.game;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundAbominationPacket {
	private double x;
	private double y;
	private double z;
	
	public ClientboundAbominationPacket(
			final double x,
			final double y,
			final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void encode(final ClientboundAbominationPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeDouble(msg.x);
		packetBuffer.writeDouble(msg.y);
		packetBuffer.writeDouble(msg.z);
	}
	
	public static ClientboundAbominationPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ClientboundAbominationPacket(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble());
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final ClientboundAbominationPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final double x = msg.x;
			final double y = msg.y;
			final double z = msg.z;
			update(x, y, z);
		}));
		context.setPacketHandled(true);
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void update(
			final double x,
			final double y,
			final double z) {
		Minecraft.getInstance().level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
		Minecraft.getInstance().level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
		Minecraft.getInstance().level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
	}
}
