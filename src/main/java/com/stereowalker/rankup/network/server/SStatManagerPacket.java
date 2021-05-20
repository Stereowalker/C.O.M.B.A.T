package com.stereowalker.rankup.network.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.stat.StatSettings;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SStatManagerPacket {
	private Stat stat;
	private StatSettings settings;
	
	public SStatManagerPacket(final Stat statIn, final StatSettings settingsIn) {
		this.stat = statIn;
		this.settings = settingsIn;
	}
	
	public static void encode(final SStatManagerPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeResourceLocation(msg.stat.getRegistryName());
		packetBuffer.writeCompoundTag(msg.settings.serialize());
	}
	
	public static SStatManagerPacket decode(final PacketBuffer packetBuffer) {
		return new SStatManagerPacket(CombatRegistries.STATS.getValue(packetBuffer.readResourceLocation()), new StatSettings(packetBuffer.readCompoundTag()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final SStatManagerPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final Stat stat = msg.stat;
			final StatSettings settings = msg.settings;
			update(stat, settings);
		}));
		context.setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void update(final Stat stat, final StatSettings settings) {
		Map<Stat,StatSettings> statMap = new HashMap<>();
		statMap.putAll(Combat.rankupInstance.CLIENT_STATS);
		statMap.put(stat, settings);
		System.out.println("UPDATED CLIENT STATS");
		Combat.rankupInstance.CLIENT_STATS = ImmutableMap.copyOf(statMap);
	}
}
