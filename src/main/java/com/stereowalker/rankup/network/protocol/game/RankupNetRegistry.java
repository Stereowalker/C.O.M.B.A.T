package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.PacketRegistry;


public class RankupNetRegistry {
	public static void registerMessages() {
		int netID = -101;
		Combat.getInstance().channel.registerMessage(netID++, ServerboundUpgradeLevelsPacket.class, ServerboundUpgradeLevelsPacket::encode, ServerboundUpgradeLevelsPacket::decode, ServerboundUpgradeLevelsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, ClientboundPlayerStatsPacket.class, ClientboundPlayerStatsPacket::encode, ClientboundPlayerStatsPacket::decode, ClientboundPlayerStatsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, ClientboundEntityStatsPacket.class, ClientboundEntityStatsPacket::encode, ClientboundEntityStatsPacket::decode, ClientboundEntityStatsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, ClientboundPlayerSkillsPacket.class, ClientboundPlayerSkillsPacket::encode, ClientboundPlayerSkillsPacket::decode, ClientboundPlayerSkillsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, ClientboundStatManagerPacket.class, ClientboundStatManagerPacket::encode, ClientboundStatManagerPacket::decode, ClientboundStatManagerPacket::handle);
		PacketRegistry.registerMessage(Combat.getInstance().channel, netID++, ClientboundPlayerLevelUpPacket.class, (packetBuffer) -> {return new ClientboundPlayerLevelUpPacket(packetBuffer);});
		PacketRegistry.registerMessage(Combat.getInstance().channel, netID++, ClientboundPlayerDisplayStatPacket.class, (packetBuffer) -> {return new ClientboundPlayerDisplayStatPacket(packetBuffer);});
	}
}
