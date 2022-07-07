package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.unionlib.network.PacketRegistry;

import net.minecraftforge.network.simple.SimpleChannel;


public class RankupNetRegistry {
	public static void registerMessages(SimpleChannel arg0) {
		int netID = -101;
		arg0.registerMessage(netID++, ClientboundPlayerStatsPacket.class, ClientboundPlayerStatsPacket::encode, ClientboundPlayerStatsPacket::decode, ClientboundPlayerStatsPacket::handle);
		arg0.registerMessage(netID++, ClientboundPlayerSkillsPacket.class, ClientboundPlayerSkillsPacket::encode, ClientboundPlayerSkillsPacket::decode, ClientboundPlayerSkillsPacket::handle);
		arg0.registerMessage(netID++, ClientboundStatManagerPacket.class, ClientboundStatManagerPacket::encode, ClientboundStatManagerPacket::decode, ClientboundStatManagerPacket::handle);
		PacketRegistry.registerMessage(arg0, netID++, ServerboundUpgradeLevelsPacket.class, (packetBuffer) -> {return new ServerboundUpgradeLevelsPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundActivateSkillPacket.class, (packetBuffer) -> {return new ServerboundActivateSkillPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ClientboundPlayerJobsPacket.class, (packetBuffer) -> {return new ClientboundPlayerJobsPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ClientboundEntityStatsPacket.class, (packetBuffer) -> {return new ClientboundEntityStatsPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ClientboundPlayerLevelUpPacket.class, (packetBuffer) -> {return new ClientboundPlayerLevelUpPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ClientboundPlayerDisplayStatPacket.class, (packetBuffer) -> {return new ClientboundPlayerDisplayStatPacket(packetBuffer);});
	}
}
