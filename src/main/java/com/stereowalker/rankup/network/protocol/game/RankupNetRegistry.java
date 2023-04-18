package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.unionlib.api.collectors.PacketCollector;


public class RankupNetRegistry {
	public static void registerMessages(PacketCollector collector) {
		collector.registerPacket(ClientboundPlayerJobsPacket.class, (packetBuffer) -> {return new ClientboundPlayerJobsPacket(packetBuffer);});
		collector.registerPacket(ClientboundEntityStatsPacket.class, (packetBuffer) -> {return new ClientboundEntityStatsPacket(packetBuffer);});
		collector.registerPacket(ClientboundPlayerLevelUpPacket.class, (packetBuffer) -> {return new ClientboundPlayerLevelUpPacket(packetBuffer);});
		collector.registerPacket(ClientboundPlayerStatsPacket.class, (packetBuffer) -> {return new ClientboundPlayerStatsPacket(packetBuffer);});
		collector.registerPacket(ClientboundPlayerSkillsPacket.class, (packetBuffer) -> {return new ClientboundPlayerSkillsPacket(packetBuffer);});
		collector.registerPacket(ClientboundStatManagerPacket.class, (packetBuffer) -> {return new ClientboundStatManagerPacket(packetBuffer);});
		collector.registerPacket(ServerboundUpgradeLevelsPacket.class, (packetBuffer) -> {return new ServerboundUpgradeLevelsPacket(packetBuffer);});
		collector.registerPacket(ServerboundActivateSkillPacket.class, (packetBuffer) -> {return new ServerboundActivateSkillPacket(packetBuffer);});
	}
}
