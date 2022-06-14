package com.stereowalker.rankup.network.protocol.game;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.StatSettings;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundStatManagerPacket {
	private Stat stat;
	private StatSettings settings;
	
	public ClientboundStatManagerPacket(final Stat statIn, final StatSettings settingsIn) {
		this.stat = statIn;
		this.settings = settingsIn;
	}
	
	public static void encode(final ClientboundStatManagerPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeResourceLocation(msg.stat.getRegistryName());
		packetBuffer.writeNbt(msg.settings.serialize());
	}
	
	public static ClientboundStatManagerPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ClientboundStatManagerPacket(CombatRegistries.STATS.getValue(packetBuffer.readResourceLocation()), new StatSettings(packetBuffer.readNbt()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final ClientboundStatManagerPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
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
		Combat.rankupInstance.CLIENT_STATS = ImmutableMap.copyOf(statMap);
	}
}
