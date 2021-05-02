package com.stereowalker.rankup.network;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.network.client.CUpgradeLevelsPacket;
import com.stereowalker.rankup.network.server.SPlayerDisplayStatPacket;
import com.stereowalker.rankup.network.server.SPlayerLevelUpPacket;
import com.stereowalker.rankup.network.server.SPlayerSkillsPacket;
import com.stereowalker.rankup.network.server.SPlayerStatsPacket;
import com.stereowalker.unionlib.network.PacketRegistry;


public class RankupNetRegistry {
	public static void registerMessages() {
		int netID = -101;
		Combat.getInstance().channel.registerMessage(netID++, CUpgradeLevelsPacket.class, CUpgradeLevelsPacket::encode, CUpgradeLevelsPacket::decode, CUpgradeLevelsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, SPlayerStatsPacket.class, SPlayerStatsPacket::encode, SPlayerStatsPacket::decode, SPlayerStatsPacket::handle);
		Combat.getInstance().channel.registerMessage(netID++, SPlayerSkillsPacket.class, SPlayerSkillsPacket::encode, SPlayerSkillsPacket::decode, SPlayerSkillsPacket::handle);
		PacketRegistry.registerMessage(Combat.getInstance().channel, netID++, SPlayerLevelUpPacket.class, (packetBuffer) -> {return new SPlayerLevelUpPacket(packetBuffer);});
		PacketRegistry.registerMessage(Combat.getInstance().channel, netID++, SPlayerDisplayStatPacket.class, (packetBuffer) -> {return new SPlayerDisplayStatPacket(packetBuffer);});
	}
}
