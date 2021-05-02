package com.stereowalker.combat.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class BasePacket {
	public abstract void encode(final PacketBuffer packetBuffer);
	public abstract void message(final Supplier<NetworkEvent.Context> contextSupplier);
}
