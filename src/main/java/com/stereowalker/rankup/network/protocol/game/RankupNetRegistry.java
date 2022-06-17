package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.unionlib.network.PacketRegistry;

import net.minecraftforge.network.simple.SimpleChannel;


public class RankupNetRegistry {
	public static void registerMessages(SimpleChannel arg0) {
		int netID = -101;
		arg0.registerMessage(netID++, ServerboundUpgradeLevelsPacket.class, ServerboundUpgradeLevelsPacket::encode, ServerboundUpgradeLevelsPacket::decode, ServerboundUpgradeLevelsPacket::handle);
		arg0.registerMessage(netID++, ClientboundPlayerStatsPacket.class, ClientboundPlayerStatsPacket::encode, ClientboundPlayerStatsPacket::decode, ClientboundPlayerStatsPacket::handle);
		arg0.registerMessage(netID++, ClientboundEntityStatsPacket.class, ClientboundEntityStatsPacket::encode, ClientboundEntityStatsPacket::decode, ClientboundEntityStatsPacket::handle);
		arg0.registerMessage(netID++, ClientboundPlayerSkillsPacket.class, ClientboundPlayerSkillsPacket::encode, ClientboundPlayerSkillsPacket::decode, ClientboundPlayerSkillsPacket::handle);
		arg0.registerMessage(netID++, ClientboundStatManagerPacket.class, ClientboundStatManagerPacket::encode, ClientboundStatManagerPacket::decode, ClientboundStatManagerPacket::handle);
		PacketRegistry.registerMessage(arg0, netID++, ClientboundPlayerLevelUpPacket.class, (packetBuffer) -> {return new ClientboundPlayerLevelUpPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ClientboundPlayerDisplayStatPacket.class, (packetBuffer) -> {return new ClientboundPlayerDisplayStatPacket(packetBuffer);});
	}
}
