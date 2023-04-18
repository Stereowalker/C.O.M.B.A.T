package com.stereowalker.rankup.network.protocol.game;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.StatSettings;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientboundStatManagerPacket extends ClientboundUnionPacket {
	private ResourceLocation stat;
	private StatSettings settings;
	
	public ClientboundStatManagerPacket(final ResourceLocation statIn, final StatSettings settingsIn) {
		super(Combat.getInstance().channel);
		this.stat = statIn;
		this.settings = settingsIn;
	}
	
	public ClientboundStatManagerPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stat = packetBuffer.readResourceLocation();
		this.settings = new StatSettings(packetBuffer.readNbt());
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeResourceLocation(this.stat);
		packetBuffer.writeNbt(this.settings.serialize());
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean handleOnClient(LocalPlayer player) {
		Map<ResourceKey<Stat>,StatSettings> statMap = new HashMap<>();
		statMap.putAll(Combat.rankupInstance.CLIENT_STAT_SETTINGS);
		statMap.put(ResourceKey.create(CombatRegistries.STATS_REGISTRY, this.stat), this.settings);
		Combat.rankupInstance.CLIENT_STAT_SETTINGS = ImmutableMap.copyOf(statMap);
		return true;
	}
}
